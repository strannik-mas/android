package com.example.student1.activityservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

public class MyReceiver extends BroadcastReceiver {

    // По всей вероятности, этого можно было бы и не делать,
    // так как в данном случае время жизненный цикл ресивера
    // и активити одинаков
    private WeakReference<MainActivity> activity;

    public MyReceiver(MainActivity activity)
    {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Сразу же устанавливаем флаг, что
        // intent был обработан, чтобы
        // сервис не стал "поджигать" уведомления
        intent.putExtra(MyService.DOWNLOAD_HANDLED, true);
        String  action = intent.getAction();
        if(!TextUtils.isEmpty(action) && activity != null)
        {
            MainActivity main = activity.get();
            if(main != null)
            {
                if(action.equals(MyService.DOWNLOAD_URL)) {
                    String filename = intent.getStringExtra(MyService.DOWNLOAD_URL);
                    main.updateImage(filename);
                }
                else if(action.equals(MyService.DOWNLOAD_PROGRESS))
                {

                    int progress = intent.getIntExtra(MyService.DOWNLOAD_PROGRESS, -1);
                    if (progress > -1) {
                        main.updateProgress(progress);
                    }

                }
            }
        }
    }
}
