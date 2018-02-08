package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 12/26/2017.
 */

public class FitnessClassTime {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("FitnessClassSelectedDayId")
    @Expose
    private Integer fitnessClassSelectedDayId;
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
