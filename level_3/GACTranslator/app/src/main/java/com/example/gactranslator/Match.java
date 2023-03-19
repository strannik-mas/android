package com.example.gactranslator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("segment")
    @Expose
    private String segment;
    @SerializedName("translation")
    @Expose
    private String translation;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("reference")
    @Expose
    private Object reference;
    @SerializedName("usage-count")
    @Expose
    private Integer usageCount;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("created-by")
    @Expose
    private String createdBy;
    @SerializedName("last-updated-by")
    @Expose
    private String lastUpdatedBy;
    @SerializedName("create-date")
    @Expose
    private String createDate;
    @SerializedName("last-update-date")
    @Expose
    private String lastUpdateDate;
    @SerializedName("match")
    @Expose
    private Float match;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Object getReference() {
        return reference;
    }

    public void setReference(Object reference) {
        this.reference = reference;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Float getMatch() {
        return match;
    }

    public void setMatch(Float match) {
        this.match = match;
    }
}
