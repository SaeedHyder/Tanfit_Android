package com.ingic.tanfit.entities;

/**
 * Created by saeedhyder on 1/6/2018.
 */

public class IsFavoriteEnt {

    boolean IsBooked;
    boolean IsFavorite;

    public boolean isFavorite() {
        return IsFavorite;
    }

    public void setFavorite(boolean favorite) {
        IsFavorite = favorite;
    }

    public boolean isBooked() {
        return IsBooked;
    }

    public void setBooked(boolean booked) {
        IsBooked = booked;
    }
}
