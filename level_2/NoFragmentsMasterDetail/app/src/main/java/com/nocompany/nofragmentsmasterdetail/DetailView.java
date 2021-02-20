package com.nocompany.nofragmentsmasterdetail;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.nocompany.nofragmentsmasterdetail.mech.ArticleHost;


public class DetailView extends LinearLayout {
    private WebView webView;
    private RatingBar ratingBar;

    private int articleId = -1;
    private  float articleRating = -1f;
    private String articleURL = "http://www.gazeta.ru";

    // Так как расширяем вью, нужно вызвать конструктор суперкласса
    public DetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // Метод жизненного цикла
    // Вызывается при завершении процесса "надувания" вью
    // Используем для инициализации
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ratingBar = (RatingBar) findViewById(R.id.article_rating);

        webView = (WebView) findViewById(R.id.article_webview);

        webView.setWebViewClient(new WebViewClient());

        // При изменении рейтинга
        // вызовем интерфейсный метод активити
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                ArticleHost host = (ArticleHost) getContext();
                // Только если активити имплементит нужный интерфейс
                if(host != null)
                {
                    articleRating  = rating;
                    host.articleRatingChanged(articleId, articleRating);
                }
            }
        });
        populateData();
    }

    // Сервисный метод
    private void populateData() {
        if(articleRating >=0 && !articleURL.equals("") && articleRating >= 0) {
            ratingBar.setRating(articleRating);
            webView.loadUrl(articleURL);
        }
        else
            ratingBar.setRating(0f);
    }

    // Вызывается активити когда в списке выбирается другая статья
    public void articleSelected(int articleId, String articleURL, float articleRating) {
        this.articleId = articleId;
        this.articleURL = articleURL;
        this.articleRating = articleRating;

        populateData();
    }
}
