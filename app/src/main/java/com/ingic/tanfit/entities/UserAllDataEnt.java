package com.ingic.tanfit.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 12/22/2017.
 */

public class UserAllDataEnt {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("EmailConfirmed")
    @Expose
    private Boolean emailConfirmed;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("PhoneNumberConfirmed")
    @Expose
    private Boolean phoneNumberConfirmed;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("UserThumbnailImage")
    @Expose
    private String userThumbnailImage;
    @SerializedName("GenderId")
    @Expose
    private Integer genderId;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("RegistrationTypeId")
    @Expose
    private Integer registrationTypeId;
    @SerializedName("RegistrationType")
    @Expose
    private String registrationType;
    @SerializedName("Height")
    @Expose
    private Integer height;
    @SerializedName("Weight")
    @Expose
    private Integer weight;
    @SerializedName("EmergencyContact")
    @Expose
    private String emergencyContact;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;

    /*@SerializedName("UserFitnessClasses")
    @Expose
    private ArrayList<UserFitnessClasses> userFitnessClasses = new ArrayList<>();*/
    @SerializedName("UserSubscription")
    @Expose
    private ArrayList<UserSubscription> userSubscription = new ArrayList<>();
   /* @SerializedName("UserFavouriteFitnessClasses")
    @Expose
    private ArrayList<FavoriteClassesEnt> userFavouriteFitnessClasses = new ArrayList<>();
    @SerializedName("UserFavouriteStudioModel")
    @Expose
    private ArrayList<FavoriteClassesEnt> userFavouriteStudioModel = new ArrayList<>();*/
    @SerializedName("UserLocationModel")
    @Expose
    private ArrayList<UserLocationModel> userLocationModel = new ArrayList<>();
    @SerializedName("UserAppSetting")
    @Expose
    private ArrayList<AppDefaultSettingEnt> UserAppSetting = new ArrayList<>();

    @SerializedName("StudioLogos")
    @Expose
    private ArrayList<StudioLogo> studioLogos = new ArrayList<>();

    public ArrayList<StudioLogo> getStudioLogos() {
        return studioLogos;
    }

    public void setStudioLogos(ArrayList<StudioLogo> studioLogos) {
        this.studioLogos = studioLogos;
    }


    public ArrayList<AppDefaultSettingEnt> getUserAppSetting() {
        return UserAppSetting;
    }

    public void setUserAppSetting(ArrayList<AppDefaultSettingEnt> userAppSetting) {
        UserAppSetting = userAppSetting;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(Boolean phoneNumberConfirmed) {
        this.phoneNumberConfirmed = phoneNumberConfirmed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserThumbnailImage() {
        return userThumbnailImage;
    }

    public void setUserThumbnailImage(String userThumbnailImage) {
        this.userThumbnailImage = userThumbnailImage;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getRegistrationTypeId() {
        return registrationTypeId;
    }

    public void setRegistrationTypeId(Integer registrationTypeId) {
        this.registrationTypeId = registrationTypeId;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }



    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

   /* public ArrayList<UserFitnessClasses> getUserFitnessClasses() {
        return userFitnessClasses;
    }

    public void setUserFitnessClasses(ArrayList<UserFitnessClasses> userFitnessClasses) {
        this.userFitnessClasses = userFitnessClasses;
    }*/

    public ArrayList<UserSubscription> getUserSubscription() {
        return userSubscription;
    }

    public void setUserSubscription(ArrayList<UserSubscription> userSubscription) {
        this.userSubscription = userSubscription;
    }

    public ArrayList<UserLocationModel> getUserLocationModel() {
        return userLocationModel;
    }

    public void setUserLocationModel(ArrayList<UserLocationModel> userLocationModel) {
        this.userLocationModel = userLocationModel;
    }

  /*  public ArrayList<FavoriteClassesEnt> getUserFavouriteFitnessClasses() {
        return userFavouriteFitnessClasses;
    }

    public void setUserFavouriteFitnessClasses(ArrayList<FavoriteClassesEnt> userFavouriteFitnessClasses) {
        this.userFavouriteFitnessClasses = userFavouriteFitnessClasses;
    }

    public ArrayList<FavoriteClassesEnt> getUserFavouriteStudioModel() {
        return userFavouriteStudioModel;
    }

    public void setUserFavouriteStudioModel(ArrayList<FavoriteClassesEnt> userFavouriteStudioModel) {
        this.userFavouriteStudioModel = userFavouriteStudioModel;
    }*/
}
