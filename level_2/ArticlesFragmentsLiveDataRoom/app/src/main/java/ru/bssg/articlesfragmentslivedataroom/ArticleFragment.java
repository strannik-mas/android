package ru.bssg.articlesfragmentslivedataroom;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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

import ru.bssg.articlesfragmentslivedataroom.room.Article;

// "Детальный" фрагмент
public class ArticleFragment extends LifecycleFragment implements RatingBar.OnRatingBarChangeListener {

    private ArticlesViewModel model;

    // Пустой конструктор по-умолчанию
    public ArticleFragment() {}

    @Override
    public void onRatingChanged(RatingBar ratingBar, float value, boolean b) {
        if(article != null) {
            article.setRating(value);
            model.updateArticle(article);
        }
    }

    // Интерфейс, который должна имплементить активити, 
    // чтобы иметь возможность получать от фрагмента 
    // сигнал о том. что рейтинг статьи изменился
    public interface ArticleFragmentHost
    {
        public void updateArticle(Article article);
    }

    // Ссылка на активность, имплементящую нужный 
    // фрагменту интерфейс
    private ArticleFragmentHost host;

    // Виджеты
    private RatingBar ratingBar;
    private WebView webView;

    Article article;
    // Метод жизненного цикла
    // Построение внешнего вида по xml
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        model = ViewModelProviders.of(getActivity()).get(ArticlesViewModel.class);

        ratingBar = (RatingBar) view.findViewById(R.id.article_rating);
        webView = (WebView) view.findViewById(R.id.article_web);
        webView.setWebViewClient(new WebViewClient());

        ratingBar.setOnRatingBarChangeListener(this);

        model.getSelected().observe(this, new Observer<Article>() {
            @Override
            public void onChanged(@Nullable Article a) {
                article = a;
                ratingBar.setOnRatingBarChangeListener(null);
                ratingBar.setRating(article.getRating());
                ratingBar.setOnRatingBarChangeListener(ArticleFragment.this);
                webView.loadUrl(article.getUrl());
            }
        });

        return view;
    }

}
