package com.example.moneytracker;

import com.example.moneytracker.api.AddItemResult;
import com.example.moneytracker.api.BalanceResult;
import com.example.moneytracker.api.RemoveResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("/auth")
    Call<AuthResult> auth(@Query("social_user_id") String userId);

    @GET("/items")
    Call<List<Item>> getItems(@Query("type") String type);

    @POST("/items/add")
    Call<AddItemResult> addItem(
            @Query("price") String price,
            @Query("name") String name,
            @Query("type") String type
    );

    @GET("/balance")
    Call<BalanceResult> balance();

    @POST("items/remove")
    Call<RemoveResult> deleteItem(@Query("id") int itemId);
}
