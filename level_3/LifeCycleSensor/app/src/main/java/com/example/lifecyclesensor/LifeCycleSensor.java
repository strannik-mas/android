package com.example.lifecyclesensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.lang.ref.WeakReference;

public class LifeCycleSensor implements LifecycleObserver, SensorEventListener {

    private Context context;
    private WeakReference<Lifecycle> lifecycle;
    private WeakReference<MainActivity.Callback> callback;

    public LifeCycleSensor(Context context, Lifecycle lifecycle, MainActivity.Callback callback) {
        this.context = context.getApplicationContext();
        this.lifecycle = new WeakReference<>(lifecycle);    //чтобы не было ссылки на ресурсы
        this.callback = new WeakReference<>(callback);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void connect() {
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void disconnect() {
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        MainActivity.Callback c = callback.get();
        if (c != null) {
            c.data(sensorEvent.values);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
