package com.strannik.looperhandler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyThread extends Thread {

    public MyHandler handler;

    @Override
    public void run() {
        Looper.prepare();
        handler = new MyHandler();
        Looper.loop();
    }
}

class MyHandler extends Handler {
    private static final String TAG = "MyHandler";
    @Override
    public void handleMessage(@NonNull Message msg) {
        Log.d(TAG, "handleMessage: " + System.currentTimeMillis());
    }
}
