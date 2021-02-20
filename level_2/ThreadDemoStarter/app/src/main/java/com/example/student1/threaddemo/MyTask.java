package com.example.student1.threaddemo;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

// 1 параметр передается в doInBackground
// 2 параметр передается в publishProgress и onProgressUpdate
// 3 параметр возвращается из doInBackground и передается в onPostExecute
public class MyTask extends AsyncTask<Void, Integer, Void>
{
    private WeakReference<MainActivity> activity;

    public MyTask(MainActivity activity) {
        //мягкая ссылка - её может удалять garbage collector, т.е. нет утечек
        this.activity = new WeakReference<MainActivity>(activity);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for(int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (activity != null) {
            MainActivity main = activity.get();
            if (main != null)
                main.progressUpdate(values[0]);
        }
    }

    public void newActivity(MainActivity context) {
        this.activity = new WeakReference<MainActivity>(context);
    }

    public void deleteActivity() {
        this.activity = null;
    }
}
