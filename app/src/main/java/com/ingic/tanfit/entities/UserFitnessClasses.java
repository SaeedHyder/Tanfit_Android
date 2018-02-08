package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 12/22/2017.
 */

public class UserFitnessClasses {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("UserSubscriptionId")
    @Expose
    private Integer userSubscriptionId;
    @SerializedName("FitnessClassId")
    @Expose
    private Integer fitnessClassId;
    @SerializedName("StudioId")
    @Expose
    private Integer StudioId;
    @SerializedName("FitnessClassStatusId")
    @Expose
    private Integer fitnessClassStatusId;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("ModifiedOn")
    @Expose
    private String modifiedOn;
    @SerializedName("IsServerError")
    @Expose
    private Boolean isServerError;
    @SerializedName("IsSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("Message")
    @Expose
    private Object message;
    @SerializedName("Result")
    @Expose
    private Object result;

    @SerializedName("FitnessClass")
    @Expose
    FitnessClassess fitnessClassess;

    public FitnessClassess getFitnessClassess() {
        return fitnessClassess;
    }

    public void setFitnessClassess(FitnessClassess fitnessClassess) {
        this.fitnessClassess = fitnessClassess;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUserSubscriptionId() {
        return userSubscriptionId;
    }

    public void setUserSubscriptionId(Integer userSubscriptionId) {
        this.userSubscriptionId = userSubscriptionId;
    }

    public Integer getFitnessClassId() {
        return fitnessClassId;
    }

    public void setFitnessClassId(Integer fitnessClassId) {
        this.fitnessClassId = fitnessClassId;
    }

    public Integer getFitnessClassStatusId() {
        return fitnessClassStatusId;
    }

    public void setFitnessClassStatusId(Integer fitnessClassStatusId) {
        this.fitnessClassStatusId = fitnessClassStatusId;
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

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Boolean getIsServerError() {
        return isServerError;
    }

    public void setIsServerError(Boolean isServerError) {
        this.isServerError = isServerError;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Integer getStudioId() {
        return StudioId;
    }

    public void setStudioId(Integer studioId) {
        StudioId = studioId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getServerError() {
        return isServerError;
    }

    public void setServerError(Boolean serverError) {
        isServerError = serverError;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
}
