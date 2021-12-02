package com.illinois.beep;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.illinois.beep.database.UserRestriction;
import com.illinois.beep.database.UserRestrictionsViewModel;
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

        binding.productName.setText(product.getName());

        MyListViewModel myListViewModel = new ViewModelProvider(requireActivity()).get(MyListViewModel.class);

        l = view.findViewById(R.id.substitute_list_view);

        Set<String> dangerRestrictions = getDangerRestrictions();
        List<Product> substitutes = new ArrayList<>();
        for (String subId: product.getSubstitutes()) {
            if (!subId.equals(productId)) {
                boolean isDangerSubstitute = false;
                for (String restriction: dangerRestrictions) {
                    if (Boolean.TRUE.equals(ProductDatabase.get(subId).getIndications().getOrDefault(restriction, false))) {
                        isDangerSubstitute = true;
                    }
                }
                if (!isDangerSubstitute) {
                    substitutes.add(ProductDatabase.get(subId));
                }
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
                    if (baseProductPosition >= 0) {
                        list.remove(baseProductPosition);
                    }
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

    private Set<String> getDangerRestrictions() {
        Set<String> dangerRestrictions = new HashSet<>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(binding.getRoot().getContext());

        // get selected profile names
        List<String> profileNames = new ArrayList<>();
        for(String name: MainActivity.getUserRestrictionsViewModel().getAllUsers()) {
            if (sharedPreferences.getBoolean(name, false)) {
                profileNames.add(name);
            }
        }

        // flat the restrictions into danger (favorite) and warning set
        UserRestrictionsViewModel restrictionsViewModel = MainActivity.getUserRestrictionsViewModel();
        for (String name: profileNames) {
            for (UserRestriction userRestriction: restrictionsViewModel.getRestrictionsObjects(name)) {
                if (userRestriction.getFavorite() == 1) {
                    dangerRestrictions.add(userRestriction.getRestriction());
                }
            }
        }
        return dangerRestrictions;
    }
}
