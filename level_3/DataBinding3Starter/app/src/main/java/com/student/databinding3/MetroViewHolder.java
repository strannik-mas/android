package com.student.databinding3;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class MetroViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public MetroViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object obj) {
        binding.setVariable(BR.obj, obj);
        binding.executePendingBindings();
    }
}
