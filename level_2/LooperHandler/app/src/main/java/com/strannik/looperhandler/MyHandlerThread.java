package com.strannik.looperhandler;

import android.os.HandlerThread;

/**
 * тут есть встроенный Looper и встроенный Handler
 */
public class MyHandlerThread extends HandlerThread {
    public MyHandlerThread(String name) {
        super(name);
    }


}
