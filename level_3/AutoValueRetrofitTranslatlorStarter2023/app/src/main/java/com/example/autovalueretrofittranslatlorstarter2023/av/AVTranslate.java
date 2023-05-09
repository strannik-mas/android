package com.example.autovalueretrofittranslatlorstarter2023.av;

import androidx.annotation.Nullable;

import com.example.autovalueretrofittranslatlorstarter2023.Match;
import com.example.autovalueretrofittranslatlorstarter2023.ResponseData;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class AVTranslate {
/*
    @AutoValue
    abstract static class ResponseData {

        @SerializedName("translatedText")
        public abstract String translatedText();

        @SerializedName("match")
        public abstract float match();

        static ResponseData create(String translatedText, float match) {
            return new AutoValue_AVTranslate_ResponseData(translatedText, match);
        }

        public static TypeAdapter<AVTranslate.ResponseData> typeAdapter(Gson gson) {
            return new AutoValue_AVTranslate_ResponseData.GsonTypeAdapter(gson);
        }
    }

 */
    @SerializedName("responseData")
    public abstract ResponseData responseData();
    @SerializedName("quotaFinished")
    public abstract Boolean quotaFinished();
    @SerializedName("mtLangSupported")
    @Nullable
    public abstract Object mtLangSupported();
    @SerializedName("responseDetails")
    public abstract String responseDetails();
    @SerializedName("responseStatus")
    public abstract Integer responseStatus();
    @SerializedName("responderId")
    @Nullable
    public abstract Integer responderId();
    @SerializedName("exception_code")
    @Nullable
    public abstract Integer exceptionCode();
    @SerializedName("matches")
    public abstract List<Match> matches();

    public static AVTranslate create(
            ResponseData responseData,
            Boolean quotaFinished,
            Object mtLangSupported,
            String responseDetails,
            Integer responseStatus,
            Integer responderId,
            Integer exceptionCode,
            List<Match> matches
    ) {
        return new AutoValue_AVTranslate(
                responseData,
                quotaFinished,
                mtLangSupported,
                responseDetails,
                responseStatus,
                responderId,
                exceptionCode,
                matches
        );
    }

    public static TypeAdapter<AVTranslate> typeAdapter(Gson gson) {
        return new AutoValue_AVTranslate.GsonTypeAdapter(gson);
    }
}
