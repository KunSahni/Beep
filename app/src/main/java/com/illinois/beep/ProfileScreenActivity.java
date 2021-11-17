package com.illinois.beep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        //Find all UI elements
        backButton = findViewById(R.id.back_btn);
        helpButton = findViewById(R.id.help_btn);
        profilesButton = findViewById(R.id.profiles_btn);
        modifyButton = findViewById(R.id.modify_btn);
        addPersonButton = findViewById(R.id.add_person_btn);
        userPicture = findViewById(R.id.user_picture);
        userName = findViewById(R.id.username_edit);
        restrictionsLayout = findViewById(R.id.restrictions_linear_layout);
        restrictionLists = new ArrayList<>();
        restrictionLists.add(findViewById(R.id.user1_list));

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshScreen();
    }

    private void refreshScreen(){
        //todo: use RestrictionListViewAdapter
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
                AddPersonDialog cdd = new AddPersonDialog(this);
                cdd.show();
                break;
        }
    }
}