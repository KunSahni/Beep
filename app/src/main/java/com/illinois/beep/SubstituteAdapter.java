package com.illinois.beep;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.illinois.beep.database.Product;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

public class SubstituteAdapter extends BaseAdapter {

    Context context;
    FragmentActivity activity;
    Fragment fragment;
    List<Product> substitutes;
    Set<Product> selectedSubstitutes;

    public SubstituteAdapter(Context context, FragmentActivity activity, Fragment fragment, List<Product> substitutes, Set<Product> selectedSubstitutes) {
        this.context = context;
        this.activity = activity;
        this.fragment = fragment;
        this.substitutes = substitutes;
        this.selectedSubstitutes = selectedSubstitutes;
    }

    @Override
    public int getCount() {
        return substitutes.size();
    }

    @Override
    public Object getItem(int position) {
        return substitutes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        Product product = substitutes.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.substitute_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            convertView.setOnClickListener(v -> {
                System.out.println(position + "th item clicked");
//                NavHostFragment.findNavController(fragment)
//                        .navigate(R.id.action_FirstFragment_to_substituteFragment);
            });
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.selectionFrame.setOnClickListener(v -> {
            if (selectedSubstitutes.contains(product)) {
                viewHolder.selectionIcon.setBackgroundResource(R.drawable.bg_teal_circle);
                selectedSubstitutes.remove(product);
            } else {
                viewHolder.selectionIcon.setBackgroundResource(R.drawable.bg_teal_rounded);
                selectedSubstitutes.add(product);
            }
        });


        Picasso.get().load(product.getImage_url()).into(viewHolder.productImage);
        viewHolder.productName.setText(product.getName());

        return convertView;
    }

    /**
     * design pattern reference: https://guides.codepath.com/android/Using-a-BaseAdapter-with-ListView#create-the-custom-baseadapter-implementation
     */
    private class ViewHolder {
        View selectionFrame;
        ImageView selectionIcon;
        ImageView productImage;
        TextView productName;

        public ViewHolder(View view) {
            selectionFrame = view.findViewById(R.id.selection_frame);
            selectionIcon = view.findViewById(R.id.selection_icon);
            productImage = view.findViewById(R.id.product_image);
            productName = view.findViewById(R.id.product_name);
        }
    }
}
