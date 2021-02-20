package com.nocompany.articlesflarmentpuresqlite;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.nocompany.articlesflarmentpuresqlite.db.ArticlesHelper;
import com.nocompany.articlesflarmentpuresqlite.db.ArticlesTable;

import java.util.ArrayList;

public class ArticlesContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.nocompany.articlesflarmentpuresqlite.articles";
    public static final Uri URI = Uri.parse(
            "content://" + AUTHORITY + "/elements"
    );

    //Вся таблица:
    //content://com.nocompany.articlesflarmentpuresqlite.articles/elements

    //Одна строка:
    //content://com.nocompany.articlesflarmentpuresqlite.articles/elements/666

    public static final UriMatcher matcher;

    public static final int ALL_ROWS = 1;
    public static final int SINGLE_ROW = 2;
    private ArticlesHelper helper;

    //статический инициализатор - блок кода, который будет выполнен 1 раз при загрузке класса
    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "elements", ALL_ROWS);
        matcher.addURI(AUTHORITY, "elements/#", SINGLE_ROW);
        //# - регулярка (число)
        //* - регулярка (строка)
    }

    @Override
    public boolean onCreate() {
        helper = new ArticlesHelper(getContext());
        return true;
    }

    
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,  @Nullable String[] projection,  @Nullable String selection,  @Nullable String[] selectionArgs,  @Nullable String sortOrder) {
        SQLiteDatabase db = helper.getReadableDatabase();

        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        selection = getSelection(uri, selection);
        Cursor cursor = db.query(
                ArticlesTable.TABLE_ARTICLES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        //уведомление об изменении таблицы
        cursor.setNotificationUri(
                getContext().getContentResolver(), URI
        );

        return cursor;
    }

    private String getSelection(@NonNull Uri uri, @Nullable String selection) {
        switch (matcher.match(uri)) {
            case SINGLE_ROW:
                String rowId = uri.getPathSegments().get(1);
                //if (! selection.isEmpty()) {  //неверно, т.к. selection может быть null и тогда будет Exception
                if (!TextUtils.isEmpty(selection)) {
                    //color='black' and _id=55
                    //(color='black' or age > 40) and _id=55
                    selection = "_id=" + rowId + " AND (" + selection + ")";
                } else {
                    selection = "_id=" + rowId;
                }
                break;
        }
        return selection;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)){
            case ALL_ROWS:
                return "vnd.android.cursor.dir/vnd.articles";
            case SINGLE_ROW:
                return "vnd.android.cursor.item/vnd.articles";
            default:
                throw new IllegalArgumentException("Unsupported uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long inserted = db.insert(
                ArticlesTable.TABLE_ARTICLES,
                null,
                values
        );
        if (inserted > -1) {
            Uri insertedUri = ContentUris.withAppendedId(URI, inserted);
            //уведомление об изменении таблицы
            getContext().getContentResolver().notifyChange(URI, null);
            return insertedUri;
        } else {
            return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();

        selection = getSelection(uri, selection);
        int deleted = db.delete(ArticlesTable.TABLE_ARTICLES, selection, selectionArgs);
        if (deleted > 0) {
            //уведомление об изменении таблицы
            getContext().getContentResolver().notifyChange(URI, null);
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();

        /*
        //будет тормозить т.к. происходит всё-равно в основном потоке
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        selection = getSelection(uri, selection);
        int updated = db.update(ArticlesTable.TABLE_ARTICLES, values, selection, selectionArgs);
        if (updated > 0) {
            //уведомление об изменении таблицы
            getContext().getContentResolver().notifyChange(URI, null);
        }
        return updated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        //в рамках одной транзакции выполнить множественную вставку
        return super.bulkInsert(uri, values);
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        //для смешанных операций (последовательное выполнение)
        return super.applyBatch(operations);
    }
}
