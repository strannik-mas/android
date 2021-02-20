package com.example.student1.asyncdownload;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

// По нажатию на кнопку загружаем из и-нета 
// картинку по URL и отображаем ее в  ImageView
public class MainActivity extends AppCompatActivity {

    public ImageView imageView;
    public static final String FRAGMENT = "FRAGMENT";
    public static final String PARAMETER_IMG_URL = "PARAMETER_IMG_URL";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        outState.putParcelable("image", bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);

        if(savedInstanceState != null) {
            Bitmap bitmap = savedInstanceState.getParcelable("image");
            imageView.setImageBitmap(bitmap);
        }
    }

    // По нажатию на кнопку
    // запускаем таск,
    // передав туда URL.
    public void download(View view) {
        Task task = new Task(this);
        task.execute("https://hightech.fm/wp-content/uploads/2018/11/59657-780x387.jpg");                                               //и последовательно и параллельно (в зав-ти от версии андроида)
        //task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, "https://hightech.fm/wp-content/uploads/2018/11/59657-780x387.jpg");      //последовательно
        //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "https://hightech.fm/wp-content/uploads/2018/11/59657-780x387.jpg");      //параллельно (несколько рабочих потоков)

    }

    // Вызывается из таска когда загрузка завершена.
    public void downloadCompleted(Result<Bitmap> result) {
        if (result.exception != null)
        {  
            // Если был exception, сообраем об этом
            Toast.makeText(this, "Exeption: " + result.exception.getMessage(), Toast.LENGTH_LONG).show();
        }
        else {
            // Если исключения не было, покажем картинку
            imageView.setImageBitmap(result.result);
        }
    }
}
