package com.illinois.beep;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;
import com.illinois.beep.database.UserRestriction;
import com.illinois.beep.database.UserRestrictionsViewModel;
import com.illinois.beep.databinding.FragmentProductReviewBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProductReviewFragment extends Fragment {
    FragmentProductReviewBinding binding;
    String productId;
    int position;
    RecyclerView recyclerView;

    public ProductReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        assert this.getArguments() != null;
        productId = this.getArguments().getString("productId");
        position = this.getArguments().getInt("positionIndex", -1);
        int initQuantity = this.getArguments().getInt("quantity", 1);
        binding = FragmentProductReviewBinding.inflate(inflater, container, false);

        binding.productQuantityValue.setText(String.valueOf(initQuantity));


        System.out.println("product review frag.");
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Product product = ProductDatabase.get(productId);

        Picasso.get().load(product.getImage_url()).into(binding.productImage);
        binding.productName.setText(product.getName());
        binding.productDescriptionText.setText(product.getDescription());
        binding.productDescriptionText.setText(product.getDescription());
        binding.productIngredientText.setText(product.getIngredients().toString());

        binding.productQuantityIncreaseButton.setOnClickListener(v -> {
           int quantity = Integer.parseInt(binding.productQuantityValue.getText().toString());
           binding.productQuantityValue.setText(String.valueOf(quantity + 1));
        });

        binding.productQuantityDecreaseButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(binding.productQuantityValue.getText().toString());
            binding.productQuantityValue.setText(String.valueOf(Math.max(quantity - 1, 1)));
        });


        binding.findSubstituteButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("productId", product.getId());
            bundle.putInt("position", position);
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_productReviewFragment_to_substituteFragment, bundle);
        });

        MyListViewModel myListViewModel = new ViewModelProvider(requireActivity()).get(MyListViewModel.class);
        binding.addToListButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(binding.productQuantityValue.getText().toString());

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_productReviewFragment_to_FirstFragment);
            myListViewModel.append(new MyListItem(product, quantity));
        });

        binding.backButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });

        Set<String> dangerRestrictions = getDangerRestrictions();
        Set<String> warningRestrictions = getWarningRestrictions();

        List<Pair<String, ProductIndicationListAdapter.RestrictionLevel>> labelledIndications = new ArrayList<>();

        for (Map.Entry<String, Boolean> indication : product.getIndications().entrySet()) {
            if (indication.getValue()) {
                if (dangerRestrictions.contains(indication.getKey())) {
                    labelledIndications.add(new Pair<>(indication.getKey(), ProductIndicationListAdapter.RestrictionLevel.BAD));
                } else if (warningRestrictions.contains(indication.getKey())) {
                    labelledIndications.add(new Pair<>(indication.getKey(), ProductIndicationListAdapter.RestrictionLevel.WARN));
                } else {
                    labelledIndications.add(new Pair<>(indication.getKey(), ProductIndicationListAdapter.RestrictionLevel.GOOD));
                }
            }
        }

        ProductIndicationListAdapter adapter = new ProductIndicationListAdapter(labelledIndications);
        recyclerView = view.findViewById(R.id.product_indications);
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        recyclerView.setAdapter(adapter);
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

    private Set<String> getWarningRestrictions() {
        Set<String> warningRestrictions = new HashSet<>();
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
                if (userRestriction.getFavorite() == 0) {
                    warningRestrictions.add(userRestriction.getRestriction());
                }
            }
        }
        return warningRestrictions;
    }
}