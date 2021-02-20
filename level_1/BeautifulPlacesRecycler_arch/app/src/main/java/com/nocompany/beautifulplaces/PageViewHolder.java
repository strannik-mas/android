package com.nocompany.beautifulplaces;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PageViewHolder extends RecyclerView.ViewHolder {

    private TextView place;
    private TextView priceold;
    private TextView pricenew;
    private TextView description;
    private ImageView picture;

    private Context context;

    public PageViewHolder(View itemView) {
        super(itemView);

        this.context = itemView.getContext();

        picture = (ImageView) itemView.findViewById(R.id.picture);
        place = (TextView) itemView.findViewById(R.id.place);
        priceold = (TextView) itemView.findViewById(R.id.priceold);
        pricenew = (TextView) itemView.findViewById(R.id.pricenew);
        description = (TextView) itemView.findViewById(R.id.description);

    }

    public void setPlace(Place p) {



        Picasso
                .with(context) // Используя Context
                .load(p.getPicture()) // Загружаем картинку по URL
                .fit() // Автоматически определяем размеры ImageView
                .centerCrop() // Масштабируем картинку
                .into(picture); // в ImageView

        place.setText(p.getPlace());
        priceold.setText(p.getOldPrice());
        priceold.setPaintFlags(priceold.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        pricenew.setText(p.getNewPrice());
        description.setText(p.getDescription());

    }
}
