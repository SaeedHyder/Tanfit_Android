package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 11/23/2017.
 */

public class SearchRecyclerEnt {

    String image;
    String gymName;
    String gymAddress;

    public SearchRecyclerEnt(String image, String gymName, String gymAddress) {
        this.image = image;
        this.gymName = gymName;
        this.gymAddress = gymAddress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getGymAddress() {
        return gymAddress;
    }

    public void setGymAddress(String gymAddress) {
        this.gymAddress = gymAddress;
    }
}
