package ru.bssg.articlesfragmentslivedataroom.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "articles")
public class Article {
    @PrimaryKey
    private int id;
    private String title;
    private String url;
    private float rating;

    public Article(int id, String title, String url, float rating) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating)
    {
        this.rating = rating;
    }
}
