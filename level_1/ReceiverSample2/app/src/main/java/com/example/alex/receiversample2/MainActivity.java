package com.example.alex.receiversample2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_SEND_TIMESTAMP = "com.example.strannik.receiversample.intent.action.SEND_TIMESTAMP";
    public static final String TIMESTAMP = "timestamp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String text = getIntent().getStringExtra(NewReceiver.KEY_TEXT_FIELD);
        TextView tv = (TextView) findViewById(R.id.textView);
        if (tv != null) {
            tv.setText(text);
        }

        //для отсылки обратно
        Intent intent = new Intent(ACTION_SEND_TIMESTAMP);
        intent.putExtra(TIMESTAMP, System.currentTimeMillis());
        sendBroadcast(intent);
    }
}
