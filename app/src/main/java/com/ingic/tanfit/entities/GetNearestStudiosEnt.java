package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 12/22/2017.
 */

public class GetNearestStudiosEnt {
    @SerializedName("Studios")
    @Expose
    private ArrayList<Studio> studios = new ArrayList<>();
    @SerializedName("FitnessClassess")
    @Expose
    private ArrayList<FitnessClassess> fitnessClassess = new ArrayList<>();

    public ArrayList<Studio> getStudios() {
        return studios;
    }

    public void setStudios(ArrayList<Studio> studios) {
        this.studios = studios;
    }

    public ArrayList<FitnessClassess> getFitnessClassess() {
        return fitnessClassess;
    }

    public void setFitnessClassess(ArrayList<FitnessClassess> fitnessClassess) {
        this.fitnessClassess = fitnessClassess;
    }
}
