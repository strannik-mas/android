package com.example.gactranslator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("translatedText")
    @Expose
    private String translatedText;
    @SerializedName("match")
    @Expose
    private Float match;

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public Float getMatch() {
        return match;
    }

    public void setMatch(Float match) {
        this.match = match;
    }
}
