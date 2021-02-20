package com.nocompany.articlesflarmentpuresqlite;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.nocompany.articlesflarmentpuresqlite.adapter.ArticlesAdapter;
import com.nocompany.articlesflarmentpuresqlite.db.ArticlesHelper;
import com.nocompany.articlesflarmentpuresqlite.db.ArticlesTable;

// ListFragment уже содержит в себе ListView
public class ArticlesListFragment extends ListFragment {

    private ArticleListFragmentHost host;
    //private ArticlesHelper helper;
    private ArticlesAdapter adapter;

    // Активность, на которой будет размещен фрагмент, должна имплментить
    // этот интерфейс, чтобы фрагмент мог передавать ей данные
    public interface ArticleListFragmentHost {
        public void articleSelected(int articleId, String articleURL, float articleRating);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(
                555,
                Bundle.EMPTY,
                new LoaderManager.LoaderCallbacks<Cursor>() {
                    @Override
                    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                        return new CursorLoader(
                                getContext(),
                                ArticlesContentProvider.URI,
                                null,
                                null,
                                null,
                                null
                        );
                    }

                    @Override
                    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                        adapter.swapCursor(data);
                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> loader) {
                        adapter.swapCursor(null);
                    }
                }
                );
    }

    // Метод для получения курсора
/*    private Cursor getArticlesCursor() {
//        return  helper.getReadableDatabase().query(
//                ArticlesTable.TABLE_ARTICLES,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
        //это работает в основном потоке
        return getContext().getContentResolver().query(
            ArticlesContentProvider.URI,
                null,
                null,
                null,
                null
        );
    }*/

    // Вызывается активностью когда меняется рейтинг статьи,
    // чтобы фрагмент поменял его в списке
    public void articleRatingChanged(final int articleId, float newRating) {
        final ContentValues cv = new ContentValues();

        // Изменяемые данные
        cv.put(
                ArticlesTable.COLUMN_ARTICLE_RATING,
                newRating
        );

        // Запрос на изменение базы данных
        /*helper.getWritableDatabase().update(
                ArticlesTable.TABLE_ARTICLES,
                cv,
                ArticlesTable.COLUMN_ARTICLE_ID+"=?",
                new String[]
                {
                        Integer.toString(articleId)
                }
        );*/
        /*getContext().getContentResolver().update(
                ArticlesContentProvider.URI,
                cv,
                "_id="+articleId,
                null
        );*/
        //равнозначно

        //порождённый поток для update
        new Thread() {
            @Override
            public void run() {
                getContext().getContentResolver().update(
                        ContentUris.withAppendedId(ArticlesContentProvider.URI, articleId),
                        cv,
                        null,
                        null
                );
            }
        }.start();

        // Обновим курсор в адаптере, чтобы изменился список
        //updateCursorInAdapter();
    }


    // Пустой конструктор по-умолчанию
    public ArticlesListFragment() {
    }

    // Метод жизненного цикла
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Так как это ListFragment, нет необходимости инфлейтить xml -
        // ссылку на ListView можно получить через getListView()


        // Получение ссылки на ListView для регистрации щелчка        
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
        // Реакция на щелчок на статью в списке
        // Вызываем интерфейсный метод в активности
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (host != null) {
                    Cursor cursor = adapter.getCursor();
                    // Cursor cursor = getArticlesCursor();
                    cursor.moveToPosition(i);
                    float articleRating = cursor.getFloat(
                            cursor.getColumnIndex(ArticlesTable.COLUMN_ARTICLE_RATING)
                    );
                    int articleId = cursor.getInt(
                            cursor.getColumnIndex(ArticlesTable.COLUMN_ARTICLE_ID)
                    );
                    String articleURL = cursor.getString(
                            cursor.getColumnIndex(ArticlesTable.COLUMN_ARTICLE_URL)
                    );
                    if(host != null)
                        host.articleSelected(articleId, articleURL, articleRating);
                }
            }
        });

    }

    // Метод жизненного цикла фрагмента
    // Нужен чтобы получить ссылку на активность,
    // привести ее к интерфейсному типу
    // создать хелпер, адаптер, присвоить адаптер
    // и выполнить запрос к базе данных
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //helper = new ArticlesHelper(context);
        adapter = new ArticlesAdapter(context, null);
        setListAdapter(adapter);
        //updateCursorInAdapter();
        Log.d("happy", "ArticlesListFragment onAttach");

        if (context instanceof ArticleListFragmentHost)
            host = (ArticleListFragmentHost) context;
        else
            throw new RuntimeException(context.toString()
                    + " must implement ArticleListFragmentHost!");
    }

    // Получем новый курсор.
    // Заменяем курсор в адаптере.
    /*private void updateCursorInAdapter() {
        Cursor cursor = getArticlesCursor();
        adapter.swapCursor(cursor);
    }*/

    // Метод жизненного цикла фрагмента.
    // Присваиваем листу нулевой адаптер
    // Заменяем в адаптере курсор на null
    // зануляем адаптер
    // закрываем хелпер
    // зануляем хелпер
    // обнуляем ссылку на активность,
    @Override
    public void onDetach() {
        super.onDetach();
        setListAdapter(null);
        adapter.swapCursor(null);
        adapter = null;
        //helper.close();
        //helper = null;
        host = null;
        Log.d("happy", "ArticlesListFragment onDetach");
    }
}
