package com.example.weatherautovalue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current {
    @SerializedName("last_updated_epoch")
    @Expose
    public int lastUpdatedEpoch;
    @SerializedName("last_updated")
    @Expose
    public String lastUpdated;
    @SerializedName("temp_c")
    @Expose
    public double tempC;
    @SerializedName("temp_f")
    @Expose
    public double tempF;
    @SerializedName("is_day")
    @Expose
    public int isDay;
    @SerializedName("condition")
    @Expose
    public Condition condition;
    @SerializedName("wind_mph")
    @Expose
    public double windMph;
    @SerializedName("wind_kph")
    @Expose
    public double windKph;
    @SerializedName("wind_degree")
    @Expose
    public int windDegree;
    @SerializedName("wind_dir")
    @Expose
    public String windDir;
    @SerializedName("pressure_mb")
    @Expose
    public double pressureMb;
    @SerializedName("pressure_in")
    @Expose
    public double pressureIn;
    @SerializedName("precip_mm")
    @Expose
    public double precipMm;
    @SerializedName("precip_in")
    @Expose
    public double precipIn;
    @SerializedName("humidity")
    @Expose
    public int humidity;
    @SerializedName("cloud")
    @Expose
    public int cloud;
    @SerializedName("feelslike_c")
    @Expose
    public double feelslikeC;
    @SerializedName("feelslike_f")
    @Expose
    public double feelslikeF;
    @SerializedName("vis_km")
    @Expose
    public double visKm;
    @SerializedName("vis_miles")
    @Expose
    public double visMiles;
    @SerializedName("uv")
    @Expose
    public double uv;
    @SerializedName("gust_mph")
    @Expose
    public double gustMph;
    @SerializedName("gust_kph")
    @Expose
    public double gustKph;

    public int getLastUpdatedEpoch() {
        return lastUpdatedEpoch;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public double getTempC() {
        return tempC;
    }

    public double getTempF() {
        return tempF;
    }

    public int getIsDay() {
        return isDay;
    }

    public Condition getCondition() {
        return condition;
    }

    public double getWindMph() {
        return windMph;
    }

    public double getWindKph() {
        return windKph;
    }

    public int getWindDegree() {
        return windDegree;
    }

    public String getWindDir() {
        return windDir;
    }

    public double getPressureMb() {
        return pressureMb;
    }

    public double getPressureIn() {
        return pressureIn;
    }

    public double getPrecipMm() {
        return precipMm;
    }

    public double getPrecipIn() {
        return precipIn;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public double getFeelslikeC() {
        return feelslikeC;
    }

    public double getFeelslikeF() {
        return feelslikeF;
    }

    public double getVisKm() {
        return visKm;
    }

    public double getVisMiles() {
        return visMiles;
    }

    public double getUv() {
        return uv;
    }

    public double getGustMph() {
        return gustMph;
    }

    public double getGustKph() {
        return gustKph;
    }
}
