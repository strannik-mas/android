package com.example.gactranslator;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface MyMemoryTranslate {
    /*@Headers({
            "X-RapidAPI-Key: ba20eb343amsh44adf13c55ad9c2p1498bajsn57dad9beba8b",
            "X-RapidAPI-Host: translated-mymemory---translation-memory.p.rapidapi.com"
    })*/
    @GET("/get")
    Call<Translate> translate(
            @Query("langpair") String langpair,
            @Query("q") String q
    );
}
