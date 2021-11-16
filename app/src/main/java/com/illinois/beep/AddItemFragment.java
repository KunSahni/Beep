
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

import com.illinois.beep.database.ProductDatabase;
import com.illinois.beep.databinding.FragmentAddItemBinding;

import java.util.List;

public class AddItemFragment extends Fragment {

    private FragmentAddItemBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddItemBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyListViewModel myListViewModel = new ViewModelProvider(requireActivity()).get(MyListViewModel.class);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                List<MyListItem> list = myListViewModel.getMyList().getValue();
                assert list != null;
                list.add(new MyListItem(ProductDatabase.get("4c7ff1cd-496c-4df3-9e20-5758c366d853"), 10));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}