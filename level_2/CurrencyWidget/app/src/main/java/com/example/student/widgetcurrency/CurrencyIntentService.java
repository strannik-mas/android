package com.example.student.widgetcurrency;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.TextUtils;
import android.util.Log;

public class CurrencyIntentService extends IntentService {

    public static final int SERVICE_UPDATE_INTERVAL = 5*60*1000;

    public CurrencyIntentService() {
        super("CurrencyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Log.d("happy", "onHandleIntent");
            // PendingIntent для передачи информации обратно в активити - там
            // выполнится метод onActivityResult
            // Десериализуем его из интента, которым запустили сервис
            PendingIntent activityPendingIntent =
                    intent.getParcelableExtra(getResources().getString(R.string.CONFIG_PENDING_INTENT));

            ConnectivityManager manager = (ConnectivityManager)
                    getSystemService(CONNECTIVITY_SERVICE);

            // Запросим состояние активного подключения к сети
            NetworkInfo info = manager.getActiveNetworkInfo();

            // Только если подключены к сети
            if (info != null && info.isConnected()) {

                // Получим страницу
                String forex = Utility.getPage("https://www.profinance.ru/");

                String rate = "";

                // Найдем в ней курс
                rate = Utility.getUSD(forex);
                Log.d("happy", "onHandleIntent rate is " + rate);

                if (!TextUtils.isEmpty(rate)) {
                    // Ссылка на базу данных SharedPreferences
                    SharedPreferences prefs = getSharedPreferences(
                            getPackageName(),
                            MODE_PRIVATE
                    );
                    if (prefs != null) {
                        // Сохраним курс в Preferences
                        prefs.edit().putString(getResources().getString(R.string.USDRUB), rate).apply();

                        if (activityPendingIntent != null) {
                            // Интент для передачи курса
                            // в активити
                            Intent result = new Intent();
                            result.putExtra(getResources().getString(R.string.USDRUB), rate);

                            try {
                                // Через PendingIntent для связи с активити
                                // отправим ей результат
                                // В активити вызовется onActivityResult
                                activityPendingIntent.send(
                                        this,
                                        R.integer.RATE_UPDATED, // requestCode
                                        result
                                );
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            else
            {
                Log.d("happy", "onHandleIntent, not connected");
            }
        }
        finally {
            // Чтобы освободить WakeLock
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }
}
