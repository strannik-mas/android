package com.example.student1.notification;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class ProgressService extends IntentService {

    public ProgressService() {
        super("ProgressService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        b.setSmallIcon(android.R.drawable.btn_minus);
        b.setContentTitle("Загрузка");
        b.setContentText("Загружаем картинку");

        for(int i = 0; i < 20; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            b.setProgress(100, i*5, false);
            manager.notify(666, b.build());
        }
        b.setContentText("загрузка завершена");
        b.setProgress(0,0,false);
        manager.notify(666, b.build());
    }
}
