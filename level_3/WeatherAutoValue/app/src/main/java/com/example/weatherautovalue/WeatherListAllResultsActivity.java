package com.example.weatherautovalue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherListAllResultsActivity extends AppCompatActivity  implements Callback<AVWeatherCurrent> {
    public static final String DOMEN = "https://api.weatherapi.com";
    private static final String KEY="12560a4c205941fe91a203214231505";

    private static GsonConverterFactory gsonConverter =
            GsonConverterFactory.create(
                    new GsonBuilder()
                            .registerTypeAdapterFactory(RetrofitGsonTypeAdapterFactory.create())
                            .create());

    private static AVWeatherLocation weatherLocation = new Retrofit.Builder()
            .baseUrl(DOMEN)
            .addConverterFactory(gsonConverter)
            .build()
            .create(AVWeatherLocation.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_list_all_results);

        Call<AVWeatherCurrent> result = weatherLocation.weather(KEY, "Famagusta", "no");
        result.enqueue(this);
    }

    @Override
    public void onResponse(Call<AVWeatherCurrent> call, Response<AVWeatherCurrent> response) {
        AVWeatherCurrent o = response.body();
        if (o != null) {
            Log.d("tempC", o.current().getTempC() + " градусов");

            List<WeatherObject> objects = new ArrayList<>();
            objects.add(o.location());
            objects.add(o.current());

            RecyclerView list = findViewById(R.id.list);
            list.setAdapter(new WeatherAdapter(objects));
        }
    }

    @Override
    public void onFailure(Call<AVWeatherCurrent> call, Throwable t) {
        Log.d("Error2", t.getMessage());
    }
}