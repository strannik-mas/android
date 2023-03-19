package com.example.weatherautovalue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<List<AVLocation>> {
    private EditText source;
    private TextView destination;
    public static final String DOMEN = "https://www.metaweather.com";

    private static GsonConverterFactory gsonConverter =
            GsonConverterFactory.create(
                    new GsonBuilder()
                            .registerTypeAdapterFactory(RetrofitGsonTypeAdapterFactory.create())
                            .create());

    private static AVWeatherLocation location = new Retrofit.Builder()
            .baseUrl(DOMEN)
            .addConverterFactory(gsonConverter)
            .build()
            .create(AVWeatherLocation.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
    }

    @Override
    public void onResponse(Call<List<AVLocation>> call, Response<List<AVLocation>> response) {
        if (response.body() != null) {
            destination.setText(response.body().get(0).title());
        }
    }

    @Override
    public void onFailure(Call<List<AVLocation>> call, Throwable t) {
        Log.d("Error", t.getMessage());
    }

    public void searchCity(View view) {
        String searchCityText = source.getText().toString();
        if (!TextUtils.isEmpty(searchCityText)) {
            Call<List<AVLocation>> result = location.location(searchCityText);
            result.enqueue(this);
        }
    }
}