package com.example.weatherautovalue;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;
    public WeatherViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object object) {
        binding.setVariable(BR.obj, object);
        binding.executePendingBindings();
    }
}
