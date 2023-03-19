package com.example.autovalueretrofittranslatormy.av;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.autovalueretrofittranslatormy.R;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AVMyMemoryMainActivity extends AppCompatActivity implements Callback<AVMyMemoryTranslate.ResponseData> {
    private EditText source;
    private TextView destination;

    private static final String ENDPOINT = "https://translated-mymemory---translation-memory.p.rapidapi.com";
    private static final String LANG="en|ru";

    private static GsonConverterFactory gsonConverter =
            GsonConverterFactory.create(
                    new GsonBuilder()
                            .registerTypeAdapterFactory(RetrofitGsonTypeAdapterFactory.create())
                    .create());

    private static AVMyMemoryTranslate.ResponseData translate =
            new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(gsonConverter)
                    .build()
                    .create(AVMyMemoryTranslate.ResponseData.class);

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
            Call<AVMyMemoryTranslate.ResponseData> result = translate.translate(LANG, textToTranslate);
            result.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<AVMyMemoryTranslate.ResponseData> call, Response<AVMyMemoryTranslate.ResponseData> response) {
        if (response.body() != null) {

        }
        //destination.setText(response.body().responseData.text().get(0));
    }

    @Override
    public void onFailure(Call<AVMyMemoryTranslate.ResponseData> call, Throwable t) {
        Log.d("happy", t.getMessage());
    }
}