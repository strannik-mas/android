package ru.bssg.articlesfragmentslivedataroom.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.bssg.articlesfragmentslivedataroom.R;
import ru.bssg.articlesfragmentslivedataroom.room.Article;

public class ArticlesAdapter extends ArrayAdapter {

    private List<Article> articles = new ArrayList<>();
    private LayoutInflater inflater;

    public ArticlesAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        inflater = LayoutInflater.from(context);
    }

    public void updateArticles(List<Article> articles)
    {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Article article = articles.get(position);
        ArticleHolder holder;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.articles_list_item, parent, false);
            holder = new ArticleHolder();
            holder.text = (TextView) convertView.findViewById(R.id.list_item_text);
            holder.rating = (RatingBar) convertView.findViewById(R.id.list_item_rating);
            convertView.setTag(holder);
        }
        holder = (ArticleHolder) convertView.getTag();

        holder.text.setText(article.getTitle());
        holder.rating.setRating(article.getRating());

        return convertView;

    }
}
