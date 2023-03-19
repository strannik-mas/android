
package com.example.autovalueretrofittranslatlorstarter2023;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translate {

    @SerializedName("responseData")
    @Expose
    private ResponseData responseData;
    @SerializedName("quotaFinished")
    @Expose
    private Boolean quotaFinished;
    @SerializedName("mtLangSupported")
    @Expose
    private Object mtLangSupported;
    @SerializedName("responseDetails")
    @Expose
    private String responseDetails;
    @SerializedName("responseStatus")
    @Expose
    private Integer responseStatus;
    @SerializedName("responderId")
    @Expose
    private Object responderId;
    @SerializedName("exception_code")
    @Expose
    private Object exceptionCode;
    @SerializedName("matches")
    @Expose
    private List<Match> matches;

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public Boolean getQuotaFinished() {
        return quotaFinished;
    }

    public void setQuotaFinished(Boolean quotaFinished) {
        this.quotaFinished = quotaFinished;
    }

    public Object getMtLangSupported() {
        return mtLangSupported;
    }

    public void setMtLangSupported(Object mtLangSupported) {
        this.mtLangSupported = mtLangSupported;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Object getResponderId() {
        return responderId;
    }

    public void setResponderId(Object responderId) {
        this.responderId = responderId;
    }

    public Object getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(Object exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

}
