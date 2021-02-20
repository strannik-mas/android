package com.example.student1.activityservice;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


/*
    Идея приложения
    ---------------

    По нажатию на кнопку загружается картинка.
    Если у приложения нет прав на запись на sd-карту, они запрашиваются.
    Картинка загружается сервисом.
    Активити регистрирует ресивер на получение броадкастов о проценте загрузки.
    Активити регистрирует ресивер на получение броадкаста об окончании загрузки.
    Если броадкаст обрабатывается ресивером, он фиксирует это в интенте броадкаста.
    Сервис посылает синхронные броадкасты, передавая в них процент загрузки.
    Сервис посылает синхронный броадкаст, когда картинка загружена.
    Если сервис обнаруживает, что броадкаст не был обработан, сервис показывает уведомление.
    При нажатии на уведомление об окончании загрузки файла, через Pending Intent в
    уведомлении запускается активити, получающая через интент путь к файлу загруженной картинки
 */

// Задача: поправить приложение везде, где есть комментарий -
/******* нужно что-то сделать  *********/
// в активити, сервисе и ресивере


public class MainActivity extends AppCompatActivity {

    // Для запроса прав на запись на sd-карту
    private static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 25;

    // Что загружаем
    private String url = "https://static.asiawebdirect.com/m/phuket/portals/phuket-com/homepage/phuket-magazine/freedom-beach/pagePropertiesImage/freedom-beach.jpg";

    private String downloadedFileName;

    private ImageView image;

    private ProgressBar progress;

    // Ресивер, который будет получать сообщения от сервиса
    private BroadcastReceiver receiver = new MyReceiver(this);

    // Регистрируем ресивер на получение сообщений от сервиса
    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);

        manager.registerReceiver(
                receiver,
                // Интент фильтр сообщений, которые нам интересны
                new IntentFilter(MyService.DOWNLOAD_URL)
        );

        manager.registerReceiver(
                receiver,
                // Интент фильтр сообщений, которые нам интересны
                new IntentFilter(MyService.DOWNLOAD_PROGRESS)
        );
    }

    // Дерегистрируем ресивер
    @Override
    protected void onPause() {
        super.onPause();
        // Не хотим получать броадкасты при остановленной активити
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(receiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!TextUtils.isEmpty(downloadedFileName))
            outState.putString(MyService.DOWNLOAD_URL, downloadedFileName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.image);

        progress = (ProgressBar) findViewById(R.id.progress);

        if (savedInstanceState != null && savedInstanceState.containsKey(MyService.DOWNLOAD_URL)) {
            downloadedFileName = savedInstanceState.getString(MyService.DOWNLOAD_URL);
        } else {
            Intent activityStartIntent = getIntent();
            if (activityStartIntent.hasExtra(MyService.DOWNLOAD_URL)) {
                downloadedFileName = activityStartIntent.getStringExtra(MyService.DOWNLOAD_URL);
            }
        }

        if (!TextUtils.isEmpty(downloadedFileName))
            updateImage(downloadedFileName);
    }

    // Вызывается из ресивера
    public void updateImage(final String path) {
        downloadedFileName = path;

        // Показываем картинку
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //работает в порождённом потоке
                image.setImageBitmap(
                        BitmapFactory.decodeFile(downloadedFileName)
                );
                progress.setVisibility(View.INVISIBLE);
            }
        });
    }


    void doDownload() {
        // Если версия андроид больше или равна M и нужных прав нет, 
        // попросим пользователя дать их нам
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        } else {
            // Если права уже есть или версия андроид меньше M
            // просто запустим сервис
            Intent downloadServiceIntent = new Intent(this, MyService.class);
            // передав ему УРЛ файла для загрузки
            downloadServiceIntent.putExtra(MyService.DOWNLOAD_URL, url);
            startService(downloadServiceIntent);
        }
    }

    // Вызывается, когда пользователь закрывает диалог 
    // с предложением выбать права
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_WRITE_EXTERNAL_STORAGE) {
            // Если права даны
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                doDownload();
            } else {
                // Пользователь не выдал нужных прав
                Toast.makeText(this, "Cannot download images without permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Вызывается по нажатию на кнопку
    public void download(View view) {
        doDownload();
    }

    public void updateProgress(final int downloadedSoFar) {
        // Показываем прогресс
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(View.VISIBLE);
                progress.setProgress(downloadedSoFar);
            }
        });
    }
}
