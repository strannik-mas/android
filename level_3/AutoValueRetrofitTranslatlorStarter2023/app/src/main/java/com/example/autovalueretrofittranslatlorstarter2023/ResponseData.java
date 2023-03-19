
package com.example.autovalueretrofittranslatlorstarter2023;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

    @SerializedName("translatedText")
    @Expose
    private String translatedText;
    @SerializedName("match")
    @Expose
    private Integer match;

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public Integer getMatch() {
        return match;
    }

    public void setMatch(Integer match) {
        this.match = match;
    }

}
