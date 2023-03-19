package com.example.gactranslator;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

public class Repository {
    private Context appContext;

    private Repository(Context context) {
        this.appContext = context.getApplicationContext();
    }

    private static Repository instance;
    private static TranslateResultDb db;

    MutableLiveData<Long> time = new MutableLiveData<>();

    public static synchronized Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    public LiveData<Long> getTime() {
        return time;
    }

    public void newTime(long l) {
        time.postValue(l);
    }



    public static synchronized TranslateResultDb getDb() {
        if (db == null) {
            Context context = MyApplication.getInstance();
            db = Room.databaseBuilder(context, TranslateResultDb.class, "translate.db").build();
        }
        return db;
    }

    public LiveData<TranslateResult> translate(String sentece, String lang) {
        Context context = MyApplication.getInstance();
        Intent service = new Intent(context, TranslateService.class);
        service.putExtra(TranslateService.SENTENCE, sentece);
        service.putExtra(TranslateService.LANG, lang);

        context.startService(service);

        return getDb().translateDao().translate(sentece, lang);
    }
}
