package ru.bssg.articlesfragmentslivedataroom;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import java.util.List;
import ru.bssg.articlesfragmentslivedataroom.room.AppDatabase;
import ru.bssg.articlesfragmentslivedataroom.room.Article;

public class Repository {

    private AppDatabase appDatabase;

    public Repository(Context context) {
        appDatabase = Room.databaseBuilder(context,
                AppDatabase.class, "articles.db").build();
    }

    public LiveData<List<Article>> getArticles() {
        return appDatabase.articleDao().getAll();
    }

    public void updateArticle(Article article) {
        appDatabase.articleDao().updateArticle(article);
    }

    public void insertArticles(List<Article> articles) {
        appDatabase.articleDao().insertArticles(articles);
    }

    public void insertArticle(Article article) {
        appDatabase.articleDao().insertArticle(article);
    }
}
