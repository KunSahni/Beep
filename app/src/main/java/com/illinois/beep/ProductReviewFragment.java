package com.illinois.beep;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;
import com.illinois.beep.databinding.FragmentProductReviewBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductReviewFragment extends Fragment {
    FragmentProductReviewBinding binding;
    String productId;
    int position;

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
        binding = FragmentProductReviewBinding.inflate(inflater, container, false);

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
    }
}