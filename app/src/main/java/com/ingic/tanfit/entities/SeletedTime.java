package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 1/8/2018.
 */

public class SeletedTime {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("FitnessClassSelectedDayId")
    @Expose
    private Integer fitnessClassSelectedDayId;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("TimeIn")
    @Expose
    private String timeIn;
    @SerializedName("TimeOut")
    @Expose
    private String timeOut;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFitnessClassSelectedDayId() {
        return fitnessClassSelectedDayId;
    }

    public void setFitnessClassSelectedDayId(Integer fitnessClassSelectedDayId) {
        this.fitnessClassSelectedDayId = fitnessClassSelectedDayId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }
}
