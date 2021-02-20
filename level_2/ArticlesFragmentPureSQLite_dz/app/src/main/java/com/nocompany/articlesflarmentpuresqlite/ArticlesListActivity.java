package com.nocompany.articlesflarmentpuresqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

// TODO Переделать приложение архитектурно
// TODO Перенести реализацию интерфейса, с помощью которого взаимодействуют фрагменты, во ViewModel
// https://developer.android.com/topic/libraries/architecture/viewmodel.html
// TODO Перенести взаимодействие с базой данных в Repository и DataSource
// TODO Переделать взаимодействие с базой данных с Cursor на LiveData
// https://developer.android.com/topic/libraries/architecture/livedata.html

/*
    Хороший вариант начать с данных - 
    создать классы для таблиц базы данных, для хелпера,
    адаптеры, холдер.

    Потом создать классы фрагментов и нарисовать их внешний вид в xml.

    Потом скомпоновать лэйауты активностей из фрагментов в зависимости от
    конфигурации устройства - альбомная/портретная, планшет и т.п.

    Написать связующий код в активностях.
*/

/*
    Основная активность.
    Основная цель в том, чтобы определить, виден ли "детальный" фрагмент.
    Если "детальный" фрагмент виден, его методы можно вызвать по ссылке на него.
    Если "детальный" фрагмент не виден, то он запускается через специальную активность (ArticleActivity)
    и обновленный рейтинг возвращается в onActivityResult.
*/

public class ArticlesListActivity extends AppCompatActivity
        implements ArticleFragment.ArticleFragmentHost, ArticlesListFragment.ArticleListFragmentHost {

    // Константа с использованием которой, если устройство
    // находится в портретном режиме и представляет собой
    // телефон, запускается "детальная" активность (ArticleActivity).
    private static final int REQUEST_RATING = 1;

    // Ссылки на фрагменты.
    private ArticleFragment articleFragment;
    private ArticlesListFragment articlesListFragment;

    // Флаг - определяет, виден ли "детальный" фрагмент.
    private boolean doubleSided = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.articles_list);

        // Проверка на то, виден ли "детальный" фрагмент
        // Вначале ищем вью с его идентификатором (устанавливается в layout)
        View view = findViewById(R.id.article_fragment);

        // doubleSided = view != null ? true : false;
        // Если это вью не нулевое, 
        if(view != null)
            doubleSided = true;

        if(doubleSided)
        {
            // Если вью, соответствующее "детальному" фрагменту не нулевое,
            // получим ссылку на "детальный" фрагмент
            articleFragment = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.article_fragment);

        }
        // Ссылка на "листовой" фрагмент
        articlesListFragment = (ArticlesListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);

        Log.d("happy", "doubleSided: " + doubleSided);

    }

    // Обновление рейтинга в "листовом" фрагменте
    // при его изменении в "детальном" или при выполнении
    // onActivityResult
    @Override
    public void articleRatingChanged(int articleId, float newRating) {
        ArticlesListFragment articlesListFragment = (ArticlesListFragment) getSupportFragmentManager().findFragmentById(R.id.list_fragment);
        if(articlesListFragment != null)
            articlesListFragment.articleRatingChanged(articleId, newRating);
    }

    // В "листовом" фрагменте выбрана статья - нужно, в зависимости от того,
    // виден или нет "детальный" фрагмент, либо загрузить ее
    // в "детальный" фрагмент либо запустить ArticleActivity
    @Override
    public void articleSelected(int articleId, String articleURL, float articleRating) {
        if (doubleSided) {
            // Если виден "детальный" фрагмент, вызовем его метод
            if (articleFragment != null)
                articleFragment.articleSelected(articleId, articleURL, articleRating);
        } else {
            // Если "детальный" фрагмент не виден, запустим активити по интенту и
            // передадим туда нужные параметры
            Intent intent = new Intent(this, ArticleActivity.class);
            intent.putExtra(ArticleActivity.PARAMETER_ARTICLE_ID, articleId);
            intent.putExtra(ArticleActivity.PARAMETER_ARTICLE_RATING, articleRating);
            intent.putExtra(ArticleActivity.PARAMETER_ARTICLE_URL, articleURL);
            startActivityForResult(intent, REQUEST_RATING);
        }
    }

    // Возврат рейтинга статьи из ArticleActivity.
    // Выполняется если "детальный" фрагмент не виден на экране.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_RATING)
        {
            if(resultCode == RESULT_OK)
            {
                Log.d("happy", "onActivityResult");
                int articleId = data.getIntExtra(ArticleActivity.PARAMETER_ARTICLE_ID, -1);
                float articleRating = data.getFloatExtra(ArticleActivity.PARAMETER_ARTICLE_RATING, -1f);
                Log.d("happy", "articleId: " + articleId + ", articleRating: " + articleRating);
                if (articleId >= 0 && articleRating >= 0) {
                    if (articlesListFragment != null) {
                        // Обновить рейтинг статьи в "листовом" фрагменте
                        articlesListFragment.articleRatingChanged(articleId, articleRating);
                    }
                }
            }
        }
    }
}
