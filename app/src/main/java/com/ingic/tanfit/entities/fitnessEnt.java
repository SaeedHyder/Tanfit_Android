package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 11/22/2017.
 */

public class fitnessEnt {

    String image;
    String title;
    String description;
    String address;
    String time;
    String duration;

    public fitnessEnt(String image, String title, String description, String address, String time, String duration) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.address = address;
        this.time = time;
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
