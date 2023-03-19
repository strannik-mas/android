package com.student.databinding3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        List<MetroObject> objects = new ArrayList<>();
        objects.add(new MetroLine("Кольцевая"));
        objects.add(new MetroStation("Комсомольска"));
        objects.add(new MetroLine("Филевская"));
        objects.add(new MetroLine("Арбатско-Покровская"));
        objects.add(new MetroStation("Таганская"));
        objects.add(new MetroLine("Кожуховская"));
        objects.add(new MetroStation("Выхино"));
        objects.add(new MetroStation("Выхино2"));
        objects.add(new MetroStation("Выхино3"));
        objects.add(new MetroStation("Выхино4"));
        objects.add(new MetroStation("Выхино5"));
        objects.add(new MetroLine("Кожуховская2"));
        objects.add(new MetroStation("Выхино2"));
        objects.add(new MetroStation("Выхино22"));
        objects.add(new MetroStation("Выхино23"));
        objects.add(new MetroStation("Выхино24"));
        objects.add(new MetroStation("Выхино25"));
        objects.add(new MetroLine("Кожуховская3"));
        objects.add(new MetroStation("Выхино3"));
        objects.add(new MetroStation("Выхино32"));
        objects.add(new MetroStation("Выхино33"));
        objects.add(new MetroStation("Выхино34"));
        objects.add(new MetroStation("Выхино35"));

        RecyclerView list = findViewById(R.id.list);

        list.setAdapter(new MetroAdapter(objects));

        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        list.setLayoutManager(new LinearLayoutManager(this));


    }
}
