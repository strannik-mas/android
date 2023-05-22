package com.example.weatherautovalue;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class AVWeatherCurrent {
    @SerializedName("location")
    public abstract Location location();
    @SerializedName("current")
    public abstract Current current();

    public static AVWeatherCurrent create(Location location, Current current) {
        return new AutoValue_AVWeatherCurrent(location, current);
    }

    public static TypeAdapter<AVWeatherCurrent> typeAdapter(Gson gson) {
        return new AutoValue_AVWeatherCurrent.GsonTypeAdapter(gson);
    }
    //нихрена не работает - приходит в респонсе класс Current с нулевыми значениями
    /*@AutoValue
    abstract static class Location {
        @SerializedName("name")
        public abstract String name();
        @SerializedName("region")
        public abstract String region();
        @SerializedName("country")
        public abstract String country();
        @SerializedName("lat")
        public abstract double lat();
        @SerializedName("lon")
        public abstract double lon();
        @SerializedName("tz_id")
        public abstract String tzId();
        @SerializedName("localtime_epoch")
        public abstract int localtimeEpoch();
        @SerializedName("localtime")
        public abstract String localtime();

        static Location create(
                String name,
                String region,
                String country,
                double lat,
                double lon,
                String tzId,
                int localtimeEpoch,
                String localtime
        ) {
            return new AutoValue_AVWeatherCurrent_Location(
                    name,
                    region,
                    country,
                    lat,
                    lon,
                    tzId,
                    localtimeEpoch,
                    localtime
            );
        }

        public static TypeAdapter<Location> typeAdapter(Gson gson) {
            return new AutoValue_AVWeatherCurrent_Location.GsonTypeAdapter(gson);
        }
    }

    @AutoValue
    abstract static class Current {
        @SerializedName("temp_c")
        public abstract double tempC();

        @SerializedName("wind_kph")
        public abstract double windKph();

        @SerializedName("wind_degree")
        public abstract int windDegree();

        @SerializedName("wind_dir")
        @Nullable
        public abstract String windDir();

        @SerializedName("precip_mm")
        public abstract double precipMm();

        @SerializedName("humidity")
        public abstract int humidity();

        @SerializedName("cloud")
        public abstract int cloud();

        static Current create(
                double tempC,
                double windKph,
                int windDegree,
                String windDir,
                double precipMm,
                int humidity,
                int cloud
        ) {
            return new AutoValue_AVWeatherCurrent_Current(
                    tempC,
                    windKph,
                    windDegree,
                    windDir,
                    precipMm,
                    humidity,
                    cloud
            );
        }

        public static TypeAdapter<Current> typeAdapter(Gson gson) {
            return new AutoValue_AVWeatherCurrent_Current.GsonTypeAdapter(gson);
        }
    }*/
}
