package com.illinois.beep;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

import java.util.ArrayList;

import androidx.lifecycle.ViewModelProvider;

public class Pop extends Activity {
    //CheckBox cb1, cb2, cb3, cb4, cb5;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.9), (int)(height*0.8));

//        cb1 = findViewById(R.id.check_myself);
//        cb2 = findViewById(R.id.check_allie);
//        cb3 = findViewById(R.id.check_jim);
//        cb4 = findViewById(R.id.check_alex);
//        cb5 = findViewById(R.id.check_sarah);
//        saveBtn = findViewById(R.id.save_button);
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

//    public void checkRadioButton(View v) {
//        int radioId = radioGroup.getCheckedRadioButtonId();
//        radioButton = findViewById(radioId);
//        Toast.makeText(this, "Selected " + radioButton.getText(), Toast.LENGTH_SHORT).show();
//    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        CheckBox cb = findViewById(view.getId());
        if (checked)
            Toast.makeText(this, "Selected " + cb.getText(), Toast.LENGTH_SHORT).show();
    }

    public void saveChecklist(View view) {
        Toast.makeText(this, "Saved selections" , Toast.LENGTH_SHORT).show();
    }
}
