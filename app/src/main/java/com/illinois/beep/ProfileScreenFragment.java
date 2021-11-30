package com.illinois.beep;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illinois.beep.database.UserAdapter;
import com.illinois.beep.database.UserRestriction;
import com.illinois.beep.database.UserRestrictionsViewModel;
import com.illinois.beep.databinding.FragmentProfileScreenBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages all interactions which happen on profile screen
 */
public class ProfileScreenFragment extends Fragment{
    ImageView userPicture;
    EditText userName;
    LinearLayout restrictionsLayout;
    ArrayList<ListView> restrictionLists;
    private static UserRestrictionsViewModel userRestrictionsViewModel = null;
    private static FragmentProfileScreenBinding binding;
    private static UserAdapter regularAdapter;
    private static UserAdapter editAdapter;

    //todo: implement help, profiles and modify
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Bind to xml layout and create db
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false);
        //Set all button listeners
        binding.backBtn.setOnClickListener($ -> {
            NavHostFragment.findNavController(ProfileScreenFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
        });

        binding.helpBtn.setOnClickListener($ -> {
            NavHostFragment.findNavController(ProfileScreenFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
        });

        binding.profilesBtn.setOnClickListener($ -> {
            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.65);

            RemovePersonDialog removePersonDialog = new RemovePersonDialog(getActivity());
            removePersonDialog.show();
            //removePersonDialog.getWindow().setLayout(width, height);
        });

        binding.modifyBtn.setOnClickListener($ -> {
            if(binding.modifyBtn.getText().toString().equals(String.valueOf(R.string.edit_restrictions_cue)))
                editMode();
            else
                regularMode();
        });

        binding.addPersonBtn.setOnClickListener($ ->{
            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.45);

            AddPersonDialog addPersonDialog = new AddPersonDialog(getActivity());
            addPersonDialog.show();
            addPersonDialog.getWindow().setLayout(width, height);
        });

        userPicture = binding.userPicture;
        userName = binding.usernameEdit;
        restrictionsLayout = binding.restrictionsLinearLayout;
        restrictionLists = new ArrayList<>();

        populateTable();

        return binding.getRoot();

    }


    /**
     * Static method which returns the current userRestrictionsViewModel
     * @return an instance of userRestrictionsViewModel
     */
    public static UserRestrictionsViewModel getUserRestrictionsViewModel() {
        return userRestrictionsViewModel;
    }

    public static void refreshData () {
        regularAdapter.notifyDataSetChanged();
    }

    protected static void editMode () {
        //setup everything needed for restrictions list
        RecyclerView recyclerView = binding.recyclerview;
        binding.modifyBtn.setText(R.string.edit_restrictions_done);
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(editAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        editAdapter.notifyDataSetChanged();
    }

    protected static void regularMode () {
        //setup everything needed for restrictions list
        RecyclerView recyclerView = binding.recyclerview;
        binding.modifyBtn.setText(R.string.edit_restrictions_cue);
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(regularAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        regularAdapter.notifyDataSetChanged();
    }

    private void populateTable() {
        new Thread() {
            @Override
            public void run() {

                userRestrictionsViewModel = new ViewModelProvider(ProfileScreenFragment.this).get(UserRestrictionsViewModel.class);
                try {

                    // code runs in a thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //setup everything needed for restrictions list
                            RecyclerView recyclerView = binding.recyclerview;
                            regularAdapter = new UserAdapter(new UserAdapter.UserRestrictionDiff(), getActivity(), false);
                            editAdapter = new UserAdapter(new UserAdapter.UserRestrictionDiff(), getActivity(), true);
                            recyclerView.setAdapter(regularAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
                            // Update the cached copy of the words in the regularAdapter.
                            userRestrictionsViewModel.getAllRestrictions().observe(getViewLifecycleOwner(),new Observer<List<UserRestriction>>() {
                                @Override
                                public void onChanged(List<UserRestriction> restrictions) {
                                    List<UserRestriction> onlyNames = new ArrayList<>();
                                    for (UserRestriction u:restrictions)
                                        if(u.getRestriction().equals("add"))
                                            onlyNames.add(u);
                                    regularAdapter.submitList(onlyNames);
                                    recyclerView.setAdapter(null);
                                    recyclerView.setLayoutManager(null);
                                    recyclerView.setAdapter(regularAdapter);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
                                    regularAdapter.notifyDataSetChanged();
                                }
                            });
                            userRestrictionsViewModel.getAllRestrictions().observe(getViewLifecycleOwner(), editAdapter::submitList);
                            regularAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (final Exception ex) {
                    Log.i("---","Exception in thread");
                }
            }
        }.start();

    }
}