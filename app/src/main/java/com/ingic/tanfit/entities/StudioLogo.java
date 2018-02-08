package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 1/6/2018.
 */

public class StudioLogo {

    @SerializedName("StudioImage")
    @Expose
    private String studioImage;

    public String getStudioImage() {
        return studioImage;
    }

    public void setStudioImage(String studioImage) {
        this.studioImage = studioImage;
    }
}
