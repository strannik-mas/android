package com.example.student.widgetcurrency;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class ServiceWakefulReceiver extends WakefulBroadcastReceiver {

    // TODO Переделать на JobScheduler

    // Этот ресивер необходим для запуска сервиса в Doze Mode.
    // Android гарантирует, что в Doze Mode у ресивера выполнится onReceive
    // Ресивер будет выполняться, пока жив Lock
    // Мы можем передать этот Lock в сервис, который освободит его в конце

    // https://developer.android.com/training/scheduling/wakelock.html

    public static final String WAKEFUL = "com.example.student.widgetcurrency.WakefulBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("happy", "ServiceWakefulReceiver onReceive");
        intent.setClass(context, CurrencyIntentService.class);
        WakefulBroadcastReceiver.startWakefulService(context, intent);
    }
}
