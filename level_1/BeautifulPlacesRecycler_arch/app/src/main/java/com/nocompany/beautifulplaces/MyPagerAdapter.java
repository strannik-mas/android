package com.nocompany.beautifulplaces;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private List<Place> places;

    MyPagerAdapter(List<Place> places) {
        this.places = places;
    }

    // Вызывается пейджером если есть необходимость уничтожить view
    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    // Создание view адаптером для педжера из элемента контейнера
    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        // Так как container уже имеет Context, используем его для
        // создания LayoutInflater
        final Context context = collection.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Получаем место из контейнера
        Place p = places.get(position);

        // "Надуваем" view из xml файла разметки
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.page, collection, false);

        // Находим ImageView внутри только что "надутого" view
        ImageView picture = (ImageView) layout.findViewById(R.id.picture);

        // С помощью Picasso
        Picasso
                .with(context) // Используя Context
                .load(places.get(position).getPicture()) // Загружаем картинку по URL
                .fit() // Автоматически определяем размеры ImageView
                .centerCrop() // Масштабируем картинку
                .into(picture); // в ImageView

        // Находим ссылки на другие элементы управления
        TextView place = (TextView) layout.findViewById(R.id.place);
        TextView priceold = (TextView) layout.findViewById(R.id.priceold);
        TextView pricenew = (TextView) layout.findViewById(R.id.pricenew);
        TextView description = (TextView) layout.findViewById(R.id.description);

        /*
        Button button = (Button) layout.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Ordered!")
                        .setMessage("Your trip is ordered!")
                        .setCancelable(false)
                        .setIcon(R.mipmap.ic_launcher)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        */

        // Актуализируем информацию в элементах управления
        place.setText(p.getPlace());
        priceold.setText(p.getOldPrice());
        priceold.setPaintFlags(priceold.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        pricenew.setText(p.getNewPrice());
        description.setText(p.getDescription());

        // Возвращаем "надутый" и настроенный view
        collection.addView(layout);
        return layout;
    }

    // Сколько всего элементов в контейнере
    @Override
    public int getCount() {
        return places.size();
    }

    // Чтобы определить, нет ли уже такого view
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
