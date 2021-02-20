package com.nocompany.articlesflarmentpuresqlite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RatingBar;

// "Детальный" фрагмент
public class ArticleFragment extends Fragment {
    // Пустой конструктор по-умолчанию
    public ArticleFragment() {}

    // Интерфейс, который должна имплементить активити, 
    // чтобы иметь возможность получать от фрагмента 
    // сигнал о том. что рейтинг статьи изменился
    public interface ArticleFragmentHost
    {
        public void articleRatingChanged(int articleId, float newRating);
    }

    // Ссылка на активность, имплементящую нужный 
    // фрагменту интерфейс
    private ArticleFragmentHost host;

    // Значения по-умолчанию
    private float articleRating = -1f;
    private int articleId = -1;
    private String articleURL = "";

    // Виджеты
    private RatingBar ratingBar;
    private WebView webView;

    // Метод жизненного цикла
    // Сохранение состояния
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ArticleActivity.PARAMETER_ARTICLE_URL, articleURL);
        outState.putInt(ArticleActivity.PARAMETER_ARTICLE_ID, articleId);
        outState.putFloat(ArticleActivity.PARAMETER_ARTICLE_RATING, articleRating);
    }

    // Метод жизненного цикла
    // Восстановление состояния
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
        {
            if(savedInstanceState.containsKey(ArticleActivity.PARAMETER_ARTICLE_ID))
            {
                articleId = savedInstanceState.getInt(ArticleActivity.PARAMETER_ARTICLE_ID, -1);
            }
            if(savedInstanceState.containsKey(ArticleActivity.PARAMETER_ARTICLE_ID))
            {
                articleRating = savedInstanceState.getFloat(ArticleActivity.PARAMETER_ARTICLE_RATING, -1f);
            }
            if(savedInstanceState.containsKey(ArticleActivity.PARAMETER_ARTICLE_URL))
            {
                articleURL = savedInstanceState.getString(ArticleActivity.PARAMETER_ARTICLE_URL);
            }
        }
        Log.d("happy", "ArticleFragment onCreate "+this.hashCode());
    }

    // Метод жизненного цикла
    // Построение внешнего вида по xml
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        ratingBar = (RatingBar) view.findViewById(R.id.article_rating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float newRating, boolean b) {
                // При изменении рейтинга статьи, вызвать
                // метод интерфейса, который должна 
                // имплементить активити
                if(host != null)
                {
                    articleRating = newRating;
                    host.articleRatingChanged(articleId, newRating);
                }
            }
        });

        webView = (WebView) view.findViewById(R.id.article_web);
        webView.setWebViewClient(new WebViewClient());

        return view;
    }

    // Метод жизненного цикла
    @Override
    public void onResume() {
        super.onResume();
        populateData();
    }

    // Загрузить в WebView статью по URL,
    // обновить рейтинг статьи
    private void populateData() {
        if(articleRating >=0 && !articleURL.equals("") && articleRating >= 0) {
            ratingBar.setRating(articleRating);
            webView.loadUrl(articleURL);
        }
        else
            ratingBar.setRating(0f);
    }

    // Вызывается активностью при выборе статьи
    public void articleSelected(int articleId, String articleURL, float articleRating) {
        this.articleId = articleId;
        this.articleURL = articleURL;
        this.articleRating = articleRating;
        populateData();
    }


    // Метод жизненного цикла
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Приводим активити к интерфейсному типу
        // и сохраняем ссылку на нее
        if(context instanceof ArticleFragmentHost)
            host = (ArticleFragmentHost) context;
        else
            throw new RuntimeException(context.toString()
                    + " must implement ArticleFragmentHost!");
    }

    // Метод жизненного цикла
    // Обнуляем ссылку на активити
    @Override
    public void onDetach() {
        super.onDetach();
        host = null;
    }
}
