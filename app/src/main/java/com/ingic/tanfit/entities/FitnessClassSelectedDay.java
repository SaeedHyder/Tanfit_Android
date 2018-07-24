package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 12/22/2017.
 */

public class FitnessClassSelectedDay {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("FitnessClassId")
    @Expose
    private Integer fitnessClassId;
    @SerializedName("DayId")
    @Expose
    private Integer dayId;
    @SerializedName("DayName_En")
    @Expose
    private String dayNameEn;
    @SerializedName("DayName_Pr")
    @Expose
    private String dayNamePr;
    @SerializedName("GenderId")
    @Expose
    private Integer genderId;
    @SerializedName("FitnessClassTimes")
    @Expose
    private ArrayList<FitnessClassTime> fitnessClassTimes = new ArrayList<>();
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;

    public String getDayNameEn() {
        return dayNameEn;
    }

    public void setDayNameEn(String dayNameEn) {
        this.dayNameEn = dayNameEn;
    }

    public String getDayNamePr() {
        return dayNamePr;
    }

    public void setDayNamePr(String dayNamePr) {
        this.dayNamePr = dayNamePr;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

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


    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public ArrayList<FitnessClassTime> getFitnessClassTimes() {
        return fitnessClassTimes;
    }

    public void setFitnessClassTimes(ArrayList<FitnessClassTime> fitnessClassTimes) {
        this.fitnessClassTimes = fitnessClassTimes;
    }
}
