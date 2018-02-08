package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 1/8/2018.
 */

public class SeletedDay {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("FitnessClassId")
    @Expose
    private Integer fitnessClassId;
    @SerializedName("DayId")
    @Expose
    private Integer dayId;
    @SerializedName("DayName")
    @Expose
    private Object dayName;
    @SerializedName("GenderId")
    @Expose
    private Integer genderId;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("FitnessClassTimes")
    @Expose
    private ArrayList<FitnessClassTime> fitnessClassTimes = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFitnessClassId() {
        return fitnessClassId;
    }

    public void setFitnessClassId(Integer fitnessClassId) {
        this.fitnessClassId = fitnessClassId;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    public Object getDayName() {
        return dayName;
    }

    public void setDayName(Object dayName) {
        this.dayName = dayName;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ArrayList<FitnessClassTime> getFitnessClassTimes() {
        return fitnessClassTimes;
    }

    public void setFitnessClassTimes(ArrayList<FitnessClassTime> fitnessClassTimes) {
        this.fitnessClassTimes = fitnessClassTimes;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
