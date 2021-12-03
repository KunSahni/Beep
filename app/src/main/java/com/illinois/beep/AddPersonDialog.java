package com.illinois.beep;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illinois.beep.database.UserRestriction;
import com.illinois.beep.database.UserRestrictionsViewModel;

/**
 * This class is a custom dialog presented to user when he wants to add a person to the app
 */
public class AddPersonDialog extends Dialog {

    private EditText newNameEdit;

    /**
     *
     * @param activity activity in which the popup will be displayed
     */
    public AddPersonDialog(FragmentActivity activity) {
        super(activity);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.insert_name_popup);

        //Find all UI elements
        FloatingActionButton cancelButton = findViewById(R.id.cancel_btn);
        MaterialButton submitButton = findViewById(R.id.submit_btn);
        newNameEdit = findViewById(R.id.new_name_edit);

        //Listener adds new person to profile and closes popup
        submitButton.setOnClickListener($ ->{
            String newName = newNameEdit.getText().toString();
            UserRestrictionsViewModel userRestrictionsViewModel = ProfileScreenFragment.getUserRestrictionsViewModel();
            userRestrictionsViewModel.insert(new UserRestriction(newName, "add"));
            ProfileScreenFragment.refreshData();
            dismiss();
        });

        cancelButton.setOnClickListener($ -> dismiss());
    }

}