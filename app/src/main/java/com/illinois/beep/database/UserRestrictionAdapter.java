package com.illinois.beep.database;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
/*
public class UserRestrictionAdapter extends ListAdapter<UserRestriction, UserRestrictionViewHolder> {

    public UserRestrictionAdapter(@NonNull DiffUtil.ItemCallback<UserRestriction> diffCallback) {
        super(diffCallback);
    }

    @Override
    public UserRestrictionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return UserRestrictionViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(UserRestrictionViewHolder holder, int position) {
        UserRestriction current = (UserRestriction) getItem(position);
        holder.bind(current.getPersonName());
    }

    public static class UserRestrictionDiff extends DiffUtil.ItemCallback<UserRestriction> {

        @Override
        public boolean areItemsTheSame(@NonNull UserRestriction oldItem, @NonNull UserRestriction newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserRestriction oldItem, @NonNull UserRestriction newItem) {
            return oldItem.getPersonName().equals(newItem.getPersonName()) &&
                    oldItem.getRestriction().equals(newItem.getRestriction()) &&
                    oldItem.getFavorite() == newItem.getFavorite();
        }
    }
}
*/
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

public class UserRestrictionAdapter extends ListAdapter<UserRestriction, UserRestrictionAdapter.UserRestrictionViewHolder> {

    private List<UserRestriction> userRestrictionList;
    private final FragmentActivity activity;

    // Constructor
    UserRestrictionAdapter(@NonNull DiffUtil.ItemCallback<UserRestriction> diffCallback, List<UserRestriction> userRestrictionList, FragmentActivity activity)
    {
        super(diffCallback);
        this.userRestrictionList = userRestrictionList;
        this.activity = activity;
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

        // Create an instance of the ChildItem
        // class for the given position
        UserRestriction currentRestriction
                = userRestrictionList.get(position);

        // For the created instance, set title.
        if(currentRestriction.getRestriction().equals("add")) {
            userRestrictionViewHolder
                    .restrictionName
                    .setText(R.string.add_restriction_cue);

            userRestrictionViewHolder
                    .icon
                    .setImageResource(R.drawable.ic_add_restriction);
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
            if(currentRestriction.getFavorite() == 1){
                userRestrictionViewHolder
                        .icon
                        .setImageResource(R.drawable.ic_star_on);
                userRestrictionViewHolder.icon.setOnClickListener($ ->
                {
                    ProfileScreenFragment.getUserRestrictionsViewModel().unfavorite(currentRestriction.getPersonName(), currentRestriction.getRestriction());
                    userRestrictionViewHolder
                            .icon
                            .setImageResource(R.drawable.ic_star_off);
                });
            }
            else{
                userRestrictionViewHolder
                        .icon
                        .setImageResource(R.drawable.ic_star_off);
                userRestrictionViewHolder.icon.setOnClickListener($ ->
                {
                    ProfileScreenFragment.getUserRestrictionsViewModel().unfavorite(currentRestriction.getPersonName(), currentRestriction.getRestriction());
                    userRestrictionViewHolder
                            .icon
                            .setImageResource(R.drawable.ic_star_on);
                });
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
