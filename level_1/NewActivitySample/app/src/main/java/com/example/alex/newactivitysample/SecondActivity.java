package com.example.alex.newactivitysample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent i = getIntent();
        String name = i.getStringExtra(MainActivity.EXTRA_USER_NAME);
        name = (name == null) ? "Unknown" : name;
        String greeting = getString(R.string.greeting_prefix) + name;
        ((TextView)findViewById(R.id.greeting)).setText(greeting);
    }

    public void onClick(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+38(097)97-91-727"));
        //для планшетов без симок проверим есть ли кто-нибудь, кто готов его обработать
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        } else {
            Toast.makeText(this, "Купи слона", Toast.LENGTH_SHORT).show();
        }

    }
}
