package com.illinois.beep;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illinois.beep.database.RemovePersonListAdapter;
import com.illinois.beep.database.UserRestrictionsViewModel;

import java.util.List;

/**
 * This class is a custom dialog presented to user when he wants to add a restriction
 * to one of the existing users on the app
 */
public class RemovePersonDialog extends Dialog implements
        android.view.View.OnClickListener {

    private final FragmentActivity c;
    private final List<String> currentUsers;

    public RemovePersonDialog(FragmentActivity a) {
        super(a);
        this.c = a;
        UserRestrictionsViewModel userRestrictionsViewModel = ProfileScreenFragment.getUserRestrictionsViewModel();
        currentUsers = userRestrictionsViewModel.getAllUsers();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.remove_user_popup);
        FloatingActionButton cancelButton = findViewById(R.id.cancel_btn);
        cancelButton.setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.current_users_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        RemovePersonListAdapter adapter = new RemovePersonListAdapter(c, this, currentUsers);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.cancel_btn) {
            dismiss();
        }
        dismiss();
    }
}