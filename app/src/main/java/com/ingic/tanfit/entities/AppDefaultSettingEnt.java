package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 1/8/2018.
 */

public class AppDefaultSettingEnt {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Notification")
    @Expose
    private Boolean notification;
    @SerializedName("LangId")
    @Expose
    private Integer langId;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("ModifiedOn")
    @Expose
    private String modifiedOn;
    @SerializedName("TermsAndConditionsEng")
    @Expose
    private String termsAndConditionsEng;
    @SerializedName("ContactUsEng")
    @Expose
    private String contactUsEng;
    @SerializedName("TermsAndConditionsPer")
    @Expose
    private String termsAndConditionsPer;
    @SerializedName("ContactUsPer")
    @Expose
    private String contactUsPer;
    @SerializedName("ContactUsAddressEng")
    @Expose
    private String contactUsAddressEng;
    @SerializedName("ContactUsAddressPer")
    @Expose
    private String contactUsAddressPer;
    @SerializedName("ContactUsUrl")
    @Expose
    private String contactUsUrl;
    @SerializedName("AboutUsEng")
    @Expose
    private String aboutUsEng;
    @SerializedName("AboutUsPer")
    @Expose
    private String aboutUsPer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    public Integer getLangId() {
        return langId;
    }

    public void setLangId(Integer langId) {
        this.langId = langId;
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

    public String getTermsAndConditionsEng() {
        return termsAndConditionsEng;
    }

    public void setTermsAndConditionsEng(String termsAndConditionsEng) {
        this.termsAndConditionsEng = termsAndConditionsEng;
    }

    public String getContactUsEng() {
        return contactUsEng;
    }

    public void setContactUsEng(String contactUsEng) {
        this.contactUsEng = contactUsEng;
    }

    public String getTermsAndConditionsPer() {
        return termsAndConditionsPer;
    }

    public void setTermsAndConditionsPer(String termsAndConditionsPer) {
        this.termsAndConditionsPer = termsAndConditionsPer;
    }

    public String getContactUsPer() {
        return contactUsPer;
    }

    public void setContactUsPer(String contactUsPer) {
        this.contactUsPer = contactUsPer;
    }

    public String getContactUsAddressEng() {
        return contactUsAddressEng;
    }

    public void setContactUsAddressEng(String contactUsAddressEng) {
        this.contactUsAddressEng = contactUsAddressEng;
    }

    public String getContactUsAddressPer() {
        return contactUsAddressPer;
    }

    public void setContactUsAddressPer(String contactUsAddressPer) {
        this.contactUsAddressPer = contactUsAddressPer;
    }

    public String getContactUsUrl() {
        return contactUsUrl;
    }

    public void setContactUsUrl(String contactUsUrl) {
        this.contactUsUrl = contactUsUrl;
    }

    public String getAboutUsEng() {
        return aboutUsEng;
    }

    public void setAboutUsEng(String aboutUsEng) {
        this.aboutUsEng = aboutUsEng;
    }

    public String getAboutUsPer() {
        return aboutUsPer;
    }

    public void setAboutUsPer(String aboutUsPer) {
        this.aboutUsPer = aboutUsPer;
    }

}
