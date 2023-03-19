package com.example.gactranslator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TranslateViewModel extends AndroidViewModel {

    private MyApplication application;

    public TranslateViewModel(@NonNull Application application) {
        super(application);
        this.application = (MyApplication) application;
    }

    public LiveData<TranslateResult> getTranslate(String sentence, String lang) {
        return Repository.getInstance(application).translate(sentence, lang);
    }
}
