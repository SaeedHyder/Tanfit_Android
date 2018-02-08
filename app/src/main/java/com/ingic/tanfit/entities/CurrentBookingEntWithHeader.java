package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 1/6/2018.
 */

public class CurrentBookingEntWithHeader {

    String fitnessHeader;
    CurrentBookingEnt detail;

    public CurrentBookingEntWithHeader(String fitnessHeader, CurrentBookingEnt detail) {
        this.fitnessHeader = fitnessHeader;
        this.detail = detail;
    }

    public String getFitnessHeader() {
        return fitnessHeader;
    }

    public void setFitnessHeader(String fitnessHeader) {
        this.fitnessHeader = fitnessHeader;
    }

    public CurrentBookingEnt getDetail() {
        return detail;
    }

    public void setDetail(CurrentBookingEnt detail) {
        this.detail = detail;
    }
}
