package com.example.student1.servicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startDumbService(View view) {
        // Запуск сервиса по Intent
        Intent intent = new Intent(this, MyService.class);
        // Пример того, как можно передать данные сервису
        intent.putExtra("HELLO", 123);

        startService(intent);

    }

    // Остановка сервиса по  Intent
    public void stopDumbService(View view) {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    // Запуск IntentService  по Intent
    public void startIntentService(View view) {
        Intent intent = new Intent(this, MyIntentService2.class);
        startService(intent);
    }
}
