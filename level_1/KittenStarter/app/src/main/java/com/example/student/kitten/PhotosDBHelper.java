package com.example.student.kitten;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhotosDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_FILE = "photos.db";
    private static final  int DATABASE_VERSION = 1;

    public PhotosDBHelper(Context context) {
        super(context, DATABASE_FILE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        PhotosTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        PhotosTable.onUpgrade(sqLiteDatabase, i, i1);
    }
}