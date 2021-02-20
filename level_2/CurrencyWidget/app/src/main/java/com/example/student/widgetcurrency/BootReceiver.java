package com.example.student.widgetcurrency;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    // Служит чтобы пересоздать алармы для сервиса и
    // виджета после перезагрузки
    @Override
    public void onReceive(Context context, Intent intent) {
        Utility.setupPeriodicServiceAlarms(context);
        Utility.setupPeriodicWidgetAlarms(context);
    }
}
