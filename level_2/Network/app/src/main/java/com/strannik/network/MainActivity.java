package com.strannik.network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public NetworkMonitor monitor = new NetworkMonitor();

    public static final IntentFilter filter = new IntentFilter(
            ConnectivityManager.CONNECTIVITY_ACTION
    );

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(monitor, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(monitor);
    }

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkNetwork(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if (info != null && info.isConnected()) {
            Toast.makeText(this, "Connected!", Toast.LENGTH_SHORT).show();
            //узнать какая сеть
            switch (info.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    Log.d(TAG, "checkNetwork: WI-FI");
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    Log.d(TAG, "checkNetwork: Mobile");
                    break;
            }
        } else {
            Toast.makeText(this, "Not connected!", Toast.LENGTH_SHORT).show();
        }


        //in service
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (info != null && info.isConnected()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                //можно использовать сеть

            } else if(!powerManager.isDeviceIdleMode() || powerManager.isIgnoringBatteryOptimizations(getPackageName())) {
                //не находится в режиме энергосбережения или игнорирует оптимизацию энергосбережения для данного приложения
                // можно использовать сеть
            } else {
                //сеть исользоваь нельзя
            }

        }
    }
}