package com.example.student1.simplefragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void b1Click(View view) {
        // Используем транзакцию для добавления фрагмента
        // В рамках одной транзакции можно произвести 
        // много операций по добавлению, замене и удалению фрагментов.
        // В конце требуется выполнить commit
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Нужно указать идентификтор лэйаута куда будет добавлен фрагмент
        transaction.replace(R.id.host, new FirstFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Так можно задать цвет:
    // 0xccaabbcc
    // Color.black

    public void b2Click(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Используем статический конструктор для создания фрагментов с параметрами
        transaction.replace(R.id.host, GenericFragment.newInstance(0xddaabbcc, "Second"));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void b3Click(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.host, GenericFragment.newInstance(0x77009900, "Third"));
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
