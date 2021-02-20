package com.example.student1.fragmentmessagebus;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// Активити совершенно пуст
// Для диспетчеризации фрагменты используют
// MessageBus

public class MainActivity extends AppCompatActivity/* implements FirstFragment.FirstFragmentReceiver,
        SecondFragment.SecondFragmentReceiver*/ {

    /*private FirstFragment first;
    private SecondFragment second;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*FragmentManager manager = getSupportFragmentManager();
        first = (FirstFragment) manager.findFragmentById(R.id.fragment_first);
        second = (SecondFragment) manager.findFragmentById(R.id.fragment_second);*/
    }
/*
    @Override
    public void firstReceive(String data) {
        second.dataReceived(data);
    }

    @Override
    public void secondReceive(String data) {
        first.dataReceived(data);
    }*/
}
