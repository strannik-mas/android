package com.example.databinding2023;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class Util {
    @BindingAdapter(value = {"src"})
    public static void getImage(ImageView view, String src) {
        Picasso.get().load(src).fit().centerCrop().into(view);
    }
}
