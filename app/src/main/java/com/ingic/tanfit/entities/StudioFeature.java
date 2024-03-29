package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 12/22/2017.
 */

public class StudioFeature {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("FeatureId")
    @Expose
    private Integer featureId;
    @SerializedName("FeatureName_En")
    @Expose
    private String featureNameEn;
    @SerializedName("FeatureName_Pr")
    @Expose
    private String featureNamePr;
    @SerializedName("Icon")
    @Expose
    private String icon;
    @SerializedName("StudioId")
    @Expose
    private Integer studioId;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public String getFeatureNameEn() {
        return featureNameEn;
    }

    public void setFeatureNameEn(String featureNameEn) {
        this.featureNameEn = featureNameEn;
    }

    public String getFeatureNamePr() {
        return featureNamePr;
    }

    public void setFeatureNamePr(String featureNamePr) {
        this.featureNamePr = featureNamePr;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getStudioId() {
        return studioId;
    }

    public void setStudioId(Integer studioId) {
        this.studioId = studioId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

}
