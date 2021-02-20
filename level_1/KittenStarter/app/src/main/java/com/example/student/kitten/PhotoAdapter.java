package com.example.student.kitten;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class PhotoAdapter extends CursorAdapter {
    
    public PhotoAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    // Для CursorAdapter эта функция вызывается для создания View
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // View view = context.getLayoutInflater(
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        Holder holder = new Holder();
        holder.picture = (ImageView) view.findViewById(R.id.image);
        populateView(holder, cursor, context);
        view.setTag(holder);

        return view;
    }

    // Для CursorAdapter эта функция вызывается для изменения View
    // Нужно только получить Holder из Tag и поменять ImageView, на 
    // который он хранит ссылку
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder = (Holder) view.getTag();
        populateView(holder, cursor, context);
    }

    // Загрузка картинки в ImageView, на который хранит ссылку Holder
    private void populateView(final Holder holder, Cursor cursor, final Context context) {

        Picasso
                .with(context)
                .load(
                        // Получаем URL картинки из курсора
                        cursor.getString(
                                // Индекс колонки
                                cursor.getColumnIndex(
                                    // С нужным названием
                                    PhotosTable.COLUMN_URL
                                )
                        )
                )
                .fit()
                .centerCrop()
                .into(holder.picture);
    }
}
