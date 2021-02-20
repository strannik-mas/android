package ru.bssg.jobschedule;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyDownloadService extends IntentService {
    private static final String TAG = "MyDownloadService";
    public MyDownloadService() {
        super("MyDownloadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
    }
}
