package com.illinois.beep;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.illinois.beep.database.Product;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

public class MyListAdapter extends BaseAdapter {

    enum RestrictionLevel {
      GOOD,
      WARN,
      BAD,
    };

    Context context;
    FragmentActivity activity;
    List<MyListItem> myList;

    public MyListAdapter(Context context, FragmentActivity activity, List<MyListItem> myList) {
        this.context = context;
        this.activity = activity;
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
        RestrictionViewModel restrictionViewModel = new ViewModelProvider(activity).get(RestrictionViewModel.class);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.my_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MyListItem item = myList.get(position);
        Product product = item.getProduct();

        Picasso.get().load(product.getImage_url()).into(viewHolder.productImage);

        Set<String> restrictions = restrictionViewModel.getRestrictions().getValue();
        RestrictionLevel restrictionLevel = RestrictionLevel.GOOD;
        assert restrictions != null;
        for (String restriction: restrictions) {
            if (product.getIndications().getOrDefault(restriction, false)) {
                restrictionLevel = RestrictionLevel.WARN;
            }
        }

        switch (restrictionLevel) {
            case WARN:
                viewHolder.restrictionIcon.setImageResource(R.drawable.ic_warning);
                break;
            case BAD:
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
