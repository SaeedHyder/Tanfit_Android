package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 12/26/2017.
 */

public class Studio {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("OpeningTime")
    @Expose
    private String openingTime;
    @SerializedName("ClosingTime")
    @Expose
    private String closingTime;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("StudioLogo")
    @Expose
    private String studioLogo;
    @SerializedName("Distance")
    @Expose
    private Integer distance;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("ModifiedOn")
    @Expose
    private Object modifiedOn;
    @SerializedName("StudioNameEng")
    @Expose
    private String studioNameEng;
    @SerializedName("StudioNamePer")
    @Expose
    private String studioNamePer;
    @SerializedName("StudioDescriptionEng")
    @Expose
    private String studioDescriptionEng;
    @SerializedName("StudioDescriptionPer")
    @Expose
    private String studioDescriptionPer;
    @SerializedName("AddressEng")
    @Expose
    private String addressEng;
    @SerializedName("AddressPer")
    @Expose
    private String addressPer;
    @SerializedName("StudioImages")
    @Expose
    private ArrayList<StudioImage> studioImages = new ArrayList<>();
    @SerializedName("StudioSelectedDays")
    @Expose
    private ArrayList<StudioSelectedDay> studioSelectedDays = new ArrayList<>();
    @SerializedName("StudioFeatures")
    @Expose
    private ArrayList<StudioFeature> studioFeatures = new ArrayList<>();
    @SerializedName("FitnessClasses")
    @Expose
    private ArrayList<FitnessClassess> fitnessClasses = new ArrayList<>();

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

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getStudioLogo() {
        return studioLogo;
    }

    public void setStudioLogo(String studioLogo) {
        this.studioLogo = studioLogo;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
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

    public Object getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Object modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getStudioNameEng() {
        return studioNameEng;
    }

    public void setStudioNameEng(String studioNameEng) {
        this.studioNameEng = studioNameEng;
    }

    public String getStudioNamePer() {
        return studioNamePer;
    }

    public void setStudioNamePer(String studioNamePer) {
        this.studioNamePer = studioNamePer;
    }

    public String getStudioDescriptionEng() {
        return studioDescriptionEng;
    }

    public void setStudioDescriptionEng(String studioDescriptionEng) {
        this.studioDescriptionEng = studioDescriptionEng;
    }

    public String getStudioDescriptionPer() {
        return studioDescriptionPer;
    }

    public void setStudioDescriptionPer(String studioDescriptionPer) {
        this.studioDescriptionPer = studioDescriptionPer;
    }

    public String getAddressEng() {
        return addressEng;
    }

    public void setAddressEng(String addressEng) {
        this.addressEng = addressEng;
    }

    public String getAddressPer() {
        return addressPer;
    }

    public void setAddressPer(String addressPer) {
        this.addressPer = addressPer;
    }

    public ArrayList<StudioImage> getStudioImages() {
        return studioImages;
    }

    public void setStudioImages(ArrayList studioImages) {
        this.studioImages = studioImages;
    }

    public ArrayList<StudioSelectedDay> getStudioSelectedDays() {
        return studioSelectedDays;
    }

    public void setStudioSelectedDays(ArrayList studioSelectedDays) {
        this.studioSelectedDays = studioSelectedDays;
    }

    public ArrayList<StudioFeature> getStudioFeatures() {
        return studioFeatures;
    }

    public void setStudioFeatures(ArrayList studioFeatures) {
        this.studioFeatures = studioFeatures;
    }

    public ArrayList<FitnessClassess> getFitnessClasses() {
        return fitnessClasses;
    }

    public void setFitnessClasses(ArrayList fitnessClasses) {
        this.fitnessClasses = fitnessClasses;
    }
}
