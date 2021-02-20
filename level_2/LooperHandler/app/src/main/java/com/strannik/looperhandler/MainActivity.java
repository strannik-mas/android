package com.strannik.looperhandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MyThread myThread = new MyThread();
    public MyMainHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * у основного потока есть ссылка на Looper, поэтому тут можно создать Handler
         */
        handler = new MyMainHandler();
        Message message = handler.obtainMessage();
        message.arg1 = 33;
        message.arg2 = 44;
        message.obj = new Object();
        handler.dispatchMessage(message);

        Messenger messenger = new Messenger(handler);
        //в сервисе можно посылать так:
        /*try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/

        Intent i = new Intent();
        //i.putExtra("MYHANDLER", handler)        //нельзя, т.к. не реализует ни Parsable ни Serializable
        i.putExtra("MYMESSENGER", messenger);   //для передачи хендлера основного потока к примеру в сервис

        myThread.start();
    }

    public void postMessage(View view) {
        Message message = myThread.handler.obtainMessage();

        myThread.handler.dispatchMessage(message);
    }
}

/**
 * выполняется в основном потоке
 */
class MyMainHandler extends Handler {
    private static final String TAG = "MyMainHandler";
    @Override
    public void handleMessage(@NonNull Message msg) {
        if (msg.arg1 == 33) {
            Log.d(TAG, "handleMessage: " + msg.arg2);
        }
    }
}