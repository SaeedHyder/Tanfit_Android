package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 1/8/2018.
 */

public class remindingClassEnt {

    @SerializedName("FitnessClass")
    @Expose
    private FitnessClassess fitnessClass;
    @SerializedName("SeletedDay")
    @Expose
    private SeletedDay seletedDay;
    @SerializedName("SeletedTime")
    @Expose
    private SeletedTime seletedTime;

    public FitnessClassess getFitnessClass() {
        return fitnessClass;
    }

    public void setFitnessClass(FitnessClassess fitnessClass) {
        this.fitnessClass = fitnessClass;
    }

    public SeletedDay getSeletedDay() {
        return seletedDay;
    }

    public void setSeletedDay(SeletedDay seletedDay) {
        this.seletedDay = seletedDay;
    }

    public SeletedTime getSeletedTime() {
        return seletedTime;
    }

    public void setSeletedTime(SeletedTime seletedTime) {
        this.seletedTime = seletedTime;
    }
}
