package com.student.databinding3;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class MetroAdapter extends RecyclerView.Adapter {

    private final List<MetroObject> objects;

    public MetroAdapter(List<MetroObject> objects) {
        this.objects = objects;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);

        return new MetroViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MetroViewHolder h = (MetroViewHolder) holder;
        h.bind(objects.get(position));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object o = objects.get(position);
        if (o instanceof MetroLine) {
            return R.layout.line_item;
        } else
            return R.layout.station_item;
    }
}
