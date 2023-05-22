package com.example.weatherautovalue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {
    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getTzId() {
        return tzId;
    }

    public int getLocaltimeEpoch() {
        return localtimeEpoch;
    }

    public String getLocaltime() {
        return localtime;
    }

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("region")
    @Expose
    public String region;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("lat")
    @Expose
    public double lat;
    @SerializedName("lon")
    @Expose
    public double lon;
    @SerializedName("tz_id")
    @Expose
    public String tzId;
    @SerializedName("localtime_epoch")
    @Expose
    public int localtimeEpoch;
    @SerializedName("localtime")
    @Expose
    public String localtime;
}
