package com.illinois.beep;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.illinois.beep.database.AppDatabase;
import com.illinois.beep.database.ConcreteAppDatabase;

import java.util.ArrayList;
import java.util.Optional;

public class RestrictionListViewAdapter extends BaseAdapter implements ListAdapter {
    private Boolean isDeleteMode;
    private String personName;
    private ArrayList<String> restrictions = new ArrayList<String>();
    private Context context;
    private Activity activity;
    private AppDatabase db = ConcreteAppDatabase.getInstance(context);

    public RestrictionListViewAdapter(ArrayList<String> restrictions, Activity activity, String personName, Boolean isDeleteMode) {
        this.restrictions = restrictions;
        //move null key to end of Array
        restrictions.remove(null);
        restrictions.add(null);
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.personName = personName;
        this.isDeleteMode = isDeleteMode;
    }

    @Override
    public int getCount() {
        return restrictions.size();
    }

    @Override
    public Object getItem(int pos) {
        return restrictions.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mylist, null);
        }

        FloatingActionButton icon = view.findViewById(R.id.icon);
        TextView textView = view.findViewById(R.id.restriction_name);

        if (restrictions.get(position) == null){
            icon.setImageResource(R.drawable.ic_add_restriction);
            textView.setText(R.string.add_restriction_cue);
        }else if (db.userRestrictionsDao().isFavorite(personName, restrictions.get(position)) == 0){
            icon.setImageResource(isDeleteMode? R.drawable.ic_remove : R.drawable.ic_star_off);
            textView.setText(restrictions.get(position));
        }else {
            icon.setImageResource(isDeleteMode? R.drawable.ic_remove : R.drawable.ic_star_on);
            textView.setText(restrictions.get(position));
        }


        icon.setOnClickListener(v -> {
            if (restrictions.get(position) == null){
                AddRestrictionDialog cdd=new AddRestrictionDialog(activity, personName);
                cdd.show();
            }else if(isDeleteMode){
                db.userRestrictionsDao().delete(personName, restrictions.get(position));
                restrictions.remove(position);
                notifyDataSetChanged();
            }else if(db.userRestrictionsDao().isFavorite(personName, restrictions.get(position)) == 0){
                db.userRestrictionsDao().favorite(personName, restrictions.get(position));
                notifyDataSetChanged();
            }else if(db.userRestrictionsDao().isFavorite(personName, restrictions.get(position)) == 1){
                db.userRestrictionsDao().favorite(personName, restrictions.get(position));
                notifyDataSetChanged();
            }

        });

        return view;
    }
}