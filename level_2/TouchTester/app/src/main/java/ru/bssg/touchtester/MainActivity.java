package ru.bssg.touchtester;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {


    // https://developer.android.com/training/gestures/multi.html
    // https://developer.android.com/training/gestures/scale.html

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
