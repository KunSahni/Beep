package com.illinois.beep;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;
import com.illinois.beep.database.RestrictionDatabase;
import com.illinois.beep.databinding.FragmentSubstitutesBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SubstituteFragment extends Fragment {
    private FragmentSubstitutesBinding binding;
    ListView l;
    String productId;
    int baseProductPosition;
    Set<Product> selectedSubstitutes = new HashSet<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert this.getArguments() != null;
        productId = this.getArguments().getString("productId");
        baseProductPosition = this.getArguments().getInt("position");
        binding = FragmentSubstitutesBinding.inflate(inflater, container, false);

        System.out.println("substitute frag.");
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Product product = ProductDatabase.get(productId);

        Picasso.get().load(product.getImage_url()).into(binding.mainProductImage);

        binding.mainProductName.setText(product.getName());

        MyListViewModel myListViewModel = new ViewModelProvider(requireActivity()).get(MyListViewModel.class);
        RestrictionViewModel restrictionViewModel = new ViewModelProvider(requireActivity()).get(RestrictionViewModel.class);

        l = view.findViewById(R.id.substitute_list_view);
        List<Product> substitutes = new ArrayList<>();
        for (String subId: product.getSubstitutes()) {
            if (!subId.equals(productId)) {
                substitutes.add(ProductDatabase.get(subId));
            }
        }

        SubstituteAdapter adapter = new SubstituteAdapter(binding.getRoot().getContext(), requireActivity(), SubstituteFragment.this, substitutes, selectedSubstitutes);
        l.setAdapter(adapter);

        binding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<MyListItem> list = myListViewModel.getMyList().getValue();
                assert list != null;

                if (selectedSubstitutes.size() > 0) {
                    list.remove(baseProductPosition);
                    for (Product substitute: selectedSubstitutes) {
                        list.add(new MyListItem(substitute, 1));
                    }
                }

                NavHostFragment.findNavController(SubstituteFragment.this).popBackStack();
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SubstituteFragment.this).popBackStack();
            }
        });
    }
}
