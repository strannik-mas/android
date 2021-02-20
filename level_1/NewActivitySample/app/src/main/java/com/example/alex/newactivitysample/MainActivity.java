package com.example.alex.newactivitysample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    static final String EXTRA_USER_NAME = "USER NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        EditText input = (EditText) findViewById(R.id.userName);
        String userName = input.getText().toString().trim();
        if (userName.length() > 0) {
            Intent i = new Intent(this, SecondActivity.class);
            i.putExtra(EXTRA_USER_NAME, userName);
            startActivity(i);
        }
        input.setText(null);
    }
}
