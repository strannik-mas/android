package com.nocompany.nofragmentsmasterdetail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.nocompany.nofragmentsmasterdetail.database.ArticlesTable;
import com.nocompany.nofragmentsmasterdetail.database.MySQLiteHelper;
import com.nocompany.nofragmentsmasterdetail.mech.ArticlesListHost;
import com.nocompany.nofragmentsmasterdetail.mech.MyCursorAdapter;

public class ItemListView extends ListView {

    // Так как наследуемся от вью, нужно вызвать конструктор суперкласса
    public ItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private MySQLiteHelper helper;

    // Сервисная функция
    private void updateCursorInAdapter() {
        Cursor cursor = getArticlesCursor();

        ((MyCursorAdapter) getAdapter()).swapCursor(cursor);
    }

    // Метод жизненного цикла
    // вызывается когда вью "надут"
    // Используем для инициализации
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        helper = new MySQLiteHelper(getContext());
        MyCursorAdapter adapter = new MyCursorAdapter(getContext(), null, 0);
        setAdapter(adapter);

        // По щелчку на элементе листа, выполним интерфейсный метод активити
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArticlesListHost host = (ArticlesListHost) getContext();
                // Только если активити имплементит нужный нам интерфейс
                if(host != null)
                {
                    Cursor cursor = ((MyCursorAdapter) getAdapter()).getCursor();

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
                    host.articleSelected(articleId, articleURL, articleRating);
                }

            }
        });
        updateCursorInAdapter();
    }

    // Сервисная функция получения курсора
    private Cursor getArticlesCursor() {
        return  helper.getReadableDatabase().query(
                ArticlesTable.TABLE_ARTICLES,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    // Вызывается активити при изменении рейтинга статьи
    public void articleRatingChanged(int articleId, float newRating) {
        ContentValues cv = new ContentValues();

        cv.put(
                ArticlesTable.COLUMN_ARTICLE_RATING,
                newRating
        );
        helper.getWritableDatabase().update(
                ArticlesTable.TABLE_ARTICLES,
                cv,
                ArticlesTable.COLUMN_ARTICLE_ID+"="+articleId,
                null
        );
        updateCursorInAdapter();
    }

}
