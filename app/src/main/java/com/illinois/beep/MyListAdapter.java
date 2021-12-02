package com.illinois.beep;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.illinois.beep.database.Product;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout myListItem;
        ImageView productImage;
        TextView productName;
        TextView productQuantity;
        ImageView restrictionIcon;

        public ViewHolder(View view) {
            super(view);
            myListItem = view.findViewById(R.id.my_list_item);
            productImage = view.findViewById(R.id.product_image);
            productName = view.findViewById(R.id.product_name);
            productQuantity = view.findViewById(R.id.product_quantity_num);
            restrictionIcon = view.findViewById(R.id.restriction_icon);
        }
    }

    enum RestrictionLevel {
        GOOD,
        WARN,
        DANGER,
    };

    Fragment fragment;
    List<MyListItem> myList;
    Set<String> dangerRestrictions = new HashSet<>();
    Set<String> warningRestrictions = new HashSet<>();

    public MyListAdapter(Fragment fragment, List<MyListItem> myList) {
        this.fragment = fragment;
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MyListItem item = myList.get(position);
        Product product = item.getProduct();

        viewHolder.myListItem.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("productId", product.getId());
            bundle.putInt("position", position);
            bundle.putInt("quantity", item.quantity);
            NavHostFragment.findNavController(fragment)
                    .navigate(R.id.action_FirstFragment_to_productReviewFragment, bundle);
        });


        Picasso.get().load(product.getImage_url()).into(viewHolder.productImage);
        RestrictionLevel restrictionLevel = RestrictionLevel.GOOD;

        for (String restriction: warningRestrictions) {
            if (Boolean.TRUE.equals(product.getIndications().get(restriction))) {
                restrictionLevel = RestrictionLevel.WARN;
                break;
            }
        }

        for (String restriction: dangerRestrictions) {
            if (Boolean.TRUE.equals(product.getIndications().get(restriction))) {
                restrictionLevel = RestrictionLevel.DANGER;
                break;
            }
        }


        // based on restriction level to set UI icon
        switch (restrictionLevel) {
            case WARN:
                viewHolder.restrictionIcon.setImageResource(R.drawable.ic_warning);
                break;
            case DANGER:
                viewHolder.restrictionIcon.setImageResource(R.drawable.ic_minus_icon);
                break;
            default:
                viewHolder.restrictionIcon.setImageResource(R.drawable.ic_icon_good);
        }

        viewHolder.productName.setText(product.getName());
        viewHolder.productQuantity.setText(item.getQuantity().toString());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void updateMyList(List<MyListItem> myList) {
        this.myList = myList;
        notifyDataSetChanged();
    }

    public void updateRestrictions(Set<String> warningRestrictions, Set<String> dangerRestrictions) {
        this.warningRestrictions = warningRestrictions;
        this.dangerRestrictions = dangerRestrictions;
        notifyDataSetChanged();
    }
}
