package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 12/22/2017.
 */

public class UserSubscription {


    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("SubscriptionType")
    @Expose
    private String SubscriptionType;
    @SerializedName("SubscriptionId")
    @Expose
    private Integer subscriptionId;
    @SerializedName("IsPaused")
    @Expose
    private Boolean isPaused;
    @SerializedName("PausedDays")
    @Expose
    private Integer pausedDays;
    @SerializedName("ExpirationDate")
    @Expose
    private String expirationDate;
    @SerializedName("TotalRemainingDays")
    @Expose
    private Integer totalRemainingDays;
    @SerializedName("SameStudioPerMonth")
    @Expose
    private Integer sameStudioPerMonth;
    @SerializedName("UserSubscriptionStatusId")
    @Expose
    private Integer userSubscriptionStatusId;
    @SerializedName("UserDateTimeOffSet")
    @Expose
    private String userDateTimeOffSet;
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

    public String getSubscriptionType() {
        return SubscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        SubscriptionType = subscriptionType;
    }

    public Boolean getPaused() {
        return isPaused;
    }

    public void setPaused(Boolean paused) {
        isPaused = paused;
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

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Boolean getIsPaused() {
        return isPaused;
    }

    public void setIsPaused(Boolean isPaused) {
        this.isPaused = isPaused;
    }

    public Integer getPausedDays() {
        return pausedDays;
    }

    public void setPausedDays(Integer pausedDays) {
        this.pausedDays = pausedDays;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getTotalRemainingDays() {
        return totalRemainingDays;
    }

    public void setTotalRemainingDays(Integer totalRemainingDays) {
        this.totalRemainingDays = totalRemainingDays;
    }

    public Integer getSameStudioPerMonth() {
        return sameStudioPerMonth;
    }

    public void setSameStudioPerMonth(Integer sameStudioPerMonth) {
        this.sameStudioPerMonth = sameStudioPerMonth;
    }

    public Integer getUserSubscriptionStatusId() {
        return userSubscriptionStatusId;
    }

    public void setUserSubscriptionStatusId(Integer userSubscriptionStatusId) {
        this.userSubscriptionStatusId = userSubscriptionStatusId;
    }

    public String getUserDateTimeOffSet() {
        return userDateTimeOffSet;
    }

    public void setUserDateTimeOffSet(String userDateTimeOffSet) {
        this.userDateTimeOffSet = userDateTimeOffSet;
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

}
