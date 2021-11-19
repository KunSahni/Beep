package com.illinois.beep;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.illinois.beep.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.illinois.beep.database.Product;
import com.illinois.beep.database.ProductDatabase;
import com.illinois.beep.database.RestrictionDatabase;
import com.illinois.beep.databinding.FragmentTestBinding;

// Reference tutorial: https://code.luasoftware.com/tutorials/android/android-scan-qrcode-library/
// as well as ZXING documentation


public class FirstFragment extends Fragment {

    private String LOG_TAG = "Test";
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
        MyListAdapter adapter = new MyListAdapter(binding.getRoot().getContext(), requireActivity(), new ArrayList<>());
        l.setAdapter(adapter);

        MyListViewModel myListViewModel = new ViewModelProvider(requireActivity()).get(MyListViewModel.class);

        myListViewModel.getMyList().observe(getViewLifecycleOwner(), adapter::updateMyList);

        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textviewTest.setOnClickListener(v -> {
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_TestFragment);
        });

        binding.textviewFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        ImageButton peopleBtn = binding.peopleButton;
        ImageButton cameraBtn = binding.cameraButton;
        ImageButton editBtn = binding.editButton;

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstFragment.this.getActivity(),Pop.class));
            }
        });

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Thank you: https://stackoverflow.com/questions/37251583/how-to-start-zxing-on-a-fragment :))
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // TODO: Do something if result fails
        String barcode = result.getContents();
        Log.d(LOG_TAG, "Barcode is " + barcode);
        Product matchedProduct = ProductDatabase.get(barcode);
        Log.d(LOG_TAG, "We matched with " + matchedProduct.getImage_url());

        MyListViewModel myListViewModel = new ViewModelProvider(requireActivity()).get(MyListViewModel.class);
        List<MyListItem> list = myListViewModel.getMyList().getValue();
        assert list != null;
        list.add(new MyListItem(matchedProduct, 1));
    }

}