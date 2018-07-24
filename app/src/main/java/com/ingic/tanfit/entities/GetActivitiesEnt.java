package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 12/22/2017.
 */

public class GetActivitiesEnt {


    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Name_En")
    @Expose
    private String nameEn;
    @SerializedName("Name_Pr")
    @Expose
    private String namePr;
    @SerializedName("MaleIcon")
    @Expose
    private String maleIcon;
    @SerializedName("FemaleIcon")
    @Expose
    private String femaleIcon;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNamePr() {
        return namePr;
    }

    public void setNamePr(String namePr) {
        this.namePr = namePr;
    }

    public String getMaleIcon() {
        return maleIcon;
    }

    public void setMaleIcon(String maleIcon) {
        this.maleIcon = maleIcon;
    }

    public String getFemaleIcon() {
        return femaleIcon;
    }

    public void setFemaleIcon(String femaleIcon) {
        this.femaleIcon = femaleIcon;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
