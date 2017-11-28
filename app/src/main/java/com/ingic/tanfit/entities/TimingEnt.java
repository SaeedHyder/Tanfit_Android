package com.ingic.tanfit.entities;

/**
 * Created on 11/28/2017.
 */

public class TimingEnt {
    private String menTime;
    private String womenTime;

    public TimingEnt(String menTime, String womenTime) {
        this.menTime = menTime;
        this.womenTime = womenTime;
    }

    public String getMenTime() {
        return menTime;
    }

    public void setMenTime(String menTime) {
        this.menTime = menTime;
    }

    public String getWomenTime() {
        return womenTime;
    }

    public void setWomenTime(String womenTime) {
        this.womenTime = womenTime;
    }
}
