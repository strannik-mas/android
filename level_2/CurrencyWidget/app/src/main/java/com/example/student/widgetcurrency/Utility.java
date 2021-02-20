package com.example.student.widgetcurrency;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    private static final String TAG = "Utility";
    // Шаблон для вызова регулярных выражений
    private static final String regexpUSD = "USD/RUB</a></td><td>(\\d+\\.\\d+)</td><td>(\\d+\\.\\d+)</td><td>(\\d+:\\d+)";

    // Скомпилируем его
    private static final Pattern patternUSD = Pattern.compile(regexpUSD);

    // Проверка, что курс получается правильно
    public static void main(String[] args) {

        String forex = getPage("https://www.profinance.ru");

        // System.out.println(forex);

        //System.out.println(getUSD(forex));

        Log.d(TAG, "main: forex = " + forex);
        Log.d(TAG, "main: kurs = " + getUSD(forex));

    }

    // Получим страницу по url в виде строки
    public static String getPage(String pageUrl)
    {
        StringBuilder buffer = new StringBuilder();

        try {
            URL url = new URL(pageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int resultCode = conn.getResponseCode();

            if(resultCode == HttpURLConnection.HTTP_OK)
            {
                InputStream is = conn.getInputStream();
                // У BufferedReader есть метод readLine()
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                String line = "";

                try {
                    // Читаем пока можем
                    while ((line = br.readLine()) != null) {
                        // и дописываем в буфер
                        buffer.append(line);
                    }
                }
                finally {
                    br.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String getUSD(String text)
    {
        Matcher matcherUSD = patternUSD.matcher(text);

        String result = "";
        // Найдем соответствие шаблону
        if(matcherUSD.find())
        {
            // Первая группа курс продажи
            // Вторая курс покупки
            // Третья время
            // Нулевая совпадение целиком
            result = matcherUSD.group(1);
        }
        Log.d(TAG, "getUSD: result = " + result);
        if (result == "") {
            result = "error";
        }
        return result;
    }

    public static String getRate(Context context) {
        String currencyRate = "";
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(),
                Context.MODE_PRIVATE
        );
        if(prefs != null)
        {
            currencyRate = prefs.getString(
                    context.getResources().getString(R.string.USDRUB),
                    context.getResources().getString(R.string.unknown)
            );
        }
        return currencyRate;
    }

    // Так как виджет не работает в контексте приложения (а в контексте
    // AppWidgetProvider-а), для обновления используем RemoteViews
    public static RemoteViews getAndUpdateRemoteViews(Context context, String rate) {
        RemoteViews rv = new RemoteViews(
                context.getPackageName(),
                R.layout.widget
        );

        if(!TextUtils.isEmpty(rate)) {
            // Значение
            rv.setTextViewText(R.id.usd_text, "USD: " + rate);
        }

        // PendingIntent для запуска конфигурационной активити
        // по щелчку
        rv.setOnClickPendingIntent(
                R.id.usd_text,
                getActivityPendingIntent(context)
        );
        
        return rv;
    }

    // Возврат PendingIntent для запуска конфигурационной Activity
    // Конфигурационная Activity запускается по щелчку на виджете
    public static PendingIntent getActivityPendingIntent(Context context) {
        return PendingIntent.getActivity(
                context,
                R.integer.ACTIVITY_PENDING_INTENT,
                new Intent(context, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    // Удаляет PendingIntent расписаний на запуск сервиса в будущем
    public static PendingIntent cancelServerPendingIntent(Context context)
    {
        return PendingIntent.getService(
                context,
                R.integer.UPDATE_SERVICE_PENDING_INTENT,
                new Intent(context, CurrencyIntentService.class),
                PendingIntent.FLAG_CANCEL_CURRENT
        );
    }

    // PendingIntent на старт сервиса
    public static PendingIntent createServicePendingIntent(Context context)
    {
        //return PendingIntent.getService(
        return PendingIntent.getBroadcast(
                context,
                R.integer.UPDATE_SERVICE_PENDING_INTENT,
                new Intent("com.example.student.widgetcurrency.WakefulBroadcastReceiver", null, context, ServiceWakefulReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    // Установка периодических алармов для старта сервиса
    public static void setupPeriodicServiceAlarms(Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        manager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + CurrencyIntentService.SERVICE_UPDATE_INTERVAL,
                CurrencyIntentService.SERVICE_UPDATE_INTERVAL,
                createServicePendingIntent(context)
        );
    }

    public static PendingIntent createWidgetUpdatePendingIntent(Context context)
    {
        return PendingIntent.getBroadcast(
                context,
                R.integer.UPDATE_WIDGET_PENDING_INTENT,
                // new Intent(CurrencyWidget.ACTION_AUTO_UPDATE),
                new Intent(CurrencyWidget.ACTION_AUTO_UPDATE, null, context, CurrencyWidget.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

    }

    // Установка периодических алармов для обновления виджета
    // public static void setupPeriodicWidgetAlarms(Context context, String action, long interval) {
    public static void setupPeriodicWidgetAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + CurrencyWidget.WIDGET_UPDATE_INTERVAL,
                CurrencyWidget.WIDGET_UPDATE_INTERVAL,
                createWidgetUpdatePendingIntent(context)
        );
    }

    public static void cancelPeriodicServiceAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(cancelServerPendingIntent(context));
    }

    // Удаление периодических алармов для обновления виджета
    // При удалении виджета с экрана
    public static void cancelPeriodicWidgetAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, R.integer.UPDATE_WIDGET_PENDING_INTENT, new Intent(CurrencyWidget.ACTION_AUTO_UPDATE), PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
