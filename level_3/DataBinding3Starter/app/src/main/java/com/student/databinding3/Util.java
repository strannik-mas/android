package com.student.databinding3;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Util {

    @BindingAdapter(value = {"src"})
    public static void getImage(ImageView view, String src) {
        Picasso.get().load(src).fit().centerCrop().into(view);
    }
}
