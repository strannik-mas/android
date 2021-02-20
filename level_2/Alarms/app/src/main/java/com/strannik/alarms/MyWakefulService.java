package com.strannik.alarms;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyWakefulService extends IntentService {
    private static final String TAG = "MyWakefulService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public MyWakefulService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent: ");
        Toast.makeText(this, "service work!", Toast.LENGTH_LONG).show();
        //освобождение WakeLock
        MyWakefulReceiver.completeWakefulIntent(intent);
    }
}
