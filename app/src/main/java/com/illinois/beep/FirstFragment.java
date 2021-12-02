package com.illinois.beep;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.illinois.beep.database.UserRestriction;
import com.illinois.beep.database.UserRestrictionsViewModel;
import com.illinois.beep.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;

// Reference tutorial: https://code.luasoftware.com/tutorials/android/android-scan-qrcode-library/
// as well as ZXING documentation


public class FirstFragment extends Fragment {

    private String LOG_TAG = "Test";
    private FragmentFirstBinding binding;
    private ListView l;
    Set<String> dangerRestrictions = new HashSet<>();
    Set<String> warningRestrictions = new HashSet<>();
    MyListAdapter adapter;

    private final SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = (sharedPreferences, key) -> {
        recalculateRestrictions(sharedPreferences);
        adapter.updateRestrictions(warningRestrictions, dangerRestrictions);
    };

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        l = view.findViewById(R.id.my_list_view);
        adapter = new MyListAdapter(binding.getRoot().getContext(), requireActivity(), FirstFragment.this, new ArrayList<>());
        l.setAdapter(adapter);

        MyListViewModel myListViewModel = new ViewModelProvider(requireActivity()).get(MyListViewModel.class);

        myListViewModel.getMyList().observe(getViewLifecycleOwner(), adapter::updateMyList);


        recalculateRestrictions(PreferenceManager.getDefaultSharedPreferences(binding.getRoot().getContext()));
        adapter.updateRestrictions(warningRestrictions, dangerRestrictions);

        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton peopleBtn = binding.peopleButton;
        ImageButton cameraBtn = binding.cameraButton;
        ImageButton editBtn = binding.editButton;
        Button settings = binding.settingsBtn;

        binding.textviewTest.setOnClickListener(v -> {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_TestFragment);
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstFragment.this.getActivity(), PopChecklist.class));
            }
        });

        peopleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

//        editBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(FirstFragment.this.getActivity(),Pop.class));
//            }
//        });

        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(FirstFragment.this);
        integrator.setOrientationLocked(false); // don't force landscape


//        final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
//                result -> {
//                    if(result.getContents() == null) {
//                        Toast.makeText(MyActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(MyActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
//                    }
//                });

        // we can add more listeners if needed
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Camera button clicked");
                integrator.initiateScan();
            }
        });

        PreferenceManager.getDefaultSharedPreferences(binding.getRoot().getContext()).registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreferenceManager.getDefaultSharedPreferences(binding.getRoot().getContext()).unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);

        binding = null;
    }

    // Thank you: https://stackoverflow.com/questions/37251583/how-to-start-zxing-on-a-fragment :))
    // https://stackoverflow.com/questions/31091328/zxing-qr-code-scanner-embedded-pressing-back-button-during-scan-issue
    // https://developer.android.com/training/basics/intents/result
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result == null || resultCode != Activity.RESULT_OK) {
            Log.d(LOG_TAG, "Null case");
            Log.d(LOG_TAG, "Fails: " + result.getContents());
            return;
        }

        Log.d(LOG_TAG, "Passes: " + result.getContents());
        // TODO: Do something if result fails
        String barcode = result.getContents();
        Log.d(LOG_TAG, "Barcode is " + barcode);
        Product matchedProduct = ProductDatabase.get(barcode);
        if (matchedProduct == null) {
            Log.d(LOG_TAG, "Product doesn't exist");
            Toast.makeText(getActivity(), "Product doesn't exist in store", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(LOG_TAG, "We matched with " + matchedProduct.getImage_url());

        MyListViewModel myListViewModel = new ViewModelProvider(requireActivity()).get(MyListViewModel.class);
        MutableLiveData<List<MyListItem>> liveList = myListViewModel.getMyList();
        List<MyListItem> list = liveList.getValue();
        assert list != null;
        list.add(new MyListItem(matchedProduct, 1));
        liveList.postValue(list);
    }

    private void recalculateRestrictions(SharedPreferences sharedPreferences) {
        warningRestrictions.clear();
        dangerRestrictions.clear();

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
                } else {
                    warningRestrictions.add(userRestriction.getRestriction());
                }
            }
        }
    }
}