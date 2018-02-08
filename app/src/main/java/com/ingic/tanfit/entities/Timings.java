package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 12/29/2017.
 */

public class Timings {

    String timeIn;
    String timeOut;

    public Timings(String timeIn, String timeOut) {
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }
}
