package com.nocompany.nofragmentsmasterdetail.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper  extends SQLiteOpenHelper {

    private static final String ARTICLES_DATABASE = "articles.db";
    private static final  int ARTICLES_DB_VERSION = 1;

    public MySQLiteHelper(Context context) {
        super(context, ARTICLES_DATABASE, null, ARTICLES_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        ArticlesTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        ArticlesTable.onUpgrade(sqLiteDatabase, i, i1);
    }
}
