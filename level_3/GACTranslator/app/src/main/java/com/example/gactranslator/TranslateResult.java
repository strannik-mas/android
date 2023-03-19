package com.example.gactranslator;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "result", primaryKeys = {"sentence", "lang"})
public class TranslateResult {

    @NonNull
    private String sentence;
    @NonNull
    private String lang;
    private String result;
    private String status;
    private String exception;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
