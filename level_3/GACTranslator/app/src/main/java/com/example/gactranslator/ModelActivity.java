package com.example.gactranslator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviderKt;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ModelActivity extends AppCompatActivity {
    private MyViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        model = new ViewModelProvider(this).get(MyViewModel.class);

        LiveData<Long> time = model.getTime();
        /*time.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                Toast.makeText(ModelActivity.this, "text: " + aLong, Toast.LENGTH_SHORT).show();
            }
        });*/

        //подписка на изменение time в Repository
        time.observe(this, aLong -> Toast.makeText(ModelActivity.this, "text: " + aLong, Toast.LENGTH_SHORT).show());
    }

    public void startTimer(View view) {
        model.start();
    }
}