package com.example.lifecyclesensor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        getLifecycle().addObserver(new LifeCycleSensor(this, this.getLifecycle(), new Callback()));
    }

    class Callback {
        public void data(float [] data) {
            text.setText("" + data[0]);
        }
    }
}