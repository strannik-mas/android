package com.example.student.widgetcurrency;

import android.app.AlarmManager;
import android.app.admin.SystemUpdatePolicy;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

public class CurrencyWidget extends AppWidgetProvider {

    public static final int WIDGET_UPDATE_INTERVAL = 10*60*1000;
    // С таким сообщением будет создан интент для периодического обновления
    // виджета
    // Надо зарегистрировать его в IntentFilter виджета в Manifest
    public static final String ACTION_AUTO_UPDATE = "com.example.student.widgetcurrency.AUTOUPDATE";

    // Выполняется при получении "нашего" интента - ACTION_AUTO_UPDATE
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Log.d("happy", "CurrencyWidget onReceive " + action);
        if(action.equals("android.appwidget.action.APPWIDGET_ENABLED"))
            onEnabled(context);
        else if(action.equals("android.appwidget.action.APPWIDGET_DISABLED"))
            onDisabled(context);
        else {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, CurrencyWidget.class);
            int[] widgetIds = appWidgetManager.getAppWidgetIds(componentName);
            onUpdate(context, appWidgetManager, widgetIds);
        }
    }

    // При получении интента на обновление от андроида
    // Частота прописывается в конфиге виджета - xml/appwidget_provider
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d("happy", "CurrencyWidget onUpdate");

        // Для всех виджетов нужного типа
        final int N = appWidgetIds.length;

        // Получение курса из SharedPreferences
        String rate = Utility.getRate(context);

        if(!TextUtils.isEmpty(rate)) {
            // Таких виджетов может быть много, обновим в цикле все
            for (int i = 0; i < N; i++) {
                // Обновить содержимое каждого виджета через RemoteViews
                RemoteViews view = Utility.getAndUpdateRemoteViews(context, rate);

                int appWidgetId = appWidgetIds[i];

                appWidgetManager.updateAppWidget(appWidgetId, view);
            }
        }
    }

    // Вызывается при добавлении первого виджета
    @Override
    public void onEnabled(Context context) {
        Log.d("happy", "CurrencyWidget onEnabled");
        Utility.setupPeriodicWidgetAlarms(context);
        Utility.setupPeriodicServiceAlarms(context);
    }

    // Вызывается при удалении с экрана последнего виджета
    @Override
    public void onDisabled(Context context) {
        Log.d("happy", "CurrencyWidget onDisabled");
        Utility.cancelPeriodicServiceAlarms(context);
        Utility.cancelPeriodicWidgetAlarms(context);
    }
}
