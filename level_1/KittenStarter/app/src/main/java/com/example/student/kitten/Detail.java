package com.example.student.kitten;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {

    private static final String TAG = "Detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Расширим окно Activity 
        // как только возможно
        /*
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        */

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        // Не хотим заголовка
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_detail);

        ImageView image = (ImageView) findViewById(R.id.detail);

        // Получим Intent с помощью которого
        // запустили
        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.IMAGE_URL))
        {
            String url = intent.getStringExtra(MainActivity.IMAGE_URL);
            if(url != null)
            {
                // "Маленькая" картинка имеет в конце URL
                // _q.jpg  , "большая" картинка имеет
                // в конце _h.jpg
                url = url.replace("_q.jpg", "_z.jpg");
                Log.d(TAG, "onCreate: imgURL  " + url);
                
                Picasso.get()
                        .load(url)
                        .fit()
                        .centerCrop()
                        .into(image);
           }
        }
    }
}
