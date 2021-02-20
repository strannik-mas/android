package com.nocompany.articlesflarmentpuresqlite.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nocompany.articlesflarmentpuresqlite.R;
import com.nocompany.articlesflarmentpuresqlite.db.ArticlesTable;


public class ArticlesAdapter extends CursorAdapter {

    public ArticlesAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View row = LayoutInflater.from(context).inflate(R.layout.articles_list_item, viewGroup, false);
        ArticleHolder holder = new ArticleHolder();
        holder.text = (TextView) row.findViewById(R.id.list_item_text);
        holder.rating = (RatingBar) row.findViewById(R.id.list_item_rating);

        //populateView(holder, cursor, context);

        row.setTag(holder);

        return row;
    }

    private void populateView(ArticleHolder holder, Cursor cursor, Context context)
    {
        holder.text.setText(cursor.getString(cursor.getColumnIndex(ArticlesTable.COLUMN_ARTICLE_TITLE)));
        holder.rating.setRating(cursor.getFloat(cursor.getColumnIndex(ArticlesTable.COLUMN_ARTICLE_RATING)));
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ArticleHolder holder = (ArticleHolder) view.getTag();
        populateView(holder, cursor, context);
    }
}
