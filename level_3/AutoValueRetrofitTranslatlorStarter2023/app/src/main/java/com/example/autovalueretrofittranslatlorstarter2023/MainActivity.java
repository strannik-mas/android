package com.example.autovalueretrofittranslatlorstarter2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<Translate> {
    private EditText source;
    private TextView destination;

    private static final String ENDPOINT = "https://translated-mymemory---translation-memory.p.rapidapi.com";
    private static final String LANG="en|ru";

    private static MyMemoryTranslate translate =
            new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MyMemoryTranslate.class);

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
            Call<Translate> result = translate.translate(LANG, textToTranslate);
            result.enqueue(this);
        }
    }

    @Override
    public void onResponse(@NonNull Call<Translate> call, Response<Translate> response) {
        assert response.body() != null;
        ResponseData data = response.body().getResponseData();
        destination.setText(data.getTranslatedText());
    }

    @Override
    public void onFailure(Call<Translate> call, Throwable t) {
        Log.d("happy", t.getMessage());
    }
}