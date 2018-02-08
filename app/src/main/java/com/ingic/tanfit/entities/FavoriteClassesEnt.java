package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 1/8/2018.
 */

public class FavoriteClassesEnt {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("FitnessClassId")
    @Expose
    private Integer fitnessClassId;
    @SerializedName("StudioId")
    @Expose
    private Integer StudioId;
    @SerializedName("FitnessClass")
    @Expose
    private FitnessClassess fitnessClass;
    @SerializedName("Studio")
    @Expose
    private Studio Studio;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFitnessClassId() {
        return fitnessClassId;
    }

    public void setFitnessClassId(Integer fitnessClassId) {
        this.fitnessClassId = fitnessClassId;
    }

    public FitnessClassess getFitnessClass() {
        return fitnessClass;
    }

    public void setFitnessClass(FitnessClassess fitnessClass) {
        this.fitnessClass = fitnessClass;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getStudioId() {
        return StudioId;
    }

    public void setStudioId(Integer studioId) {
        StudioId = studioId;
    }

    public com.ingic.tanfit.entities.Studio getStudio() {
        return Studio;
    }

    public void setStudio(com.ingic.tanfit.entities.Studio studio) {
        Studio = studio;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
