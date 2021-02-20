package com.example.alex.dialogsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements BrowserCall{
    static final CharSequence[] ENGINE_NAMES = {"Google", "Yandex", "Yahoo",};
    private static final String[] ENGINE_URLS = {"google.com", "ya.ru", "yahoo.com",};
    final static String URL_PREFIX  = "http://";

    final static String KEY_IDX = "KEY_IDX";
    static final String ACTION_ENGINE_SELECTED = "com.example.alex.dialogsample.intent.action.ENGINE_SELECTED";

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int idx = intent.getIntExtra(KEY_IDX, -1);
            callBrowser(idx);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(ACTION_ENGINE_SELECTED);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.selectButton1:
                DialogFragment1 df = new DialogFragment1();
                df.show(getSupportFragmentManager(), "SearchDialogOne");
                break;
            case R.id.selectButton2:
                DialogFragment2 df2 = new DialogFragment2();
                df2.show(getSupportFragmentManager(), "SearchDialogTwo");
                break;
            default:
                Toast.makeText(this, "OOPS", Toast.LENGTH_LONG).show();
        }
    }

    public void callBrowser(int idx) {
        if (idx >= 0 || idx < ENGINE_URLS.length) {
            Uri webpage = Uri.parse(URL_PREFIX + ENGINE_URLS[idx]);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
