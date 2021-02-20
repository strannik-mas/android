package com.example.student.widgetcurrency;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

// Что нужно сделать
// 1. Класс виджета, производный от AppWidgetProvider
// 2. Прописать виджет в Manifest как BroadcastReceiver
// 3. Добавить ему IntentFilter
// <intent-filter>
//     <action android:name="android.appwidget.action.APPWIDGET_UPDATE"/>
// </intent-filter>
// 4. Добавить ему meta-data с ссылкой на его конфиг
// <meta-data
//     android:name="android.appwidget.provider"
//     android:resource="@xml/appwidget_provider"/>
// 5. Создать конфиг виджета
// 6. Создать конфигурационную активити
// 7. Поправить ее интент-фильтр
// 8. В активити получить идентификатор конфигурируемого виджета
// 9. Настроить его
// 10. Создать "возвратный" интент и сохранить туда идентификатор виджета
// 11. Возвратить этот интент установив результат активити ОК
// 12. Завершить активити

public class MainActivity extends AppCompatActivity {

    // Идентификатор конфигурируемого виджета по-умолчанию
    private int mWidget = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Результат активити по-умолчанию
        setResult(RESULT_CANCELED);

        // Извлечем идентификатор конфигурируемого виджета из интента
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mWidget = bundle.getInt(
                    // Ключ, по которому в интенте хранится идентификатор
                    // виджета
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    // Идентификатор конфигурируемого виджета по-умолчанию
                    AppWidgetManager.INVALID_APPWIDGET_ID
            );
        }
    }

    // Обработчик нажатия на кнопку "Request exchange rate"
    public void requestRate(View view) {
        Intent intent = new Intent(this, CurrencyIntentService.class);

        // Специальный PendingIntent, который позволит сервису слать
        // информацию обратно в активити
        PendingIntent pendingIntentResult = createPendingResult(
                R.integer.PENDING_RESULT_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Сериализуем его в интент, с помощью которого
        intent.putExtra(getResources().getString(R.string.CONFIG_PENDING_INTENT), pendingIntentResult);

        // Запустим сервис
        startService(intent);
    }

    // Вызывается каждый раз когда в сервисе шлется сигнал через
    // переданный туда PendingIntent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("happy", "onActivityResult");
        Log.d("happy", data.getStringExtra(getResources().getString(R.string.USDRUB)));
        updateWidget();
        // super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateWidget() {
        Log.d("happy", "updateWidget");

        // Получим курс из SharedPreferences
        String rate = Utility.getRate(this);

        // Настроим виджет
        RemoteViews rv = Utility.getAndUpdateRemoteViews(this, rate);

        Intent result = new Intent(CurrencyWidget.ACTION_AUTO_UPDATE);

        if(mWidget != AppWidgetManager.INVALID_APPWIDGET_ID)
        {
            AppWidgetManager manager = AppWidgetManager
                    .getInstance(getApplicationContext());
            // Обновим его через AppWidgetManager


            manager.updateAppWidget(mWidget, rv);

            // Создадим "возвратный" интент

            // Сохраним туда идентификатор виджета, который настроили
            result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidget);

            // Установим результат
            setResult(RESULT_OK, result);

            // Нет необходимсти запускать периодику - она запускается из
            // CurrencyWidget.onEnabled

            // Завершим активити, возвратив результат в AppWidgetManager
        }
        else {
            sendBroadcast(result);
            Log.d("happy", "updateWidget sending broadcast");
        }
        finish();
    }
}
