package com.example.databinding2023;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter extends RecyclerView.Adapter{

    private final List<MetroObject> objects;

    public BaseAdapter(List<MetroObject> objects) {
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
        Object o = getElementForPosition(position);
        h.bind(o);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getTypeForPosition(position);
    }

    protected abstract Object getElementForPosition(int position);

    public abstract int getTypeForPosition(int position);
}
