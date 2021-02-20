package com.example.student1.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;

import java.util.List;


class Utility {
    private Utility() {};

    static PendingIntent getUriPendingIntent(Context context, int pendingIntentId,  String uri)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));

        return PendingIntent.getActivity(
                context, pendingIntentId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    static PendingIntent getTaskStackPendingIntent(Context context, int pendingIntentId, List<Intent> intents)
    {

        // Сформируем стэк активностей
        TaskStackBuilder task = TaskStackBuilder.create(context);

        for(Intent i: intents)
        {
            task.addNextIntent(i);
        }

        return task.getPendingIntent(
                pendingIntentId,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

}
