package com.illinois.beep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illinois.beep.database.ProductDatabase;
import com.illinois.beep.database.RestrictionDatabase;
import com.illinois.beep.database.UserAdapter;
import com.illinois.beep.database.UserRestrictionsViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * This class manages all interactions which happen on profile screen
 */
public class ProfileScreenActivity extends AppCompatActivity implements View.OnClickListener{
    FloatingActionButton backButton;
    FloatingActionButton helpButton;
    FloatingActionButton profilesButton;
    MaterialButton modifyButton;
    MaterialButton addPersonButton;
    ImageView userPicture;
    EditText userName;
    LinearLayout restrictionsLayout;
    ArrayList<ListView> restrictionLists;
    private static UserRestrictionsViewModel userRestrictionsViewModel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        userRestrictionsViewModel = new ViewModelProvider(this).get(UserRestrictionsViewModel.class);

        //todo: remove code from here after testing
        try {
            AssetManager am = getApplicationContext().getAssets();
            InputStream productData = am.open("products.json");
            InputStream restrictionData = am.open("restrictions.json");
            ProductDatabase.loadData(productData);
            RestrictionDatabase.loadData(restrictionData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Find all UI elements
        backButton = findViewById(R.id.back_btn);
        backButton.setOnClickListener(this);
        helpButton = findViewById(R.id.help_btn);
        helpButton.setOnClickListener(this);
        profilesButton = findViewById(R.id.profiles_btn);
        profilesButton.setOnClickListener(this);
        modifyButton = findViewById(R.id.modify_btn);
        modifyButton.setOnClickListener(this);
        addPersonButton = findViewById(R.id.add_person_btn);
        addPersonButton.setOnClickListener(this);

        userPicture = findViewById(R.id.user_picture);
        userName = findViewById(R.id.username_edit);
        restrictionsLayout = findViewById(R.id.restrictions_linear_layout);
        restrictionLists = new ArrayList<>();
        //restrictionLists.add(findViewById(R.id.user1_list));

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final UserAdapter adapter = new UserAdapter(new UserAdapter.UserRestrictionDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Update the cached copy of the words in the adapter.
        userRestrictionsViewModel.getAllRestrictions().observe(this, adapter::submitList);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //todo: implement help, profiles and modify
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.help_btn:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.profiles_btn:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.modify_btn:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.add_person_btn:
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.35);

                AddPersonDialog addPersonDialog = new AddPersonDialog(this);
                addPersonDialog.show();
                addPersonDialog.getWindow().setLayout(width, height);
                break;
        }
    }

    /**
     * Static method which returns the current userRestrictionsViewModel
     * @return an instance of userRestrictionsViewModel
     */
    public static UserRestrictionsViewModel getUserRestrictionsViewModel() {
        return userRestrictionsViewModel;
    }
}