package com.illinois.beep;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.illinois.beep.database.Product;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

public class MyListAdapter extends BaseAdapter {

    enum RestrictionLevel {
        GOOD,
        WARN,
        DANGER,
    };

    Context context;
    FragmentActivity activity;
    Fragment fragment;
    List<MyListItem> myList;
    Set<String> dangerRestrictions = new HashSet<>();
    Set<String> warningRestrictions = new HashSet<>();

    public MyListAdapter(Context context, FragmentActivity activity, Fragment fragment, List<MyListItem> myList) {
        this.context = context;
        this.activity = activity;
        this.fragment = fragment;
        this.myList = myList;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        MyListItem item = myList.get(position);
        Product product = item.getProduct();

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.my_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            convertView.setOnClickListener(v -> {
                System.out.println(position + "th item clicked");
                Bundle bundle = new Bundle();
                bundle.putString("productId", product.getId());
                bundle.putInt("position", position);
                NavHostFragment.findNavController(fragment)
                        .navigate(R.id.action_FirstFragment_to_productReviewFragment, bundle);
            });
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Picasso.get().load(product.getImage_url()).into(viewHolder.productImage);
        RestrictionLevel restrictionLevel = RestrictionLevel.GOOD;

        for (String restriction: warningRestrictions) {
            if (Boolean.TRUE.equals(product.getIndications().getOrDefault(restriction, false))) {
                restrictionLevel = RestrictionLevel.WARN;
                break;
            }
        }

        for (String restriction: dangerRestrictions) {
            if (Boolean.TRUE.equals(product.getIndications().getOrDefault(restriction, false))) {
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

        return convertView;
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

    /**
     * design pattern reference: https://guides.codepath.com/android/Using-a-BaseAdapter-with-ListView#create-the-custom-baseadapter-implementation
     */
    private class ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productQuantity;
        ImageView restrictionIcon;

        public ViewHolder(View view) {
            productImage = view.findViewById(R.id.product_image);
            productName = view.findViewById(R.id.product_name);
            productQuantity = view.findViewById(R.id.product_quantity_num);
            restrictionIcon = view.findViewById(R.id.restriction_icon);
        }
    }
}
