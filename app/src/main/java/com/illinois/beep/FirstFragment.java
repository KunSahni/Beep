package com.illinois.beep;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;
import com.illinois.beep.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ListView l;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        l = view.findViewById(R.id.my_list_view);
        MyListAdapter adapter = new MyListAdapter(binding.getRoot().getContext(), new ArrayList<>());
        l.setAdapter(adapter);

        MyListViewModel myListViewModel = new ViewModelProvider(requireActivity()).get(MyListViewModel.class);

        myListViewModel.getMyList().observe(getViewLifecycleOwner(), adapter::updateMyList);

        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textviewTestAddItem.setOnClickListener(v -> {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_addItemFragment);
        });

        binding.textviewFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}