package com.example.gactranslator;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TranslateResultDao {

    @Query("SELECT * FROM RESULT")
    List<TranslateResult> getAll();

    @Query("SELECT * FROM RESULT WHERE sentence = :sentence AND lang = :lang LIMIT 1")
    LiveData<TranslateResult> translate(String sentence, String lang);

    @Insert(onConflict = REPLACE)
    void insertAll (TranslateResult ...results);

    @Query("DELETE FROM RESULT")
    void deleteAll();

    @Delete
    void delete(TranslateResult result);

    @Query("UPDATE RESULT SET status=:status where lang=:lang")
    void updateAll(String lang, String status);

    @Update
    void updateAll(TranslateResult ...results);
}
