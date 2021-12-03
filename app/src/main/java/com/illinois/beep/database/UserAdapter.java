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

/**
 * This is an adapter which manages the RecyclerView used to display users and their restrictions.
 * The class inflates the layout and manages all interactions with UI elements.
 */
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

        //Layout manages used to assign layout to RecyclerView
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                UserViewHolder
                        .childRecyclerView
                        .getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        //Get a list of the current restrictions which will be submitted to inner RecyclerView
        List<UserRestriction> currentRestrictions = new ArrayList<>(userRestrictionsViewModel.getRestrictionsObjects(current.getPersonName()));

        // Define number of elements in child
        layoutManager
                    .setInitialPrefetchItemCount(
                            currentRestrictions
                                    .size());

        // Create an instance of the child
        UserRestrictionAdapter childItemAdapter = new UserRestrictionAdapter(new UserAdapter.UserRestrictionDiff(), currentRestrictions, activity, isEditMode);
        //Set its layout manager
        UserViewHolder
                .childRecyclerView
                .setLayoutManager(layoutManager);
        //Set its adapter
        UserViewHolder
                .childRecyclerView
                .setAdapter(childItemAdapter);
        //set its RecyclerViewPool
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