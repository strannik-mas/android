package com.strannik.mapsgeofences;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class GeoFenceReceiver extends BroadcastReceiver {
    public GeoFenceReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new  NotificationCompat.Builder(context)
                .setContentTitle("Ohakune")
                .setContentTitle("Ohakune - open a wiki page")
                .setSmallIcon(android.R.drawable.btn_star)
                .setContentIntent(getPendingIntent(context));

        ((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(123, builder.build());

        Log.d("happy", "GeoFenceReceiver onReceive");

    }

    public static PendingIntent getPendingIntent(Context context)
    {
        String url = "https://en.wikipedia.org/wiki/Ohakune";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));

        return PendingIntent.getActivity(
                context,
                1,
                i,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }
}
