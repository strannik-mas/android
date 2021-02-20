package com.strannik.sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;
    private Sensor accel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //проверка есть ли сенсор
        PackageManager packageManager = getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER)) {

            //получение системного сервиса
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

            //какие сенсоры вообще есть в системе
            List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

            accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null && accel != null) {
            sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        }
    }

    /**
     * Выполняется каждый раз, когда мендяются показания или приходят по частоте обновления
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged: " + event.values[0]);
    }

    /**
     *  Изменение точности диапазона измерений сенсора
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}