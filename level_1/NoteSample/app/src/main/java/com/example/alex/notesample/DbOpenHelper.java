package com.example.alex.notesample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by Alex on 12.01.2020.
 */
public class DbOpenHelper extends SQLiteOpenHelper{
    final static String DB_NAME = "notes.db";
    final static int DB_VERSION = 1;
    static final String DB_TABLE = "notes";

    static final String COLUMN_NOTE = "note";
    static final String COLUMN_TIME = "pub_time";
    private static final String DB_CREATE = "CREATE TABLE " + DB_TABLE
    + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NOTE + " TEXT NOT NULL, "
            + COLUMN_TIME + " INTEGER);";
    private static final String DB_UPDATE = "ALTER TABLE " + DB_TABLE + " ADD COLUMN "
            + COLUMN_TIME + " INTEGER";

    Context mContext;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
        //TODO удалить после дебага
        Date date = new Date();
        ContentValues values = new ContentValues();
        for (int i = 0; i < 5; i++) {
            values.put(COLUMN_NOTE, "Note #" + i);
            values.put(COLUMN_TIME, System.currentTimeMillis());
            db.insert(DB_TABLE, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*if (newVersion == 2) {
            db.rawQuery(DB_UPDATE, null);
            ContentValues values = new ContentValues(1);
            values.put(COLUMN_TIME, System.currentTimeMillis());
            db.update(DB_TABLE, values, null, null);
        }*/
    }
}
