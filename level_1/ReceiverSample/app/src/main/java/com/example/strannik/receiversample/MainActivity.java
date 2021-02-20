package com.example.strannik.receiversample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final String ACTION_TEXT_SEND = "com.example.strannik.receiversample.intent.action.TEXT_SEND";
    static final String ACTION_SEND_TIMESTAMP = "com.example.strannik.receiversample.intent.action.SEND_TIMESTAMP";
    private static final String KEY_TEXT_FIELD = "KEY_TXT";
    public static final String TIMESTAMP = "timestamp";

    EditText mInputField;
    TextView mTimeStamp;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            String text = intent.getStringExtra(KEY_TXT);
            long time = intent.getLongExtra(TIMESTAMP, 0);
//            String text = intent.getStringExtra(KEY_TEXT_FIELD);
//            if (text != null) {
            if (time != 0) {
//                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mTimeStamp.setText(fmt.format(new Date(time)));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputField = (EditText) findViewById(R.id.inputField);
        mTimeStamp = (TextView) findViewById(R.id.timestamp);

        IntentFilter filter = new IntentFilter(ACTION_SEND_TIMESTAMP);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void onClick(View view) {
        String text = mInputField.getText().toString().trim();
        if (text.length() > 0) {
            Intent intent = new Intent(ACTION_TEXT_SEND);
            intent.putExtra(KEY_TEXT_FIELD, text);
            sendBroadcast(intent);
        }
        mInputField.setText(null);
    }
}
