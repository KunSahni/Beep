package com.illinois.beep.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.illinois.beep.ProfileScreenFragment;
import com.illinois.beep.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ListAdapter<UserRestriction, UserViewHolder> {

    private final RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();
    private final UserRestrictionsViewModel userRestrictionsViewModel;
    private final FragmentActivity activity;
    private final boolean isEditMode;

    public UserAdapter(@NonNull DiffUtil.ItemCallback<UserRestriction> diffCallback, FragmentActivity activity, boolean isEditMode) {
        super(diffCallback);
        this.activity = activity;
        this.userRestrictionsViewModel = ProfileScreenFragment.getUserRestrictionsViewModel();
        this.isEditMode = isEditMode;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.user_recyclerview_item,
                        parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        UserRestriction current = (UserRestriction) getItem(position);
        holder.bind(current.getPersonName());


        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.

        // Here we have assigned the layout
        // as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                UserViewHolder
                        .childRecyclerView
                        .getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        List<UserRestriction> currentRestrictions = new ArrayList<>(userRestrictionsViewModel.getRestrictionsObjects(current.getPersonName()));

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager
                    .setInitialPrefetchItemCount(
                            currentRestrictions
                                    .size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
        UserRestrictionAdapter childItemAdapter = new UserRestrictionAdapter(new UserAdapter.UserRestrictionDiff(), currentRestrictions, activity, isEditMode);

        UserViewHolder
                .childRecyclerView
                .setLayoutManager(layoutManager);
        UserViewHolder
                .childRecyclerView
                .setAdapter(childItemAdapter);
        UserViewHolder
                .childRecyclerView
                .setRecycledViewPool(viewPool);
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