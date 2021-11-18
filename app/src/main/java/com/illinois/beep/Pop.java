package com.illinois.beep;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.illinois.beep.databinding.FragmentFirstBinding;

import java.util.ArrayList;

import androidx.lifecycle.ViewModelProvider;

public class Pop extends Activity {
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window);

        //radioGroup = findViewById(R.id.radioGroup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.9), (int)(height*0.8));
    }

    public void checkRadioButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "Selected " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.check_myself:
                if (checked)
                    Toast.makeText(this, "Selected myself", Toast.LENGTH_SHORT).show();
            else
                // Remove the meat
                break;
            case R.id.check_allie:
                if (checked)
                    Toast.makeText(this, "Selected Allie", Toast.LENGTH_SHORT).show();
            else
                // I'm lactose intolerant
                break;
            case R.id.check_jim:
                if (checked)
                    Toast.makeText(this, "Selected Jim", Toast.LENGTH_SHORT).show();
                else
                    // I'm lactose intolerant
                    break;
            case R.id.check_alex:
                if (checked)
                    Toast.makeText(this, "Selected Alex", Toast.LENGTH_SHORT).show();
                else
                    // I'm lactose intolerant
                    break;
            case R.id.check_sarah:
                if (checked)
                    Toast.makeText(this, "Selected Sarah", Toast.LENGTH_SHORT).show();
                else
                    // I'm lactose intolerant
                    break;
        }
    }
}
