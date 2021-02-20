package com.nocompany.articlesflarmentpuresqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


// Выполняется только на телефоне в портретном режиме
// Содержит в себе "детальный" фрагмент
public class ArticleActivity extends AppCompatActivity implements ArticleFragment.ArticleFragmentHost {

    // Значения по-умолчанию
    private int articleId = -1;
    private float articleRating = -1f;
    private String articleURL = "";

    public static final String PARAMETER_ARTICLE_ID = "PARAMETER_ARTICLE_ID";
    public static final String PARAMETER_ARTICLE_URL = "PARAMETER_ARTICLE_URL";
    public static final String PARAMETER_ARTICLE_RATING = "PARAMETER_ARTICLE_RATING";

    private ArticleFragment articleFragment;

    // Метод жизненного цикла
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        // Получаем параметы статьи из интента
        Intent intent = getIntent();
        articleId = intent.getIntExtra(PARAMETER_ARTICLE_ID, -1);
        articleRating = intent.getFloatExtra(PARAMETER_ARTICLE_RATING, -1f);
        articleURL = intent.getStringExtra(PARAMETER_ARTICLE_URL);

        // Получаем ссылку на "детальный" фрагмент 
        articleFragment = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_fragment);
    }

    // Метод жизненного цикла
    @Override
    protected void onResume() {
        super.onResume();
        if(articleFragment != null)
        {
            // Обновляем данные во фрагменте
            articleFragment.articleSelected(articleId, articleURL, articleRating);
        }

    }

    // Метод интерфейса фрагмента
    // Фрагмент сообщает активности, что рейтинг статьи изменился
    @Override
    public void articleRatingChanged(int articleId, float newRating) {
        this.articleRating = newRating;
        this.articleId = articleId;
    }

    // Выполняется по нажатию на Back
    @Override
    public void onBackPressed() {
        // Возвращение результата в ArticlesListActivity
        Intent intent = new Intent();
        intent.putExtra(PARAMETER_ARTICLE_ID, articleId);
        intent.putExtra(PARAMETER_ARTICLE_RATING, articleRating);
        setResult(
                RESULT_OK,
                intent
        );
        super.onBackPressed();
    }
}
