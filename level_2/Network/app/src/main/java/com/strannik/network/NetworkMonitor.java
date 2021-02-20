package com.strannik.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class NetworkMonitor extends BroadcastReceiver {
    private static final String TAG = "NetworkMonitor";
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if (info != null && info.isConnected()) {
            Toast.makeText(context, "Connected!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, "Not connected!", Toast.LENGTH_SHORT).show();
        }
    }
}
