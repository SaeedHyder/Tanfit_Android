package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 12/22/2017.
 */

public class GetSubscriptionEnt {


    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("PriceInIranianRiyal")
    @Expose
    private Integer priceInIranianRiyal;
    @SerializedName("NoOfClassesADay")
    @Expose
    private Integer noOfClassesADay;
    @SerializedName("NoOfSubscriptionDays")
    @Expose
    private Integer noOfSubscriptionDays;
    @SerializedName("SubscriptionTypeId")
    @Expose
    private Integer subscriptionTypeId;
    @SerializedName("SubscriptionType")
    @Expose
    private String subscriptionType;
    @SerializedName("TotalNoOfFitnessClasses")
    @Expose
    private String totalNoOfFitnessClasses;
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
    @SerializedName("TotalRecords")
    @Expose
    private Integer totalRecords;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPriceInIranianRiyal() {
        return priceInIranianRiyal;
    }

    public void setPriceInIranianRiyal(Integer priceInIranianRiyal) {
        this.priceInIranianRiyal = priceInIranianRiyal;
    }

    public Integer getNoOfClassesADay() {
        return noOfClassesADay;
    }

    public void setNoOfClassesADay(Integer noOfClassesADay) {
        this.noOfClassesADay = noOfClassesADay;
    }

    public Integer getNoOfSubscriptionDays() {
        return noOfSubscriptionDays;
    }

    public void setNoOfSubscriptionDays(Integer noOfSubscriptionDays) {
        this.noOfSubscriptionDays = noOfSubscriptionDays;
    }

    public Integer getSubscriptionTypeId() {
        return subscriptionTypeId;
    }

    public void setSubscriptionTypeId(Integer subscriptionTypeId) {
        this.subscriptionTypeId = subscriptionTypeId;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getTotalNoOfFitnessClasses() {
        return totalNoOfFitnessClasses;
    }

    public void setTotalNoOfFitnessClasses(String totalNoOfFitnessClasses) {
        this.totalNoOfFitnessClasses = totalNoOfFitnessClasses;
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

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

}
