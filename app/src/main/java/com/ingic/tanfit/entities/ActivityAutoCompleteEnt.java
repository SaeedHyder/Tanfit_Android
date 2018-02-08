package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 12/14/2017.
 */

public class ActivityAutoCompleteEnt {


    String activityImage;
    String activityName;
    int  activityImageResourceId;

    public ActivityAutoCompleteEnt(String activityImage,String activityName, int activityImageResourceId) {
        this.activityName = activityName;
        this.activityImage = activityImage;
        this.activityImageResourceId = activityImageResourceId;
    }

    public int getActivityImageResourceId() {
        return activityImageResourceId;
    }

    public void setActivityImageResourceId(int activityImageResourceId) {
        this.activityImageResourceId = activityImageResourceId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityImage() {
        return activityImage;
    }

    public void setActivityImage(String activityImage) {
        this.activityImage = activityImage;
    }
}
