package com.example.gactranslator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //private LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
    //private LocationListener listener = new MyLocationListener(this, this.getLifecycle());
    
    public static final int REQUEST_FINE = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerListener();
    }

    private void registerListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            //запрашиваем права
            requestPermissions(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    REQUEST_FINE
            );
        } else {
            //подписка на активность и её события
            getLifecycle().addObserver(new MyLocationListener(this, this.getLifecycle()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_FINE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                registerListener();
            }
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        /*manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5*1000,
                0,
                listener
        );*/
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onPause() {
        super.onPause();
        //manager.removeUpdates(listener);
    }
}