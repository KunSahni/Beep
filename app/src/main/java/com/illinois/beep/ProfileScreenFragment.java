package com.illinois.beep;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.illinois.beep.database.UserAdapter;
import com.illinois.beep.database.UserRestrictionsViewModel;
import com.illinois.beep.databinding.FragmentProfileScreenBinding;

import java.util.ArrayList;

/**
 * This class manages all interactions which happen on profile screen
 */
public class ProfileScreenFragment extends Fragment{
    ImageView userPicture;
    EditText userName;
    LinearLayout restrictionsLayout;
    ArrayList<ListView> restrictionLists;
    private static UserRestrictionsViewModel userRestrictionsViewModel = null;
    private FragmentProfileScreenBinding binding;

    //todo: implement help, profiles and modify
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Bind to xml layout and create db
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        userRestrictionsViewModel = new ViewModelProvider(this).get(UserRestrictionsViewModel.class);

        //Set all button listeners
        binding.backBtn.setOnClickListener($ -> {
            NavHostFragment.findNavController(ProfileScreenFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
        });

        binding.helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileScreenFragment.this.getActivity(), PopupHelp.class));
            }
        });

        binding.profilesBtn.setOnClickListener($ -> {
            NavHostFragment.findNavController(ProfileScreenFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
        });

        binding.modifyBtn.setOnClickListener($ -> {
            NavHostFragment.findNavController(ProfileScreenFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
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

        //setup everything needed for restrictions list
        RecyclerView recyclerView = binding.recyclerview;
        final UserAdapter adapter = new UserAdapter(new UserAdapter.UserRestrictionDiff(), getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));

        // Update the cached copy of the words in the adapter.
        userRestrictionsViewModel.getAllRestrictions().observe(getViewLifecycleOwner(), adapter::submitList);

        return binding.getRoot();

    }


//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
////        ImageButton peopleBtn = binding.peopleButton;
////        ImageButton cameraBtn = binding.cameraButton;
////        ImageButton editBtn = binding.editButton;
////        Button settings = binding.settingsBtn;
//        ImageButton help = binding.helpBtn;
//
//        help.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ProfileScreenFragment.this.getActivity(), PopupHelp.class));
//            }
//        });
//
//        peopleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });

//        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(FirstFragment.this);
//        integrator.setOrientationLocked(false); // don't force landscape
//
//        // we can add more listeners if needed
//        cameraBtn.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View view) {
//                Log.d(LOG_TAG, "Camera button clicked");
//                integrator.initiateScan();
//            }
//        });
//    }

    /**
     * Static method which returns the current userRestrictionsViewModel
     * @return an instance of userRestrictionsViewModel
     */
    public static UserRestrictionsViewModel getUserRestrictionsViewModel() {
        return userRestrictionsViewModel;
    }
}