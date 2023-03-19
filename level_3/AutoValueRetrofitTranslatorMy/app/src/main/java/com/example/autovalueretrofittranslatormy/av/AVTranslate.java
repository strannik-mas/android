package com.example.autovalueretrofittranslatormy.av;

import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class AVTranslate{
    @SerializedName("code")
    public abstract int code();
    @SerializedName("lang")
    public abstract String lang();
    @SerializedName("text")
    @Nullable
    public abstract List<String> text();
    @SerializedName("message")
    @Nullable
    public abstract String message();

    public static AVTranslate create(int code, String lang, List<String> text, String message) {
        return new AutoValue_AVTranslate(code, lang, text, message);
    }

    public static TypeAdapter<AVTranslate> typeAdapter(Gson gson) {
        return new AutoValue_AVTranslate.GsonTypeAdapter(gson);
    }
}
