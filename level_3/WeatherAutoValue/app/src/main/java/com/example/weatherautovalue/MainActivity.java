package com.example.weatherautovalue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<AVWeatherCurrent> {
    private EditText source;
    private TextView destination;
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
        setContentView(R.layout.activity_main);

        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
    }

    @Override
    public void onResponse(Call<AVWeatherCurrent> call, Response<AVWeatherCurrent> response) {
        AVWeatherCurrent o = response.body();
        String text = "no";
        if (o != null) {
            text = o.current().getTempC() + " градусов";
        }
        destination.setText(text);
    }

    @Override
    public void onFailure(Call<AVWeatherCurrent> call, Throwable t) {
        Log.d("Error2", t.getMessage());
    }

    public void searchCityWeather(View view) {
        String searchCityText = source.getText().toString();
        if (!TextUtils.isEmpty(searchCityText)) {
            Call<AVWeatherCurrent> result = weatherLocation.weather(KEY, searchCityText, "no");
            result.enqueue(this);
        }
    }
}