package com.nocompany.nofragmentsmasterdetail.mech;

import android.content.Context;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RatingBar;
import android.widget.TextView;


import com.nocompany.nofragmentsmasterdetail.R;
import com.nocompany.nofragmentsmasterdetail.database.ArticlesTable;


public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View row = LayoutInflater.from(context).inflate(R.layout.articles_list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder();
        holder.text = (TextView) row.findViewById(R.id.list_item_text);
        holder.rating = (RatingBar) row.findViewById(R.id.list_item_rating);

        // populateView(holder, cursor, context);

        row.setTag(holder);

        return row;

    }
    private void populateView(ViewHolder holder, Cursor cursor, Context context)
    {
        holder.text.setText(cursor.getString(cursor.getColumnIndex(ArticlesTable.COLUMN_ARTICLE_TITLE)));
        holder.rating.setRating(cursor.getFloat(cursor.getColumnIndex(ArticlesTable.COLUMN_ARTICLE_RATING)));
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        populateView(holder, cursor, context);
    }
}
