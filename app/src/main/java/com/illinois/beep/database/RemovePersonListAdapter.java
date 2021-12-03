package com.illinois.beep.database;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illinois.beep.ProfileScreenFragment;
import com.illinois.beep.R;

import java.util.List;

/**
 * This is an adapter which manages the RecyclerView used to remove persons.
 * The class inflates the layout and manages all interactions with UI elements.
 */
public class RemovePersonListAdapter extends RecyclerView.Adapter<RemovePersonListAdapter.ViewHolder> {

    private final Dialog parent;
    private final List<String> mData;
    private final LayoutInflater mInflater;

    // data is passed into the constructor
    public RemovePersonListAdapter(Context context, Dialog parent, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.parent = parent;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.remove_user_popup_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String personName = mData.get(position);
        holder.personName.setText(personName);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView personName;
        FloatingActionButton removeButton;

        ViewHolder(View itemView) {
            super(itemView);
            personName = itemView.findViewById(R.id.user_name);
            removeButton = itemView.findViewById(R.id.remove_btn);
            removeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ProfileScreenFragment.getUserRestrictionsViewModel().delete(personName.getText().toString());
            parent.dismiss();
        }
    }
}