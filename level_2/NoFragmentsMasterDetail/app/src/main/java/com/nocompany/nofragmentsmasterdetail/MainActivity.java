package com.nocompany.nofragmentsmasterdetail;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.nocompany.nofragmentsmasterdetail.mech.ArticleHost;
import com.nocompany.nofragmentsmasterdetail.mech.ArticlesListHost;

// TODO Переделать на LiveData

public class MainActivity extends AppCompatActivity
        // имплементим нужные для кастомных вью интерфейсы
        implements ArticleHost, ArticlesListHost
{
    // Виден ли на экране детальный вид?
    private boolean doublePane = false;

    // Ссылка на список
    private ItemListView itemListView;

    // Ссылка на детальный вид
    private DetailView detailView;

    // Ссылка на корневой LinearLayout
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.main_ll);

        itemListView = (ItemListView) findViewById(R.id.main_itemlistview);
        detailView = (DetailView) findViewById(R.id.main_detailview);

        // doublePane = detailView != null ? true : false;
        // Определяем, виден ли на экране детальный вид статьи
        if(detailView != null)
            doublePane = true;
        else
            doublePane = false;

        if(savedInstanceState != null)
        {
            selectedArticleId = savedInstanceState.getInt(ARTICLE_ID, -1);
            selectedArticleURL = savedInstanceState.getString(ARTICLE_URL);
            selectedArticleRating = savedInstanceState.getFloat(ARTICLE_RATING, -1);
            if(selectedArticleId > -1 &&
                    !TextUtils.isEmpty(selectedArticleURL) &&
                    selectedArticleRating > -1
                    && doublePane)
                detailView.articleSelected(selectedArticleId, selectedArticleURL, selectedArticleRating);
        }

        Log.d("happy", "doublePane: " + doublePane);
    }

    // Из интерфейса детального вью
    @Override
    public void articleRatingChanged(int articleId, float newRating) {
        if (itemListView != null) {
            itemListView.articleRatingChanged(articleId, newRating);
            selectedArticleRating = newRating;
        }

    }

    private int selectedArticleId = -1;
    private String selectedArticleURL = "";
    private float selectedArticleRating  = -1;
    public static final String ARTICLE_ID = "ARTICLE_ID";
    public static final String ARTICLE_URL = "ARTICLE_URL";
    public static final String ARTICLE_RATING = "ARTICLE_RATING";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(selectedArticleId > -1)
        {
            outState.putInt(ARTICLE_ID, selectedArticleId);
            outState.putString(ARTICLE_URL, selectedArticleURL);
            outState.putFloat(ARTICLE_RATING, selectedArticleRating);
        }
    }

    // Из интерфейса списка
    @Override
    public void articleSelected(int articleId, String articleURL, float articleRating) {
        selectedArticleId = articleId;
        selectedArticleURL = articleURL;
        selectedArticleRating = articleRating;
        if (doublePane) {
            // Если детальный вью виден на экране,
            // вызовем его метод по ссылке
            if (detailView != null)
                detailView.articleSelected(articleId, articleURL, articleRating);
        } else {
            // Если не виден
            // и список все еще присоединен
            if (isListAttached()) {
                // Отсоединим его от корнегого LinearLayout
                linearLayout.removeViewAt(0);
                // И "надуем" на его месте детальный вью
                View.inflate(this, R.layout.detail, linearLayout);
            }
            // Получим локальную ссылку на детальный вью
            DetailView detailView = (DetailView) linearLayout.getChildAt(0);
            // и вызовем его метод по ссылке
            detailView.articleSelected(articleId, articleURL, articleRating);
        }
    }

    // Определим, присоединен ли список к корневому LinearLayout
    private boolean isListAttached() {
        return itemListView.getParent() != null;
    }

    // Метод жизненного цикла, вызывается по нажатию на кнопку  Back
    @Override
    public void onBackPressed() {
        if (!doublePane && !isListAttached()) {
            // Если вид портретный и список не подсоединен
            // то, значит, подсоединен детальный вью.
            // А значит, надо его отсоединить от корневого 
            // LinearLayout и присоединить список
            linearLayout.removeViewAt(0);
            linearLayout.addView(itemListView);
        } else
            // Если вид альбомный
            // или вид портретный и список присоединен, выполним
            // метод суперкласса (завершает активити)
            super.onBackPressed();
    }
}
