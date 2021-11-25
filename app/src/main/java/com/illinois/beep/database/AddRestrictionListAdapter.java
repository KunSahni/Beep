package com.illinois.beep.database;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.illinois.beep.ProfileScreenFragment;
import com.illinois.beep.R;

import java.util.List;

public class AddRestrictionListAdapter extends RecyclerView.Adapter<AddRestrictionListAdapter.ViewHolder> {

    private final String personName;
    private final Dialog parent;
    private final List<String> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public AddRestrictionListAdapter(Context context, Dialog parent, List<String> data, String personName) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.personName = personName;
        this.parent = parent;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.add_restriction_popup_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String restriction = mData.get(position);
        holder.restriction.setText(restriction);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView restriction;

        ViewHolder(View itemView) {
            super(itemView);
            restriction = itemView.findViewById(R.id.available_restriction_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ProfileScreenFragment.getUserRestrictionsViewModel().insert(new UserRestriction(personName, restriction.getText().toString()));
            parent.dismiss();
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}