package com.example.student1.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReplyActivity extends AppCompatActivity {

    private TextView request;
    private EditText reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reply);

        request = (TextView) findViewById(R.id.request);
        reply = (EditText) findViewById(R.id.reply_text);

        // Intent с помощью которого запущена активити
        Intent intent = getIntent();

        // Получим содержимое inline reply
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            CharSequence requestText = remoteInput.getCharSequence(getResources().getString(R.string.KEY_TEXT_REPLY));
            // И запустим обработчик

            // ...
        }
    }

    // Для отправки ответа на inline reply уведомление
    public void reply(View view) {

    }
}
