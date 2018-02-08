package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 1/8/2018.
 */

public class BookClassEnt {

    @SerializedName("FitnessClass")
    @Expose
    private FitnessClass fitnessClass;
    @SerializedName("SeletedDay")
    @Expose
    private SeletedDay seletedDay;
    @SerializedName("SeletedTime")
    @Expose
    private SeletedTime seletedTime;

    public FitnessClass getFitnessClass() {
        return fitnessClass;
    }

    public void setFitnessClass(FitnessClass fitnessClass) {
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
