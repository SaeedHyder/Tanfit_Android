package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 11/22/2017.
 */

public class SpecialFeatureEnt {

    String image;
    String feature;

    public SpecialFeatureEnt(String image, String feature) {
        this.image = image;
        this.feature = feature;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
