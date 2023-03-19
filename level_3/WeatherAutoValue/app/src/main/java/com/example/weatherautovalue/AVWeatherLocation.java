package com.example.weatherautovalue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AVWeatherLocation {
    @GET("/api/location/search")
    Call<List<AVLocation>> location(
            @Query("query") String city
    );
}
