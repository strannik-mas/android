package com.example.gactranslator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.List;

public class MyLocationListener implements LocationListener, LifecycleObserver {
    private static final String TAG = "happy";
    private Context context;
    private Lifecycle lifecycle;

    public MyLocationListener(Context context, Lifecycle lifecycle) {

        this.context = context;
        this.lifecycle = lifecycle;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d(TAG, "onLocationChanged: " + location);
    }

    @Override
    public void onFlushComplete(int requestCode) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void connect() {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //только если состояние активное
        if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5 * 1000,
                    0,
                    this
            );
        }
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void disconnect() {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager.removeUpdates(this);
    }
}
