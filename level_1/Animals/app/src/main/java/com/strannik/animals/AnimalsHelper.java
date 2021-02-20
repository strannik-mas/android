package com.strannik.animals;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AnimalsHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "animals.db";
    public static final int VERSION = 1;

    public AnimalsHelper(@Nullable Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        AnimalsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        AnimalsTable.onUpgrade(db, oldVersion, newVersion);
    }
}
