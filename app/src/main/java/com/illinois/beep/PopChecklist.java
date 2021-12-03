package com.illinois.beep;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PopChecklist extends Activity {
    List<CheckBox> persons;
    Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Correctly size the popup
        setContentView(R.layout.popup_window);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.8));

        LinearLayout ll = findViewById(R.id.layout);
        persons = new ArrayList<>();

        //Add All checkboxes to UI
        for(String name:MainActivity.getUserRestrictionsViewModel().getAllUsers()){
            //Create checkbox and customize its style
            CheckBox temp = new CheckBox(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            temp.setLayoutParams(lp);
            temp.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal)));
            temp.setText(name);
            //Listener updates PreferenceManager and displays toast
            temp.setOnClickListener($ ->
                    {
                        boolean checked = temp.isChecked();
                        PreferenceManager.getDefaultSharedPreferences(this).edit()
                                .putBoolean(name, checked).commit();
                        if(checked)
                            Toast.makeText(this, "Selected " + name, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this, "Deselected " + name, Toast.LENGTH_SHORT).show();
                    }
            );
            persons.add(temp);
            ll.addView(temp, persons.size()+1);
        }

        clearBtn = findViewById(R.id.clear_button);

        // CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);
        for(int i = 0; i < persons.size(); i++) {
            boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean(persons.get(i).getText().toString(), false);
            Log.d("Checked:", String.valueOf(persons.get(i).isChecked()));
            Log.d("Size:", String.valueOf(persons.size()));
            persons.get(i).setChecked(checked);
        }
    }

    public void saveChecklist(View view) {
        //Clear all selections
        for(int i = 0; i < persons.size(); i++) {
            CheckBox curr = persons.get(i);
            if (curr.isChecked())
                curr.setChecked(false);
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putBoolean(persons.get(i).getText().toString(), false).commit();
        }

        Toast.makeText(this, "Cleared selections" , Toast.LENGTH_SHORT).show();
    }
}
