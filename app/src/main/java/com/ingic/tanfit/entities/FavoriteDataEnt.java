package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 1/17/2018.
 */

public class FavoriteDataEnt {

    @SerializedName("FitnessClasses")
    @Expose
    private ArrayList<FitnessClassess> FitnessClasses = new ArrayList<>();
    @SerializedName("Studios")
    @Expose
    private ArrayList<Studio> Studios = new ArrayList<>();

    public ArrayList<FitnessClassess> getFitnessClasses() {
        return FitnessClasses;
    }

    public void setFitnessClasses(ArrayList<FitnessClassess> fitnessClasses) {
        FitnessClasses = fitnessClasses;
    }

    public ArrayList<Studio> getStudios() {
        return Studios;
    }

    public void setStudios(ArrayList<Studio> studios) {
        Studios = studios;
    }
}
