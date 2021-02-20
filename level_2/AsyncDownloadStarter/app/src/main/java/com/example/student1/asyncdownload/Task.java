package com.example.student1.asyncdownload;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class Task extends AsyncTask<String, Void, Result<Bitmap>> {
    private WeakReference<MainActivity> activity;

    public Task(MainActivity activity)
    {
        this.activity = new WeakReference<MainActivity>(activity);
    }

    // Загрузим Bitmap
    @Override
    protected Result<Bitmap> doInBackground(final String... params) {
        return Utility.download(params[0]);
    }

    // И передадим результат в активность
    @Override
    protected void onPostExecute(Result<Bitmap> result) {
        if(activity != null) {
            MainActivity main = activity.get();
            if(main != null)
            {
                // Вызвать метод активности
                main.downloadCompleted(result);
            }
        }
    }

    public void newActivity(MainActivity context) {
        this.activity = new WeakReference<MainActivity>(context);
    }

    public void deleteActivity() {
        activity = null;
    }
}
