package com.illinois.beep;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.illinois.beep.databinding.FragmentFirstBinding;

import java.util.ArrayList;

import androidx.lifecycle.ViewModelProvider;

public class Pop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.9), (int)(height*0.8));
    }

    private ListView l;
    private FragmentFirstBinding binding;

    //@Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

//        l = view.findViewById(R.id.my_list_view);
//        MyListAdapter adapter = new MyListAdapter(binding.getRoot().getContext(), requireActivity(), new ArrayList<>());
//        l.setAdapter(adapter);
//
//        MyListViewModel myListViewModel = new ViewModelProvider(requireActivity()).get(MyListViewModel.class);
//
//        myListViewModel.getMyList().observe(getViewLifecycleOwner(), adapter::updateMyList);



        return view;

    }
}
