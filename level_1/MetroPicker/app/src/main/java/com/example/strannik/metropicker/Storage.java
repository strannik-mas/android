package com.example.strannik.metropicker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alex on 04.01.2020.
 */
public class Storage {
    private static final String PREFS = "PREFS";
    static final String KEY_STATION = "selectedStation";
    SharedPreferences mPrefs;
    String mDefaultString;



    public Storage(Context mContext) {
        mPrefs = mContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        mDefaultString = mContext.getResources().getString(R.string.no_station_selected_msg);
    }

    String getStation(){
        String station = mPrefs.getString(KEY_STATION, null);
        if (station == null) {
            station = mDefaultString;
        }
        return station;
    }

    void setStation(String stationName){
        SharedPreferences.Editor e = mPrefs.edit();
        if (stationName != null){
            e.putString(KEY_STATION, stationName);
        }else {
            e.remove(KEY_STATION);
        }
        e.apply();
    }
}
