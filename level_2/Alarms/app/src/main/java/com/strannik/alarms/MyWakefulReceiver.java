package com.strannik.alarms;

import android.content.Context;
import android.content.Intent;

import androidx.legacy.content.WakefulBroadcastReceiver;

public class MyWakefulReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, MyWakefulService.class);
        //не через context.startService(serviceIntent)
        startWakefulService(context, serviceIntent);    //пробудит цп и передаст туда wakelock
    }
}
