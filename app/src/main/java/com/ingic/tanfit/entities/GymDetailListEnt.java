package com.ingic.tanfit.entities;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 12/29/2017.
 */

public class GymDetailListEnt {

    String featureHeader;
    ArrayList<StudioFeature> featuresList;

    public GymDetailListEnt(String featureHeader, ArrayList<StudioFeature> featuresList) {
        this.featureHeader = featureHeader;
        this.featuresList = featuresList;
    }

    public String getFeatureHeader() {
        return featureHeader;
    }

    public void setFeatureHeader(String featureHeader) {
        this.featureHeader = featureHeader;
    }

    public ArrayList<StudioFeature> getFeaturesList() {
        return featuresList;
    }

    public void setFeaturesList(ArrayList<StudioFeature> featuresList) {
        this.featuresList = featuresList;
    }
}
