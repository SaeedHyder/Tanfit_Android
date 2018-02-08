package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 1/8/2018.
 */

public class FitnessClassFeature {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("FeatureId")
    @Expose
    private Integer featureId;
    @SerializedName("FeatureName")
    @Expose
    private String featureName;
    @SerializedName("Icon")
    @Expose
    private String icon;
    @SerializedName("FitnessClassId")
    @Expose
    private Integer fitnessClassId;
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

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getFitnessClassId() {
        return fitnessClassId;
    }

    public void setFitnessClassId(Integer fitnessClassId) {
        this.fitnessClassId = fitnessClassId;
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
