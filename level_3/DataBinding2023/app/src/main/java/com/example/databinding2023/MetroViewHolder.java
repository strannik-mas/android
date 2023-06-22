package com.example.databinding2023;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class MetroViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public MetroViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(MetroObject metroObject) {
        binding.setVariable(BR.obj, metroObject);
        binding.executePendingBindings();
    }
}
