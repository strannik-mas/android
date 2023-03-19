package com.example.weatherautovalue;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class AVLocation {
    @SerializedName("title")
    public abstract String title();
    @SerializedName("location_type")
    public abstract String location_type();
    @SerializedName("woeid")
    public abstract int woeid();
    @SerializedName("latt_long")
    public abstract String latt_long();

    public static AVLocation create(
            String title,
            String location_type,
            int woeid,
            String latt_long
    ) {
        return new AutoValue_AVLocation(title, location_type, woeid, latt_long);
    }

    public static TypeAdapter<AVLocation> typeAdapter(Gson gson) {
        return new AutoValue_AVLocation.GsonTypeAdapter(gson);
    }
}
