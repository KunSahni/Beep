package com.illinois.beep.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.illinois.beep.R;

class UserViewHolder extends RecyclerView.ViewHolder {
    private final TextView userName;
    public static RecyclerView childRecyclerView = null;

    public UserViewHolder(View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.user_name);
        childRecyclerView = itemView.findViewById(R.id.recyclerview);
    }

    public void bind(String text) {
        userName.setText(text);
    }

    static UserViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_recyclerview_item, parent, false);
        return new UserViewHolder(view);
    }
}