package com.example.student1.allsaints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RatingBar;

public class SaintDetail extends AppCompatActivity {

    private static final String TAG = "SaintDetail";
    private WebView mSaintWebView;
    private RatingBar mSaintRating;
    private int mSaintId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saint_detail);

        // Находим элементы управления
        mSaintWebView = (WebView) findViewById(R.id.saint_detail);
        mSaintWebView.setWebViewClient(new WebViewClient());

        mSaintRating = (RatingBar) findViewById(R.id.rating);

        // Получаем Intent, с которым была запущена Activity
        Intent intent = getIntent();
        if (intent != null) {
            // Получаем из Intent переданные параметры

            ///
            String saint = intent.getStringExtra(MainActivity.SAINT_NAME);
            ///

            if (saint != null) {
                //Log.d(TAG, "onCreate: name1 = " + saint);
                // Формируем URL для википедии
                saint = saint.replace(" ", "_");
                String url = "https://en.m.wikipedia.org/wiki/" + saint;
                Log.d(TAG, "onCreate: url = " + url);
                Log.d(TAG, "onCreate: name2 = " + saint);
                mSaintWebView.loadUrl(url);
            }

            // Вначале проверяем, есть ли такое значение
            if(intent.hasExtra(MainActivity.SAINT_RATING))
            {
                float rating = intent.getFloatExtra(MainActivity.SAINT_RATING, 0f);
                mSaintRating.setRating(rating);
            }

            if(intent.hasExtra(MainActivity.SAINT_ID))
            {
                mSaintId = intent.getIntExtra(MainActivity.SAINT_ID, -1);
            }
        }
    }

    // По нажатию на кнопку "Back"
    @Override
    public void onBackPressed() {
        // Формируем Intent, который будет возвращен в вызвавшую нас Activity
        ///
        Intent intent = new Intent();

        // Добавляем в Intent нужные параметры
        intent.putExtra(MainActivity.SAINT_ID, mSaintId);
        intent.putExtra(MainActivity.SAINT_RATING, mSaintRating.getRating());


        // Устанавливаем результат
        setResult(RESULT_OK, intent);

        // Вызываем onBackPressed суперкласса, закрывая Activity
        super.onBackPressed();
    }
}
