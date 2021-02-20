package ru.bssg.articlesfragmentslivedataroom.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM articles")
    LiveData<List<Article>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertArticles(List<Article> articles);

    @Update
    public void updateArticle(Article article);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(Article article);
}
