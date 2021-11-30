package com.illinois.beep;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.illinois.beep.database.UserRestriction;
import com.illinois.beep.databinding.FragmentFirstBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.core.widget.CompoundButtonCompat;
import androidx.lifecycle.ViewModelProvider;

public class PopChecklist extends Activity {
    CheckBox cb1, cb2, cb3, cb4, cb5;
    List<CheckBox> persons;
    Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            CheckBox temp = new CheckBox(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            temp.setLayoutParams(lp);
            if (Build.VERSION.SDK_INT < 21) {
                CompoundButtonCompat.setButtonTintList(temp, ColorStateList.valueOf(getResources().getColor(R.color.teal)));//Use android.support.v4.widget.CompoundButtonCompat when necessary else
            } else {
                temp.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.teal)));//setButtonTintList is accessible directly on API>19
            }
            temp.setText(name);
            temp.setOnClickListener($ ->
                    {
                        Boolean checked = temp.isChecked();
                        PreferenceManager.getDefaultSharedPreferences(this).edit()
                                .putBoolean(name, checked).commit();
                        Toast.makeText(this, "Selected " + name, Toast.LENGTH_SHORT).show();
                    }
            );
            persons.add(temp);
            ll.addView(temp, persons.size()+1);
        }


        /*cb1 = findViewById(R.id.check_myself);
        cb2 = findViewById(R.id.check_allie);
        cb3 = findViewById(R.id.check_jim);
        cb4 = findViewById(R.id.check_alex);
        cb5 = findViewById(R.id.check_sarah);
        ArrayList<CheckBox> checkBoxes= new ArrayList<CheckBox>(Arrays.asList(
                cb1,
                cb2,
                cb3,
                cb4,
                cb5));*/
        clearBtn = findViewById(R.id.clear_button);

        // CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);
        for(int i = 0; i < persons.size(); i++) {
            boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean(String.valueOf(persons.get(i).getText().toString()), false);
            Log.d("Checked:", String.valueOf(persons.get(i).isChecked()));
            Log.d("Size:", String.valueOf(persons.size()));
            persons.get(i).setChecked(checked);
        }
    }

    public void saveChecklist(View view) {
        /*CheckBox cb1, cb2, cb3, cb4, cb5;
        cb1 = findViewById(R.id.check_myself);
        cb2 = findViewById(R.id.check_allie);
        cb3 = findViewById(R.id.check_jim);
        cb4 = findViewById(R.id.check_alex);
        cb5 = findViewById(R.id.check_sarah);

        ArrayList<CheckBox> checkBoxes= new ArrayList<CheckBox>(Arrays.asList(
                cb1,
                cb2,
                cb3,
                cb4,
                cb5));*/

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
