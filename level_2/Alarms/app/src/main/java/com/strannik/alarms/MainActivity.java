package com.strannik.alarms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

// При перезагрузке все алармы пропадают
// Для задания расписания после перезагрузки используйте
// специальный BroadcastReceiver с IntentFilter-ом,
// настроенным на событие android.intent.action.BOOT_COMPLETED
//            <intent-filter>
//                <action android:name="android.intent.action.BOOT_COMPLETED"/>
//            </intent-filter>

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText alarmInSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmInSec = (EditText) findViewById(R.id.edit_alarm);
    }

    public void scheduleAlarm(View view) {
        try {
            // Парсим содержимое EditText -
            // может вызывать исключение
            int timeInSeconds = Integer.parseInt(alarmInSec.getText().toString());
            Log.i(TAG, "scheduleAlarm: " + timeInSeconds);

            // Ссылка на системый сервис - AlarmManger
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

            // Интент на запуск Broadcast Receiver
            Intent i = new Intent(this, MyReceiver.class);
            Intent i2 = new Intent(this, MyWakefulReceiver.class);

            // Оборачиваем его в PendingIntent, так как
            // он будет вызван AlarmManager-ом
            PendingIntent pI = PendingIntent.getBroadcast(this, 333, i2, PendingIntent.FLAG_UPDATE_CURRENT);

            // Шедулим alarm
            if (manager != null) {
                if (Build.VERSION.SDK_INT >= 23) {
                    manager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + timeInSeconds*1000,
                            pI
                    );
                } else if(Build.VERSION.SDK_INT >= 19) {
                    manager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + timeInSeconds*1000,
                            pI
                    );
                } else {
                    //в новых версиях выполняются в окнах обслуживания, учитывают dos-mode
                    manager.set(
                            // Время считается с 1 января 1970 года
                            // "разбудить" устройство, если оно будет спать
                            // при наступлении alarm
                            AlarmManager.RTC_WAKEUP,
                            // Время в милисекундах
                            System.currentTimeMillis() + timeInSeconds*1000,
                            pI
                    );
                }
/*
                manager.setRepeating(
                        AlarmManager.RTC,
                        System.currentTimeMillis() + timeInSeconds*1000,
                        10*1000,    //10sec
                        pI
                );*/
                //для новых версиях выполняются в окнах обслуживания, учитывают dos-mode
//                manager.setInexactRepeating();


            }
//            manager.cancel(pI);

            //будильник
            /*Intent intentBel = new Intent(AlarmClock.ACTION_SET_ALARM);
            //для удаления
            Intent remove = new Intent(AlarmClock.ACTION_DISMISS_ALARM);

            intentBel.putExtra(AlarmClock.EXTRA_HOUR, 17);
            intentBel.putExtra(AlarmClock.EXTRA_MINUTES, 25);
            intentBel.putExtra(AlarmClock.EXTRA_MESSAGE, "HiiiiQ");
            intentBel.putExtra(AlarmClock.EXTRA_SKIP_UI, true);


            startActivity(intentBel);   //тут ошибка*/

        }
        catch (NumberFormatException ex)
        {
            Log.e(TAG, "scheduleAlarm: " + ex.getMessage());
        }
    }
}