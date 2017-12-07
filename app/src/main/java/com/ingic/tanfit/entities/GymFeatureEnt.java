package com.ingic.tanfit.entities;

/**
 * Created on gym_image_11/27/2017.
 */

public class GymFeatureEnt {
    private int featureRes;
    private String featureTitle;

    public GymFeatureEnt(int featureRes, String featureTitle) {
        this.featureRes = featureRes;
        this.featureTitle = featureTitle;
    }

    public int getFeatureRes() {
        return featureRes;
    }

    public void setFeatureRes(int featureRes) {
        this.featureRes = featureRes;
    }

    public String getFeatureTitle() {
        return featureTitle;
    }

    public void setFeatureTitle(String featureTitle) {
        this.featureTitle = featureTitle;
    }
}
