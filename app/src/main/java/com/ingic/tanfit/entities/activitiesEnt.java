package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 6/8/2018.
 */

public class activitiesEnt {

    String text;
    String image;

    public activitiesEnt(String text, String image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
