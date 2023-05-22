package com.example.weatherautovalue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Condition {
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("icon")
    @Expose
    public String icon;
    @SerializedName("code")
    @Expose
    public int code;

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

    public int getCode() {
        return code;
    }
}
