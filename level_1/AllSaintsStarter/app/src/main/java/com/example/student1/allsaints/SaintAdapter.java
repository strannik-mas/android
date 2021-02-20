package com.example.student1.allsaints;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class SaintAdapter extends ArrayAdapter<Saint> {

    private List<Saint> saints;

    private HashSet<Integer> selection = new HashSet<>();

    public SaintAdapter(@NonNull Context context, int resource, List<Saint> saints) {
        super(context, resource);
        this.saints = saints;
    }

    @Override
    public int getViewTypeCount() {
        return 2;   //для нескольких очередей ListView для разного вида элементов
    }

    public boolean isSelected(int position) {
        return selection.contains(position);
    }

    public boolean hasSelected() {
        return !selection.isEmpty();
    }

    public void toggleSelection(int position){
        if(isSelected(position)) {
            selection.remove(position);
        }else {
            selection.add(position);
        }
        notifyDataSetChanged();
    }

    public void deleteSelected(){
        //нужно удалять с конца
        List<Integer> items = new ArrayList<>();
        items.addAll(selection);

        //сортировка в обратном порядке
        Collections.sort(items, Collections.<Integer>reverseOrder());

        for(int i: items) {
            selection.remove(i);
            saints.remove(i);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //для поддержания нескольких очередей для нескольких типов элементов
        if (isSelected(position))
            return 1;
        else
            return 0;
    }

    @Override
    public int getCount() {
        return saints.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Saint s = saints.get(position);
        Holder holder = null;

        if(convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (isSelected(position)) {
                convertView = inflater.inflate(R.layout.listviewitemselected, parent, false);
            } else {
                convertView = inflater.inflate(R.layout.listviewitem, parent, false);
            }
            holder = new Holder();

            holder.name = (TextView) convertView.findViewById(R.id.text);
            holder.dob = (TextView) convertView.findViewById(R.id.dob);
            holder.dod = (TextView) convertView.findViewById(R.id.dod);
            holder.rating = (RatingBar) convertView.findViewById(R.id.rating);
            holder.three = (ImageView) convertView.findViewById(R.id.threedots);
            holder.three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(v, position);
                }
            });

            convertView.setTag(holder);

            Log.d("happy", "inflater.inflate");
        }
        holder = (Holder) convertView.getTag();

        holder.name.setText(s.getName());
        holder.dob.setText(s.getDob());
        holder.dod.setText(s.getDod());
        holder.rating.setRating(s.getRating());

        if (hasSelected()) {
            holder.three.setVisibility(View.INVISIBLE);
        } else {
            holder.three.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private void showPopup(View v, final int position) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.context);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                saints.remove(position);
                notifyDataSetChanged();
                return true;
            }
        });
        popupMenu.show();
    }

    private static class Holder {
        TextView name;
        TextView dob;
        TextView dod;
        RatingBar rating;
        ImageView three;
    }
}
