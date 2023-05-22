package com.example.weatherautovalue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AVWeatherLocation {
    @GET("/v1/current.json")
    Call<AVWeatherCurrent> weather(
            @Query("key") String key,
            @Query("q") String city,
            @Query("aqi") String aqi
    );
}
