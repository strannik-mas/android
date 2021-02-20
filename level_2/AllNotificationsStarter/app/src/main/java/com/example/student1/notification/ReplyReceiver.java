package com.example.student1.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.widget.Toast;

public class ReplyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat.from(context).cancel(R.id.DIRECT_REPLY_NOTIFICATION_ID);
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            CharSequence input = remoteInput.getCharSequence(context.getResources().getString(R.string.KEY_TEXT_REPLY));
            Toast.makeText(context, "Input: " + input, Toast.LENGTH_LONG).show();
        }
    }
}
