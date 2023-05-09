package com.example.autovalueretrofittranslatlorstarter2023.av;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.autovalueretrofittranslatlorstarter2023.Match;
import com.example.autovalueretrofittranslatlorstarter2023.MyMemoryTranslate;
import com.example.autovalueretrofittranslatlorstarter2023.R;
import com.example.autovalueretrofittranslatlorstarter2023.ResponseData;
import com.example.autovalueretrofittranslatlorstarter2023.Translate;
import com.google.gson.GsonBuilder;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AVMainActivity extends AppCompatActivity implements Callback<AVTranslate> {
    private EditText source;
    private TextView destination;

    private static final String ENDPOINT = "https://translated-mymemory---translation-memory.p.rapidapi.com";
    private static final String LANG="en|ru";

    private static GsonConverterFactory gsonConverter = GsonConverterFactory.create(
            new GsonBuilder()
                    .registerTypeAdapterFactory(RetrofitGsonTypeAdapterFactory.create())
                    .create());

    private static AVMyMemoryTranslate translate =
            new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(gsonConverter)
                    .build()
                    .create(AVMyMemoryTranslate.class);

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
            Call<AVTranslate> result = translate.translate(LANG, textToTranslate);
            result.enqueue(this);
        }
    }

    @Override
    public void onResponse(@NonNull Call<AVTranslate> call, Response<AVTranslate> response) {
        String text = "";
        if (response.body() != null) {
            AVTranslate o = response.body();
            List<Match> lm = o.matches();
            for(Match m : lm) {
                text = m.getTranslation();
                break;
            }
        }
        destination.setText(text);
    }

    @Override
    public void onFailure(Call<AVTranslate> call, Throwable t) {
        Log.d("happy", t.getMessage());
    }
}