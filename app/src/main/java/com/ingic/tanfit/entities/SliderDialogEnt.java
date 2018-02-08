package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 12/14/2017.
 */

public class SliderDialogEnt {

    private String featureRes;
    private int intfeatureRes;


    public SliderDialogEnt(String featureRes, int intfeatureRes) {
        this.featureRes = featureRes;
        this.intfeatureRes = intfeatureRes;
    }

    public int getIntfeatureRes() {
        return intfeatureRes;
    }

    public void setIntfeatureRes(int intfeatureRes) {
        this.intfeatureRes = intfeatureRes;
    }

    public String getFeatureRes() {
        return featureRes;
    }

    public void setFeatureRes(String featureRes) {
        this.featureRes = featureRes;
    }
}
