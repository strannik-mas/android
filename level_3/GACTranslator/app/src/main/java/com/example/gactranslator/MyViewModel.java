package com.example.gactranslator;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    public LiveData<Long> getTime() {
        return Repository.getInstance(MyApplication.getInstance()).getTime();
    }

    public void start() {
        Context context = MyApplication.getInstance();
        Intent service = new Intent(context, MyIntentService.class);
        context.startService(service);
    }
}
