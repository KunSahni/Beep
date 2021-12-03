package com.illinois.beep;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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

    public static class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private final MyListAdapter mAdapter;
        private final Paint mClearPaint;
        private final ColorDrawable mBackground;
        private final int backgroundColor;
        private final Drawable deleteDrawable;
        private final int intrinsicWidth;
        private final int intrinsicHeight;

        public SwipeToDeleteCallback(MyListAdapter adapter) {
            super(0,ItemTouchHelper.LEFT);
            mAdapter = adapter;
            mBackground = new ColorDrawable();
            backgroundColor = Color.parseColor("#b80f0a");
            mClearPaint = new Paint();
            mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            deleteDrawable = ContextCompat.getDrawable(adapter.fragment.getContext(), R.drawable.trash_alt_solid);
            intrinsicWidth = deleteDrawable.getIntrinsicWidth();
            intrinsicHeight = deleteDrawable.getIntrinsicHeight();
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mAdapter.deleteItem(position);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            View itemView = viewHolder.itemView;
            int itemHeight = itemView.getHeight();

            boolean isCancelled = dX == 0 && !isCurrentlyActive;

            if (isCancelled) {
                clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                return;
            }

            mBackground.setColor(backgroundColor);
            mBackground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            mBackground.draw(c);

            int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
            int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
            int deleteIconRight = itemView.getRight() - deleteIconMargin;
            int deleteIconBottom = deleteIconTop + intrinsicHeight;


            deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
            deleteDrawable.draw(c);

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


        }

        private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom) {
            c.drawRect(left, top, right, bottom, mClearPaint);

        }
    };


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
                viewHolder.restrictionIcon.setImageResource(R.drawable.ic_buttontest);
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

    public void deleteItem(int position) {
        myList.remove(position);
        notifyItemRemoved(position);
    }
}
