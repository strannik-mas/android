package com.example.alex.receiversample2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NewReceiver extends BroadcastReceiver{
    static final String KEY_TEXT_FIELD = "KEY_TXT";

    @Override
    public void onReceive(Context context, Intent intent) {
        String text = intent.getStringExtra(KEY_TEXT_FIELD);
        if (text != null) {
            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(KEY_TEXT_FIELD, text);
            context.startActivity(i);
        }
    }
}
