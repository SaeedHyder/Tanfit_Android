package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saeedhyder on 12/22/2017.
 */

public class FitnessClass {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("StudioId")
    @Expose
    private Integer studioId;
    @SerializedName("StudioNameEng")
    @Expose
    private String studioNameEng;
    @SerializedName("StudioNamePer")
    @Expose
    private String studioNamePer;
    @SerializedName("StudioAddressEng")
    @Expose
    private String studioAddressEng;
    @SerializedName("StudioAddressPer")
    @Expose
    private String studioAddressPer;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("ClassDurationMin")
    @Expose
    private Integer classDurationMin;
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("GenderId")
    @Expose
    private Integer genderId;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("FitnessClassActivityId")
    @Expose
    private Integer fitnessClassActivityId;
    @SerializedName("FitnessClassActivity")
    @Expose
    private String fitnessClassActivity;
    @SerializedName("FitnessClassActivityIcon")
    @Expose
    private String fitnessClassActivityIcon;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("ModifiedOn")
    @Expose
    private Object modifiedOn;
    @SerializedName("ClassNameEng")
    @Expose
    private String classNameEng;
    @SerializedName("ClassNamePer")
    @Expose
    private String classNamePer;
    @SerializedName("ClassDescriptionEng")
    @Expose
    private String classDescriptionEng;
    @SerializedName("ClassDescriptionPer")
    @Expose
    private String classDescriptionPer;
    @SerializedName("ClassCancellationDurationHrs")
    @Expose
    private Integer classCancellationDurationHrs;
    @SerializedName("FitnessClassFeatures")
    @Expose
    private ArrayList<FitnessClassFeature> fitnessClassFeatures = new ArrayList<>();
    @SerializedName("FitnessClassSelectedDays")
    @Expose
    private ArrayList<FitnessClassSelectedDay> fitnessClassSelectedDays = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudioId() {
        return studioId;
    }

    public void setStudioId(Integer studioId) {
        this.studioId = studioId;
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

    public String getStudioAddressEng() {
        return studioAddressEng;
    }

    public void setStudioAddressEng(String studioAddressEng) {
        this.studioAddressEng = studioAddressEng;
    }

    public String getStudioAddressPer() {
        return studioAddressPer;
    }

    public void setStudioAddressPer(String studioAddressPer) {
        this.studioAddressPer = studioAddressPer;
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

    public Integer getClassDurationMin() {
        return classDurationMin;
    }

    public void setClassDurationMin(Integer classDurationMin) {
        this.classDurationMin = classDurationMin;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getFitnessClassActivityId() {
        return fitnessClassActivityId;
    }

    public void setFitnessClassActivityId(Integer fitnessClassActivityId) {
        this.fitnessClassActivityId = fitnessClassActivityId;
    }

    public String getFitnessClassActivity() {
        return fitnessClassActivity;
    }

    public void setFitnessClassActivity(String fitnessClassActivity) {
        this.fitnessClassActivity = fitnessClassActivity;
    }

    public String getFitnessClassActivityIcon() {
        return fitnessClassActivityIcon;
    }

    public void setFitnessClassActivityIcon(String fitnessClassActivityIcon) {
        this.fitnessClassActivityIcon = fitnessClassActivityIcon;
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

    public String getClassNameEng() {
        return classNameEng;
    }

    public void setClassNameEng(String classNameEng) {
        this.classNameEng = classNameEng;
    }

    public String getClassNamePer() {
        return classNamePer;
    }

    public void setClassNamePer(String classNamePer) {
        this.classNamePer = classNamePer;
    }

    public String getClassDescriptionEng() {
        return classDescriptionEng;
    }

    public void setClassDescriptionEng(String classDescriptionEng) {
        this.classDescriptionEng = classDescriptionEng;
    }

    public String getClassDescriptionPer() {
        return classDescriptionPer;
    }

    public void setClassDescriptionPer(String classDescriptionPer) {
        this.classDescriptionPer = classDescriptionPer;
    }

    public Integer getClassCancellationDurationHrs() {
        return classCancellationDurationHrs;
    }

    public void setClassCancellationDurationHrs(Integer classCancellationDurationHrs) {
        this.classCancellationDurationHrs = classCancellationDurationHrs;
    }

    public ArrayList<FitnessClassFeature> getFitnessClassFeatures() {
        return fitnessClassFeatures;
    }

    public void setFitnessClassFeatures(ArrayList<FitnessClassFeature> fitnessClassFeatures) {
        this.fitnessClassFeatures = fitnessClassFeatures;
    }

    public ArrayList<FitnessClassSelectedDay> getFitnessClassSelectedDays() {
        return fitnessClassSelectedDays;
    }

    public void setFitnessClassSelectedDays(ArrayList<FitnessClassSelectedDay> fitnessClassSelectedDays) {
        this.fitnessClassSelectedDays = fitnessClassSelectedDays;
    }

}
