package com.example.strannik.currencyrates;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

public class Task extends AsyncTask<String, Void, Result<ArrayList<Map<String, String>>>> {
    private WeakReference<CurrencyRates> activity;

    public Task(CurrencyRates activity) {
        this.activity = new WeakReference<CurrencyRates> (activity);
    }

    @Override
    protected Result<ArrayList<Map<String, String>>> doInBackground(String... strings) {
        return UtilityDownload.download(strings[0]);
    }

    @Override
    protected void onPostExecute(Result<ArrayList<Map<String, String>>> arrayListResult) {
        if(activity != null) {
            CurrencyRates main = activity.get();
            if (main != null) {
                main.downloadCompleted(arrayListResult);
            }
        }
    }
}
