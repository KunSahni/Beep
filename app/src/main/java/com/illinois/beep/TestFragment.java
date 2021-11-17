
package com.illinois.beep;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;
import com.illinois.beep.database.RestrictionDatabase;
import com.illinois.beep.databinding.FragmentTestBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TestFragment extends Fragment {

    private FragmentTestBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTestBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyListViewModel myListViewModel = new ViewModelProvider(requireActivity()).get(MyListViewModel.class);
        RestrictionViewModel restrictionViewModel = new ViewModelProvider(requireActivity()).get(RestrictionViewModel.class);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                List<MyListItem> list = myListViewModel.getMyList().getValue();
                assert list != null;

                List<Product> products = new ArrayList<>(ProductDatabase.getDb().values());
                Random rand = new Random();
                Product randomProduct = products.get(rand.nextInt(products.size()));
                list.add(new MyListItem(randomProduct, rand.nextInt(10)));
            }
        });

        binding.buttonRandomRestriction.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                Set<String> restrictions = restrictionViewModel.getRestrictions().getValue();
                assert restrictions != null;

                restrictions.clear();

                for (String restriction: RestrictionDatabase.getRestrictions()) {
                    if (rand.nextBoolean() && restrictions.size() < 2) {
                        restrictions.add(restriction);
                    }
                }

                System.out.println(restrictions);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}