package com.illinois.beep;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.illinois.beep.database.UserAdapter;
import com.illinois.beep.database.UserRestriction;
import com.illinois.beep.database.UserRestrictionsViewModel;
import com.illinois.beep.databinding.FragmentProfileScreenBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This fragment manages all profile related interactions.
 */
public class ProfileScreenFragment extends Fragment{
    private static UserRestrictionsViewModel userRestrictionsViewModel = null;
    private static FragmentProfileScreenBinding binding;
    private static UserAdapter regularAdapter;
    private static UserAdapter editAdapter;
    private static final int PICK_PHOTO_FOR_AVATAR = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Bind to xml layout
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false);

        //Back button listener navigates back to my list screen
        binding.backBtn.setOnClickListener($ -> NavHostFragment.findNavController(ProfileScreenFragment.this)
                .navigate(R.id.action_SecondFragment_to_FirstFragment));

        //Help button listener displays help popup
        binding.helpBtn.setOnClickListener(view -> startActivity(new Intent(ProfileScreenFragment.this.getActivity(), PopupHelp.class)));

        //Image listener lets user pick a profile picture
        binding.userPicture.setOnClickListener($ -> pickImage());
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        loadImageFromStorage(cw.getDir("imageDir", Context.MODE_PRIVATE).getAbsolutePath());

        //Profile button listener opens RemovePersonDialog
        binding.profilesBtn.setOnClickListener($ -> {
            //int width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
            //int height = (int)(getResources().getDisplayMetrics().heightPixels*0.65);

            RemovePersonDialog removePersonDialog = new RemovePersonDialog(getActivity());
            removePersonDialog.show();
            //removePersonDialog.getWindow().setLayout(width, height);
        });

        //Modify button listener switches back and forth between
        // regular and edit mode for restrictions in RecyclerView
        binding.modifyBtn.setOnClickListener($ -> {
            if(binding.modifyBtn.getText().toString().equals(getString(R.string.edit_restrictions_cue)))
                editMode();
            else
                regularMode();
        });

        //Add person button listener opens AddPersonDialog
        binding.addPersonBtn.setOnClickListener($ ->{
            int width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.45);

            AddPersonDialog addPersonDialog = new AddPersonDialog(getActivity());
            addPersonDialog.show();
            addPersonDialog.getWindow().setLayout(width, height);
        });

        populateRecycler();

        return binding.getRoot();

    }


    /**
     * Static method which returns the current userRestrictionsViewModel
     * @return an instance of userRestrictionsViewModel
     */
    public static UserRestrictionsViewModel getUserRestrictionsViewModel() {
        return MainActivity.getUserRestrictionsViewModel();
    }

    public static void refreshData () {
        regularAdapter.notifyDataSetChanged();
    }

    /**
     * This method switches RecyclerView to edit mode.
     * Pretty much this means that the adapter associate with the RecyclerView gets changed and
     * the new adapter redraws all restrictions with a delete button instead of a star button.
     * This also means that user can delete restriction when profile is in edit mode.
     */
    protected void editMode() {
        new Thread() {
            @Override
            public void run() {
                userRestrictionsViewModel = MainActivity.getUserRestrictionsViewModel();
                try {
                    // code runs in a thread
                    getActivity().runOnUiThread(() -> {
                        //setup everything needed for restrictions list
                        RecyclerView recyclerView = binding.recyclerview;
                        editAdapter = new UserAdapter(new UserAdapter.UserRestrictionDiff(), getActivity(), true);
                        //Modify button text
                        binding.modifyBtn.setText(R.string.edit_restrictions_done);
                        //Redraw recyclerview
                        recyclerView.setAdapter(null);
                        recyclerView.setLayoutManager(null);
                        recyclerView.setAdapter(editAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
                        // Observe updates on restrictions
                        userRestrictionsViewModel.getAllRestrictions().observe(getViewLifecycleOwner(), restrictions -> {
                            List<UserRestriction> onlyNames = new ArrayList<>();
                            for (UserRestriction u:restrictions)
                                if(u.getRestriction().equals("add"))
                                    onlyNames.add(u);
                            editAdapter.submitList(onlyNames);
                            //Force adapter to be redrawn at  each update
                            recyclerView.setAdapter(null);
                            recyclerView.setLayoutManager(null);
                            recyclerView.setAdapter(editAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
                            editAdapter.notifyDataSetChanged();
                        });
                        editAdapter.notifyDataSetChanged();
                    });
                } catch (final Exception ex) {
                    Log.i("---","Exception in thread");
                }
            }
        }.start();
    }

    /**
     * On top of calling populateRecycler(), this method also changes the text on modify button.
     */
    protected void regularMode() {
        new Thread() {
            @Override
            public void run() {
                userRestrictionsViewModel = MainActivity.getUserRestrictionsViewModel();
                try {
                    // code runs in a thread
                    getActivity().runOnUiThread(() -> {
                        //Modify button text
                        binding.modifyBtn.setText(R.string.edit_restrictions_cue);
                        populateRecycler();
                    });
                } catch (final Exception ex) {
                    Log.i("---","Exception in thread");
                }
            }
        }.start();
    }

    /**
     * This method switches RecyclerView to regular mode.
     * Pretty much this means that the adapter associate with the RecyclerView gets changed and
     * the new adapter redraws all restrictions with regular star buttons.
     */
    private void populateRecycler() {
        new Thread() {
            @Override
            public void run() {

                userRestrictionsViewModel = MainActivity.getUserRestrictionsViewModel();
                try {

                    // code runs in a thread
                    getActivity().runOnUiThread(() -> {
                        //setup everything needed for restrictions list
                        RecyclerView recyclerView = binding.recyclerview;
                        regularAdapter = new UserAdapter(new UserAdapter.UserRestrictionDiff(), getActivity(), false);
                        recyclerView.setAdapter(regularAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
                        // Observe updates on restrictions
                        userRestrictionsViewModel.getAllRestrictions().observe(getViewLifecycleOwner(), restrictions -> {
                            List<UserRestriction> onlyNames = new ArrayList<>();
                            for (UserRestriction u:restrictions)
                                if(u.getRestriction().equals("add"))
                                    onlyNames.add(u);
                            regularAdapter.submitList(onlyNames);
                            //Force adapter to be redrawn at  each update
                            recyclerView.setAdapter(null);
                            recyclerView.setLayoutManager(null);
                            recyclerView.setAdapter(regularAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
                            regularAdapter.notifyDataSetChanged();
                        });
                        regularAdapter.notifyDataSetChanged();
                    });
                } catch (final Exception ex) {
                    Log.i("---","Exception in thread");
                }
            }
        }.start();
    }


    /**
     * This method sets an Intent in order to pick user profile picture
     */
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    /**
     * This method manages user selection in regards to profile picture
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                saveToInternalStorage(bmp);
                binding.userPicture.setImageBitmap(bmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method saves a Bitmap image to local storage
     * @param bitmapImage the image which you want to save
     * @return the path where image was saved
     */
    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File myPath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    /**
     * Reads image from path passed as parameter
     * @param path path from which you want to read an image
     */
    private void loadImageFromStorage(String path)
    {
        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            binding.userPicture.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
        }

    }
}