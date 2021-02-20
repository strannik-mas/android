package ru.bssg.articlesfragmentslivedataroom;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;


import ru.bssg.articlesfragmentslivedataroom.room.Article;

public class ArticlesViewModel extends ViewModel
        implements
        ArticlesListFragment.ArticleListFragmentHost,
        ArticleFragment.ArticleFragmentHost
{

    public LiveData<List<Article>> getArticles() {
        return ArticlesApp.getRepository().getArticles();
    }

    private MutableLiveData<Article> selected = new MutableLiveData<>();
    private MutableLiveData<Boolean> doubleSided = new MutableLiveData<>();

    @Override
    public void articleSelected(Article article) {
        selected.postValue(article);
    }

    public LiveData<Article> getSelected() {
        return selected;
    }

    public void doubleSided(boolean twoSided) {
        doubleSided.postValue(twoSided);
    }

    public LiveData<Boolean> getDoubleSided() {
        return doubleSided;
    }

    @Override
    public void updateArticle(final Article article) {
        new Thread() {
            @Override
            public void run() {
                ArticlesApp.getRepository().updateArticle(article);
            }
        }.start();
    }
}
