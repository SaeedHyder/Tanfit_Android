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
    @SerializedName("Title_En")
    @Expose
    private String titleEn;
    @SerializedName("Title_Pr")
    @Expose
    private String titlePr;
    @SerializedName("PriceInIranianRiyal")
    @Expose
    private Integer priceInIranianRiyal;
    @SerializedName("PriceInIranianRiyal_Pr")
    @Expose
    private String priceInIranianRiyalPr;
    @SerializedName("NoOfClassesADay")
    @Expose
    private Integer noOfClassesADay;
    @SerializedName("NoOfClassesADay_Pr")
    @Expose
    private String noOfClassesADayPr;
    @SerializedName("NoOfSubscriptionDays")
    @Expose
    private Integer noOfSubscriptionDays;
    @SerializedName("NoOfSubscriptionDays_Pr")
    @Expose
    private String noOfSubscriptionDaysPr;
    @SerializedName("SubscriptionTypeId")
    @Expose
    private Integer subscriptionTypeId;
    @SerializedName("SubscriptionId")
    @Expose
    private Integer SubscriptionId;

    @SerializedName("SubscriptionType")
    @Expose
    private String subscriptionType;
    @SerializedName("SubscriptionType_Pr")
    @Expose
    private String subscriptionTypePr;
    @SerializedName("TotalNoOfFitnessClasses")
    @Expose
    private Integer totalNoOfFitnessClasses;
    @SerializedName("TotalNoOfFitnessClasses_Pr")
    @Expose
    private String totalNoOfFitnessClassesPr;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("ModifiedOn")
    @Expose
    private String modifiedOn;

    @SerializedName("TotalRecords")
    @Expose
    private Integer totalRecords;

    public Integer getSubscriptionId() {
        return SubscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        SubscriptionId = subscriptionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitlePr() {
        return titlePr;
    }

    public void setTitlePr(String titlePr) {
        this.titlePr = titlePr;
    }

    public Integer getPriceInIranianRiyal() {
        return priceInIranianRiyal;
    }

    public void setPriceInIranianRiyal(Integer priceInIranianRiyal) {
        this.priceInIranianRiyal = priceInIranianRiyal;
    }

    public String getPriceInIranianRiyalPr() {
        return priceInIranianRiyalPr;
    }

    public void setPriceInIranianRiyalPr(String priceInIranianRiyalPr) {
        this.priceInIranianRiyalPr = priceInIranianRiyalPr;
    }

    public Integer getNoOfClassesADay() {
        return noOfClassesADay;
    }

    public void setNoOfClassesADay(Integer noOfClassesADay) {
        this.noOfClassesADay = noOfClassesADay;
    }

    public String getNoOfClassesADayPr() {
        return noOfClassesADayPr;
    }

    public void setNoOfClassesADayPr(String noOfClassesADayPr) {
        this.noOfClassesADayPr = noOfClassesADayPr;
    }

    public Integer getNoOfSubscriptionDays() {
        return noOfSubscriptionDays;
    }

    public void setNoOfSubscriptionDays(Integer noOfSubscriptionDays) {
        this.noOfSubscriptionDays = noOfSubscriptionDays;
    }

    public String getNoOfSubscriptionDaysPr() {
        return noOfSubscriptionDaysPr;
    }

    public void setNoOfSubscriptionDaysPr(String noOfSubscriptionDaysPr) {
        this.noOfSubscriptionDaysPr = noOfSubscriptionDaysPr;
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

    public String getSubscriptionTypePr() {
        return subscriptionTypePr;
    }

    public void setSubscriptionTypePr(String subscriptionTypePr) {
        this.subscriptionTypePr = subscriptionTypePr;
    }

    public Integer getTotalNoOfFitnessClasses() {
        return totalNoOfFitnessClasses;
    }

    public void setTotalNoOfFitnessClasses(Integer totalNoOfFitnessClasses) {
        this.totalNoOfFitnessClasses = totalNoOfFitnessClasses;
    }

    public String getTotalNoOfFitnessClassesPr() {
        return totalNoOfFitnessClassesPr;
    }

    public void setTotalNoOfFitnessClassesPr(String totalNoOfFitnessClassesPr) {
        this.totalNoOfFitnessClassesPr = totalNoOfFitnessClassesPr;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }
}
