package com.example.autovalueretrofittranslatormy.av;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autovalueretrofittranslatormy.R;
import com.example.autovalueretrofittranslatormy.Translate;
import com.example.autovalueretrofittranslatormy.YandexTranslate;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AVMainActivity extends AppCompatActivity implements Callback<AVTranslate> {
    private EditText source;
    private TextView destination;

    private static final String KEY = "trnsl.1.1.20170331T163527Z.6d3042c2a9aad0e7.2ce36155bc4a0c5b432344b88b634c25287243c2";
    private static final String ENDPOINT = "https://translate.yandex.net";
    private static final String LANG="ru-fr";

    private static GsonConverterFactory gsonConverter =
            GsonConverterFactory.create(
                    new GsonBuilder()
                            .registerTypeAdapterFactory(RetrofitGsonTypeAdapterFactory.create())
                    .create());

    private static AVYandexTranslate translate =
            new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(gsonConverter)
                    .build()
                    .create(AVYandexTranslate.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
    }

    public void translate(View view) {
        String textToTranslate = source.getText().toString();
        if(!TextUtils.isEmpty(textToTranslate))
        {
            Call<AVTranslate> result = translate.translate(KEY, LANG, textToTranslate);
            result.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<AVTranslate> call, Response<AVTranslate> response) {
        destination.setText(response.body().text().get(0));
    }

    @Override
    public void onFailure(Call<AVTranslate> call, Throwable t) {
        Log.d("happy", t.getMessage());
    }
}