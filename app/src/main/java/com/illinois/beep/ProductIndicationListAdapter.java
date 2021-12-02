package com.illinois.beep;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductIndicationListAdapter extends RecyclerView.Adapter<ProductIndicationListAdapter.ViewHolder> {
    /**
     * reference: https://developer.android.com/guide/topics/ui/layout/recyclerview
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView indicationIcon;
        TextView indicationName;

        public ViewHolder(View view) {
            super(view);
            indicationIcon = view.findViewById(R.id.indication_icon);
            indicationName = view.findViewById(R.id.indication_name);
        }
    }

    enum RestrictionLevel {
      GOOD,
      WARN,
      BAD,
    };

    List<Pair<String, RestrictionLevel>> indications;

    public ProductIndicationListAdapter(List<Pair<String, RestrictionLevel>> indications) {
        this.indications = indications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.indication_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Pair<String, RestrictionLevel> indication = indications.get(position);
        String indicationName = indication.first;
        RestrictionLevel restrictionLevel = indication.second;

        switch (restrictionLevel) {
            case WARN:
                viewHolder.indicationIcon.setImageResource(R.drawable.ic_warning);
                break;
            case BAD:
                viewHolder.indicationIcon.setImageResource(R.drawable.ic_minus_icon);
                break;
            default:
                viewHolder.indicationIcon.setImageResource(R.drawable.ic_icon_good);
        }

        viewHolder.indicationName.setText(indicationName);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return indications.size();
    }
}
