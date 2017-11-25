package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 11/25/2017.
 */

public class CurrentBookingEnt {

    String date;
    String center;
    String address;

    public CurrentBookingEnt(String date, String center, String address) {
        this.date = date;
        this.center = center;
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
