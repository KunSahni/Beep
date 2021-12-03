package com.illinois.beep.database;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illinois.beep.AddRestrictionDialog;
import com.illinois.beep.ProfileScreenFragment;
import com.illinois.beep.R;

import java.util.List;

/**
 * This is the adapter which manages the inner RecyclerView used to display restrictions
 * for a specific user.
 * The class inflates the layout and manages all interactions with UI elements.
 */
public class UserRestrictionAdapter extends ListAdapter<UserRestriction, UserRestrictionAdapter.UserRestrictionViewHolder> {

    private final List<UserRestriction> userRestrictionList;
    private final FragmentActivity activity;
    private final boolean isEditMode;

    /**
     *
     * @param userRestrictionList list of current restrictions
     * @param activity activity in which the adapter is used
     * @param isEditMode true if adapter should display edit mode, false if it should display regular mode
     */
    UserRestrictionAdapter(@NonNull DiffUtil.ItemCallback<UserRestriction> diffCallback, List<UserRestriction> userRestrictionList, FragmentActivity activity, boolean isEditMode)
    {
        super(diffCallback);
        this.userRestrictionList = userRestrictionList;
        this.activity = activity;
        this.isEditMode = isEditMode;
    }

    @NonNull
    @Override
    public UserRestrictionViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(
                        R.layout.user_restriction_recyclerview_item,
                        viewGroup, false);

        return new UserRestrictionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull UserRestrictionViewHolder userRestrictionViewHolder,
            int position)
    {

        //Get the current restriction
        UserRestriction currentRestriction = userRestrictionList.get(position);

        //Based on the restriction personalize the content of the row
        if(currentRestriction.getRestriction().equals("add")) {
            userRestrictionViewHolder
                    .restrictionName
                    .setText(R.string.add_restriction_cue);

            userRestrictionViewHolder
                    .icon
                    .setImageResource(R.drawable.ic_add_restriction);

            if(isEditMode)
                userRestrictionViewHolder.icon.setClickable(false);

            //Listener opens popup where the user can select a new restriction
            userRestrictionViewHolder.icon.setOnClickListener($ ->
                    {
                        AddRestrictionDialog addRestrictionDialog = new AddRestrictionDialog(activity, currentRestriction.getPersonName());
                        addRestrictionDialog.show();
                    }
            );
        }else{
            userRestrictionViewHolder
                    .restrictionName
                    .setText(currentRestriction.getRestriction());

            //If in edit mode display remove icons
            if(isEditMode){
                userRestrictionViewHolder
                        .icon
                        .setImageResource(R.drawable.ic_remove);
                //Listener deletes selected restriction
                userRestrictionViewHolder.icon.setOnClickListener($ ->
                {
                    ProfileScreenFragment.getUserRestrictionsViewModel().delete(currentRestriction.getPersonName(), currentRestriction.getRestriction());
                    notifyDataSetChanged();
                });
            }
            //If favorite then display favorite icon
            else if(currentRestriction.getFavorite() == 1){
                userRestrictionViewHolder
                        .icon
                        .setImageResource(R.drawable.ic_star_on);
                //Listener removes selected restrictions from favorites
                userRestrictionViewHolder.icon.setOnClickListener($ ->
                        ProfileScreenFragment.getUserRestrictionsViewModel().unfavorite(currentRestriction.getPersonName(), currentRestriction.getRestriction()));
            }
            //If not a favorite then display regular icon
            else{
                userRestrictionViewHolder
                        .icon
                        .setImageResource(R.drawable.ic_star_off);
                //Listener adds selected restrictions to favorites
                userRestrictionViewHolder.icon.setOnClickListener($ ->
                        ProfileScreenFragment.getUserRestrictionsViewModel().favorite(currentRestriction.getPersonName(), currentRestriction.getRestriction()));
            }
        }

    }

    @Override
    public int getItemCount()
    {
        return userRestrictionList==null ? 0:userRestrictionList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    class UserRestrictionViewHolder extends RecyclerView.ViewHolder {
        private final TextView restrictionName;
        private final FloatingActionButton icon;

        private UserRestrictionViewHolder(View itemView) {
            super(itemView);
            restrictionName = itemView.findViewById(R.id.restriction_name);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
