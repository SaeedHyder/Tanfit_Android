package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 6/9/2018.
 */

public class BookingHistoryEnt {


    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ClassNameEng")
    @Expose
    private String classNameEng;
    @SerializedName("ClassNamePer")
    @Expose
    private String classNamePer;
    @SerializedName("BookingDateTime")
    @Expose
    private String bookingDateTime;
    @SerializedName("ModifiedOn")
    @Expose
    private String modifiedOn;
    @SerializedName("StudioNameEng")
    @Expose
    private String studioNameEng;
    @SerializedName("StudioNamePer")
    @Expose
    private String studioNamePer;
    @SerializedName("FitnessClassStatusId")
    @Expose
    private Integer fitnessClassStatusId;
    @SerializedName("AddressEng")
    @Expose
    private String addressEng;
    @SerializedName("AddressPer")
    @Expose
    private String addressPer;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
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

    public Integer getFitnessClassStatusId() {
        return fitnessClassStatusId;
    }

    public void setFitnessClassStatusId(Integer fitnessClassStatusId) {
        this.fitnessClassStatusId = fitnessClassStatusId;
    }
}
