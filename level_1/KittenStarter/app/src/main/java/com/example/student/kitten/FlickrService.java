package com.example.student.kitten;

/*
https://www.flickr.com/services/rest/?
method=flickr.photos.search&
api_key=ac48aeab2258f96b0dfbdce7543b3462&
text=moscow&
format=json&
nojsoncallback=1&
api_sig=c9fc84c4d086384000297a5d87f956ad
 */
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrService {
    @GET("/services/rest/")
    Call<Result> search(
            @Query("method")         String method,
            @Query("api_key")        String key,
            @Query("text")           String text,
            @Query("format")         String format,
            @Query("nojsoncallback") int nojsoncallback,
            @Query("page")           int page
    );
}
