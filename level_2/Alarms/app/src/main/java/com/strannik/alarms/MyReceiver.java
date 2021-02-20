package com.strannik.alarms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        /*
        // При вызове создаем интент на запуск активити
        //
        Intent i = null;

        // Оборачиваем его в PendingIntent, так как он
        // будет запускаться через NotificationManager
        PendingIntent pI = null;

        // Создание builder-а для Notification
        NotificationCompat.Builder builder = null;

        // Создание нотификации
        NotificationManagerCompat.from(context).notify(
                R.integer.NOTIFICATION_ID, // Желательно сделать константой
                builder.build()
        );

         */
        Log.d(TAG, "onReceive: ");
    }
}
