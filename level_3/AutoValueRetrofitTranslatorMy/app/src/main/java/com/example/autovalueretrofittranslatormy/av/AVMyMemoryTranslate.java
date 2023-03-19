package com.example.autovalueretrofittranslatormy.av;

import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class AVMyMemoryTranslate {

    @AutoValue
    abstract static class ResponseData {
        @SerializedName("translatedText")
        public abstract String translatedText();
        @SerializedName("match")
        public abstract int match();

        static ResponseData create(String translatedText, int match) {
            return new AutoValue_AVMyMemoryTranslate_ResponseData(translatedText, match);
        }

        public static TypeAdapter<AVMyMemoryTranslate.ResponseData> typeAdapter(Gson gson) {
            return new AutoValue_AVMyMemoryTranslate_ResponseData.GsonTypeAdapter(gson);
        }
    }
    /*public static AVMyMemoryTranslate create(int code, String lang, List<String> text, String message) {
        return new AutoValue_AVTranslate(code, lang, text, message);
    }

    public static TypeAdapter<AVMyMemoryTranslate> typeAdapter(Gson gson) {
        return new AutoValue_AVMyMemoryTranslate.GsonTypeAdapter(gson);
    }*/
}
