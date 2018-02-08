package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 1/1/2018.
 */

public class SlotsEnt {

    Integer id;
    Integer DayId;
    Integer FitnessClassId;
    String timeIn;
    String timeOut;

    public SlotsEnt(Integer dayId, Integer id, Integer FitnessClassId, String timeIn, String timeOut) {
        this.id = id;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.DayId=dayId;
        this.FitnessClassId=FitnessClassId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDayId() {
        return DayId;
    }

    public void setDayId(Integer dayId) {
        DayId = dayId;
    }

    public Integer getFitnessClassId() {
        return FitnessClassId;
    }

    public void setFitnessClassId(Integer fitnessClassId) {
        FitnessClassId = fitnessClassId;
    }
}
