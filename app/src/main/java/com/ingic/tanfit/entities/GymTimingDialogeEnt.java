package com.ingic.tanfit.entities;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 12/29/2017.
 */

public class GymTimingDialogeEnt {

    String dayName;
    ArrayList<Timings> maleTiming=new ArrayList<>();
    ArrayList<Timings> femaleTiming=new ArrayList<>();

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public ArrayList<Timings> getMaleTiming() {
        return maleTiming;
    }

    public void setMaleTiming(ArrayList<Timings> maleTiming) {
        this.maleTiming = maleTiming;
    }

    public ArrayList<Timings> getFemaleTiming() {
        return femaleTiming;
    }

    public void setFemaleTiming(ArrayList<Timings> femaleTiming) {
        this.femaleTiming = femaleTiming;
    }
}
