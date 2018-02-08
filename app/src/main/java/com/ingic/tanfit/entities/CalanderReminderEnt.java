package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 1/6/2018.
 */

public class CalanderReminderEnt {

    String title;
    String info;
    String address;
    String startTIme;

    public CalanderReminderEnt(String title, String info, String address, String startTIme) {
        this.title = title;
        this.info = info;
        this.address = address;
        this.startTIme = startTIme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartTIme() {
        return startTIme;
    }

    public void setStartTIme(String startTIme) {
        this.startTIme = startTIme;
    }
}
