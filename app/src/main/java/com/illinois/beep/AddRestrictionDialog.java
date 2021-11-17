package com.illinois.beep;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.room.Room;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illinois.beep.database.AppDatabase;
import com.illinois.beep.database.ConcreteAppDatabase;

/**
 * This class is a custom dialog presented to user when he wants to add a restriction
 * to one of the existing users on the app
 */
public class AddRestrictionDialog extends Dialog implements
        android.view.View.OnClickListener {

    private Activity c;
    private String personName;
    private Dialog d;
    private FloatingActionButton cancelButton;
    private MaterialButton submitButton;
    private EditText newNameEdit;

    public AddRestrictionDialog(Activity a, String personName) {
        super(a);
        this.c = a;
        this.personName = personName;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_restriction_popup);
        cancelButton = findViewById(R.id.cancel_btn);
        submitButton = findViewById(R.id.submit_btn);
        newNameEdit = findViewById(R.id.new_name_edit);
        cancelButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    //todo: implement class
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.submit_btn:
                String newName = newNameEdit.getText().toString();
                AppDatabase db = ConcreteAppDatabase.getInstance(c);
                c.finish();
                break;
            case R.id.cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}