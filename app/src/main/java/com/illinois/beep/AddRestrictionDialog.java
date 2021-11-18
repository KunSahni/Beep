package com.illinois.beep;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illinois.beep.database.AddRestrictionListAdapter;
import com.illinois.beep.database.RestrictionDatabase;
import com.illinois.beep.database.UserRestrictionsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a custom dialog presented to user when he wants to add a restriction
 * to one of the existing users on the app
 */
public class AddRestrictionDialog extends Dialog implements
        android.view.View.OnClickListener {

    private final FragmentActivity c;
    private String personName;
    private Dialog d;
    private FloatingActionButton cancelButton;
    private final ArrayList<String> availableRestrictions;
    private final UserRestrictionsViewModel userRestrictionsViewModel;

    public AddRestrictionDialog(FragmentActivity a, String personName) {
        super(a);
        this.c = a;
        this.personName = personName;
        //Create a list of possible restriction excluding the ones user already has
        userRestrictionsViewModel = ProfileScreenActivity.getUserRestrictionsViewModel();
        List<String> currentRestriction = userRestrictionsViewModel.getRestrictions(personName);
        List<String> allRestrictions = new ArrayList<>(RestrictionDatabase.getRestrictions());
        for(String curRes:currentRestriction)
            if(curRes != null)
                allRestrictions.remove(curRes);
        availableRestrictions = new ArrayList<>(allRestrictions);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_restriction_popup);
        cancelButton = findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.available_restrictions_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        AddRestrictionListAdapter adapter = new AddRestrictionListAdapter(c, this, availableRestrictions, personName);
        recyclerView.setAdapter(adapter);
    }

    //todo: implement class
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}