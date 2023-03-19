package com.example.gactranslator;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TranslateResult.class}, version = 1)
public abstract class TranslateResultDb extends RoomDatabase {
    public abstract TranslateResultDao translateDao();
}
