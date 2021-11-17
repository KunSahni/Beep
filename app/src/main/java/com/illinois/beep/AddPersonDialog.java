package com.illinois.beep;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illinois.beep.database.AppDatabase;
import com.illinois.beep.database.ConcreteAppDatabase;
import com.illinois.beep.database.User;

public class AddPersonDialog extends Dialog implements android.view.View.OnClickListener {

    private Activity c;
    private Dialog d;
    private FloatingActionButton cancelButton;
    private MaterialButton submitButton;
    private EditText newNameEdit;

    public AddPersonDialog(Activity a) {
        super(a);
        this.c = a;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.insert_name_popup);
        cancelButton = findViewById(R.id.cancel_btn);
        submitButton = findViewById(R.id.submit_btn);
        newNameEdit = findViewById(R.id.new_name_edit);
        cancelButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.submit_btn:
                String newName = newNameEdit.getText().toString();
                AppDatabase db = ConcreteAppDatabase.getInstance(getContext());
                db.userDao().insertOne(new User(newName));
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