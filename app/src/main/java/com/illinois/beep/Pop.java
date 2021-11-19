package com.illinois.beep;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.illinois.beep.databinding.FragmentFirstBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.lifecycle.ViewModelProvider;

public class Pop extends Activity {
    CheckBox cb1, cb2, cb3, cb4, cb5;
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
                cb5));
        clearBtn = findViewById(R.id.clear_button);

//        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
//                .getBoolean("check_myself", false);
//        cb1.setChecked(checked);
//        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
//                .getBoolean("checkBox1", false);
//        cb2.setChecked(checked);
//        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
//                .getBoolean("checkBox1", false);
//        cb3.setChecked(checked);
//        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
//                .getBoolean("checkBox1", false);
//        cb4.setChecked(checked);
//        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
//                .getBoolean("checkBox1", false);
//        cb5.setChecked(checked);

        // CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);
        for(int i = 0; i < checkBoxes.size(); i++) {
            boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
                    .getBoolean(String.valueOf(checkBoxes.get(i).getId()), false);
            Log.d("Checked:", String.valueOf(checkBoxes.get(i).isChecked()));
            Log.d("Size:", String.valueOf(checkBoxes.size()));
            checkBoxes.get(i).setChecked(checked);
        }
//        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
//                .getBoolean(cb1.getText().toString(), false);
//        cb1.setChecked(checked);
//        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
//                .getBoolean("checkBox1", false);
//        cb2.setChecked(checked);
//        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
//                .getBoolean("checkBox1", false);
//        cb3.setChecked(checked);
//        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
//                .getBoolean("checkBox1", false);
//        cb4.setChecked(checked);
//        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
//                .getBoolean("checkBox1", false);
//        cb5.setChecked(checked);

//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                StringBuffer result = new StringBuffer();
//                result.append("Selected: ");
//                if(cb1.isChecked())
//                    result.append("\n" + cb1.getText().toString());
//
//                if(cb2.isChecked())
//                    result.append("\n" + cb1.getText().toString());
//
//                if(cb3.isChecked())
//                    result.append("\n" + cb1.getText().toString());
//
//                if(cb4.isChecked())
//                    result.append("\n" + cb1.getText().toString());
//
//                if(cb5.isChecked())
//                    result.append("\n" + cb1.getText().toString());
//
//                if (!cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked()
//                        && !cb4.isChecked() && !cb5.isChecked())
//                    result.append("\nNone");
//            }
//        });
    }

    public void onCheckboxClicked(View view) {
        boolean checked;
        CheckBox cb;
//        if (checked) {
//            Toast.makeText(this, "Selected " + cb.getText(), Toast.LENGTH_SHORT).show();
//            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(cb.getText().toString(), checked).commit();
//        }

        switch(view.getId()) {
            case R.id.check_myself:
                cb = findViewById(R.id.check_myself);
                checked = cb.isChecked();
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean(String.valueOf(findViewById(R.id.check_myself).getId()), checked).commit();
                Toast.makeText(this, "Selected myself", Toast.LENGTH_SHORT).show();
                break;
            case R.id.check_allie:
                cb = findViewById(R.id.check_allie);
                checked = cb.isChecked();
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean(String.valueOf(findViewById(R.id.check_allie).getId()), checked).commit();
                Toast.makeText(this, "Selected myself", Toast.LENGTH_SHORT).show();
                break;
            case R.id.check_jim:
                cb = findViewById(R.id.check_jim);
                checked = cb.isChecked();
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean(String.valueOf(findViewById(R.id.check_jim).getId()), checked).commit();
                Toast.makeText(this, "Selected Jim", Toast.LENGTH_SHORT).show();
                break;
            case R.id.check_alex:
                cb = findViewById(R.id.check_alex);
                checked = cb.isChecked();
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean(String.valueOf(findViewById(R.id.check_alex).getId()), checked).commit();
                Toast.makeText(this, "Selected Alex", Toast.LENGTH_SHORT).show();
                break;
            case R.id.check_sarah:
                cb = findViewById(R.id.check_sarah);
                checked = cb.isChecked();
                PreferenceManager.getDefaultSharedPreferences(this).edit()
                        .putBoolean(String.valueOf(findViewById(R.id.check_sarah).getId()), checked).commit();
                Toast.makeText(this, "Selected Sarah", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void saveChecklist(View view) {
        CheckBox cb1, cb2, cb3, cb4, cb5;
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
                cb5));

        for(int i = 0; i < checkBoxes.size(); i++) {
            CheckBox curr = checkBoxes.get(i);
            if (curr.isChecked())
                curr.setChecked(false);
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putBoolean(String.valueOf(checkBoxes.get(i).getId()), false).commit();
        }

        Toast.makeText(this, "Cleared selections" , Toast.LENGTH_SHORT).show();
    }
}
