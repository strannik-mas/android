package com.strannik.beautifulplacesrecycler;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class PageViewHolder extends RecyclerView.ViewHolder {

    public ImageView picturePlaceImage;
    public TextView placeTitle;
    public TextView placeDesc;
    public TextView placePrice;
    public TextView placeOldPrice;

    public PageViewHolder(@NonNull View itemView) {
        super(itemView);

        placeTitle = itemView.findViewById(R.id.place);
        placeDesc = itemView.findViewById(R.id.description);
        placePrice = itemView.findViewById(R.id.pricenew);
        placeOldPrice = itemView.findViewById(R.id.priceold);

        picturePlaceImage = (ImageView) itemView.findViewById(R.id.pictureRecycler);

    }

    public void setPlace(Place p) {
        Picasso
                .get()
                .load(p.getPicture()) // Загружаем картинку по URL
                .fit() // Автоматически определяем размеры ImageView
                .centerCrop() // Масштабируем картинку
                .into(picturePlaceImage); // в ImageView

        placeTitle.setText(p.getPlace());
        placeOldPrice.setText(p.getOldPrice());
        placeOldPrice.setPaintFlags(placeOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        placePrice.setText(p.getNewPrice());
        placeDesc.setText(p.getDescription());

    }
}
