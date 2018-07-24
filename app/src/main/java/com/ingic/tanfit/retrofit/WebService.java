package com.ingic.tanfit.retrofit;


import com.google.android.gms.common.api.Api;
import com.ingic.tanfit.entities.AppDefaultSettingEnt;
import com.ingic.tanfit.entities.BookClassEnt;
import com.ingic.tanfit.entities.BookingHistoryEnt;
import com.ingic.tanfit.entities.CancelBody;
import com.ingic.tanfit.entities.FavoriteDataEnt;
import com.ingic.tanfit.entities.FitnessClass;
import com.ingic.tanfit.entities.FitnessClassess;
import com.ingic.tanfit.entities.GetActivitiesEnt;
import com.ingic.tanfit.entities.GetNearestStudiosEnt;
import com.ingic.tanfit.entities.GetSubscriptionEnt;
import com.ingic.tanfit.entities.IsFavoriteEnt;
import com.ingic.tanfit.entities.IsVerifiedEnt;
import com.ingic.tanfit.entities.ResponseWrapper;
import com.ingic.tanfit.entities.Studio;
import com.ingic.tanfit.entities.SurveyQuestionsEnt;
import com.ingic.tanfit.entities.UpdateUserEnt;
import com.ingic.tanfit.entities.UserAllDataEnt;
import com.ingic.tanfit.entities.UserEnt;
import com.ingic.tanfit.entities.UserFitnessClasses;
import com.ingic.tanfit.entities.UserLocationModel;
import com.ingic.tanfit.entities.UserSubscription;
import com.ingic.tanfit.entities.remindingClassEnt;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {

/*
    @Multipart
    @POST("Api/Account/UserRegister")
    Call<ResponseWrapper> registerUser(@Part("Email") RequestBody Email,
                                       @Part("Password") RequestBody Password,
                                       @Part("ConfirmPassword") RequestBody ConfirmPassword,
                                       @Part("PhoneNumber") RequestBody PhoneNumber,
                                       @Part("FullName") RequestBody FullName,
                                       @Part MultipartBody.Part UserThumbnailImage,
                                       @Part("GenderId") RequestBody GenderId
                                       );
*/

    @FormUrlEncoded
    @POST("Api/Account/UserRegister")
    Call<ResponseWrapper<UserEnt>> registerUser(@Field("Email") String Email,
                                                @Field("Password") String Password,
                                                @Field("ConfirmPassword") String ConfirmPassword,
                                                @Field("PhoneNumber") String PhoneNumber,
                                                @Field("FullName") String FullName,
                                                @Field("UserThumbnailImage") String UserThumbnailImage,
                                                @Field("GenderId") String GenderId
    );

    @FormUrlEncoded
    @POST("api/Account/Login")
    Call<ResponseWrapper<UserEnt>> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("api/user/ConfirmedVerificationCode")
    Call<ResponseWrapper> verifyCode(
            @Field("UserId") String UserId,
            @Field("Code") String Code);

    @FormUrlEncoded
    @POST("api/user/ReGeneratePhoneNumberCode")
    Call<ResponseWrapper> resendCode(
            @Field("UserId") String UserId);

    @FormUrlEncoded
    @POST("api/Account/ForgotPasswordCodeGenerate")
    Call<ResponseWrapper> forgotPassword(
            @Field("Email") String Email);

    @FormUrlEncoded
    @POST("api/Account/ForgotPassword")
    Call<ResponseWrapper> forgotPasswordVerification(
            @Field("Email") String Email,
            @Field("Code") String Code,
            @Field("NewPassword") String NewPassword,
            @Field("ConfirmPassword") String ConfirmPassword);

    @FormUrlEncoded
    @POST("api/Account/ChangePassword")
    Call<ResponseWrapper> changePassword(
            @Field("OldPassword") String OldPassword,
            @Field("NewPassword") String NewPassword,
            @Field("ConfirmPassword") String ConfirmPassword);


    @GET("api/User/GetUser/{UserId}")
    Call<ResponseWrapper<UserAllDataEnt>> userAllData(
            @Path("UserId") String UserId);


    @GET("api/User/GetUserFavoriteLite")
    Call<ResponseWrapper<FavoriteDataEnt>> getFavoriteData(
            @Query("userId") String userId);



    @FormUrlEncoded
    @POST("api/User/AddUserLocation")
    Call<ResponseWrapper<UserLocationModel>> addUserLocation(
            @Field("UserId") String UserId,
            @Field("Latitude") String Latitute,
            @Field("Longitude") String Longitute,
            @Field("LocationName") String LocationName);

    @GET("api/User/GetActivities")
    Call<ResponseWrapper<ArrayList<GetActivitiesEnt>>> getActivities();

    @FormUrlEncoded
    @POST("api/User/SetIsPaused")
    Call<ResponseWrapper> setPauseSubscription(
            @Field("UserId") String UserId,
            @Field("PausedDays") String PausedDays,
            @Field("UserDateTimeOffSet") String UserDateTimeOffSet);

    @GET("api/User/GetSubscriptions")
    Call<ResponseWrapper<ArrayList<GetSubscriptionEnt>>> getSubscriptionsPlan();

    @FormUrlEncoded
    @POST("api/User/AddUserSubscription")
    Call<ResponseWrapper> addUserSubscription(
            @Field("UserId") String UserId,
            @Field("SubscriptionId") String SubscriptionId,
            @Field("UserDateTimeOffSet") String UserDateTimeOffSet);

    @FormUrlEncoded
    @POST("api/User/GetUserNearestStudios")
    Call<ResponseWrapper<GetNearestStudiosEnt>> getNearestStudios(
            @Field("UserId") String UserId,
            @Field("Latitude") String Latitude,
            @Field("Longitude") String Longitude,
            @Field("Distance") int Distance);

    @FormUrlEncoded
    @POST("api/User/GetUserNearestStudiosLite")
    Call<ResponseWrapper<GetNearestStudiosEnt>> getNearestStudiosLite(
            @Field("UserId") String UserId,
            @Field("Latitude") String Latitude,
            @Field("Longitude") String Longitude,
            @Field("TimeZone") String TimeZone);


    @FormUrlEncoded
    @POST("api/User/SearchNearestStudiosLite")
    Call<ResponseWrapper<ArrayList<Studio>>> searchNearestStudios(
            @Field("UserId") String UserId,
            @Field("Latitude") String Latitude,
            @Field("Longitude") String Longitude,
            @Field("FitnessClassActivityId") String FitnessClassActivityId,
            @Field("GenderId") String GenderId,
            @Field("TimeIn") String TimeIn,
            @Field("TimeOut") String TimeOut,
            @Field("Distance") int Distance);

    @FormUrlEncoded
    @POST("api/User/AddUserFitnessClass")
    Call<ResponseWrapper<BookClassEnt>> bookUserClass(
            @Field("UserId") String UserId,
            @Field("FitnessClassId") Integer FitnessClassId,
            @Field("SelectedFitnessClassDayId") Integer SelectedFitnessClassDayId,
            @Field("SelectedFitnessClassTimeId") Integer SelectedFitnessClassTimeId);

    @FormUrlEncoded
    @POST("api/User/CancelFitnessClass")
    Call<ResponseWrapper> cancelUserClass(
            @Field("UserId") String UserId,
            @Field("FitnessClassId") String FitnessClassId);

    @GET("api/User/IsFitnessClassBooked")
    Call<ResponseWrapper<IsFavoriteEnt>> isClassBooked(
            @Query("UserId") String UserId,
            @Query("FitnessClassId") Integer fitnessID,
            @Query("fitnessClassDayId") String fitnessClassDayId );


    @GET("api/User/GetFitnessClass")
    Call<ResponseWrapper<IsFavoriteEnt>> isBooked(
            @Query("UserId") String UserId,
            @Query("FitnessClassId") Integer fitnessID,
            @Query("fitnessClassDayId") String fitnessClassDayId );

    @FormUrlEncoded
    @POST("api/User/IsFavoriteFitnessClass")
    Call<ResponseWrapper<IsFavoriteEnt>> isFavoriteClass(
            @Field("UserId") String UserId,
            @Field("FitnessClassId") Integer fitnessID);


    @FormUrlEncoded
    @POST("api/User/IsFavoriteStudio")
    Call<ResponseWrapper<IsFavoriteEnt>> isFavoriteStudio(
            @Field("UserId") String UserId,
            @Field("StudioId") Integer StudioId);

    @FormUrlEncoded
    @POST("api/User/AddFavoriteStudio")
    Call<ResponseWrapper> addFavoriteStudio(
            @Field("UserId") String UserId,
            @Field("StudioId") Integer StudioId,
            @Field("IsDeleted") boolean IsDeleted);

    @FormUrlEncoded
    @POST("api/User/AddFavoriteFitnessClass")
    Call<ResponseWrapper> addFavoriteClass(
            @Field("UserId") String UserId,
            @Field("FitnessClassId") Integer FitnessClassId,
            @Field("IsDeleted") boolean IsDeleted);

    @FormUrlEncoded
    @POST("api/User/CancelUserSubscription")
    Call<ResponseWrapper> cancelUserSubscription(
            @Field("UserId") String UserId,
            @Field("UserSubscriptionId") String UserSubscriptionId);

    @GET("api/User/GetFitnessClass")
    Call<ResponseWrapper<FitnessClassess>> getFitnessClass(
            @Query("userId ") String userId ,
            @Query("fitnessClassId ") String fitnessClassId ,
            @Query("fitnessClassDayId ") String fitnessClassDayId );

    @GET("api/User/GetStudio")
    Call<ResponseWrapper<Studio>> getStudio(
            @Query("Id") String Id);


    @GET("api/User/GetDefaultAppSetting")
    Call<ResponseWrapper<AppDefaultSettingEnt>> getDefaultSetting();


    @GET("api/User/RemindingFitnessClass")
    Call<ResponseWrapper<remindingClassEnt>> RemindingFitnessClass(
            @Query("userId") String userId );

    @FormUrlEncoded
    @POST("api/User/UpdateUser")
    Call<ResponseWrapper<UserAllDataEnt>> deleteAccount(
            @Field("Id") String Id,
            @Field("Email") String Email,
            @Field("FullName") String FullName,
            @Field("UserThumbnailImage") String UserThumbnailImage,
            @Field("GenderId") String GenderId,
            @Field("IsDeleted") boolean IsDeleted
            );


    @GET("api/Account/Logout")
    Call<ResponseWrapper<remindingClassEnt>> logout(
            @Query("UserId") String UserId );

    @FormUrlEncoded
    @POST("api/Account/ForgotPasswordCodeVerification")
    Call<ResponseWrapper> forgotCodeVerification(
            @Field("Email") String Email,
            @Field("Code") String Code);

    @FormUrlEncoded
    @POST("api/Account/UpdateForgotPassword")
    Call<ResponseWrapper> updateForgotPassword(
            @Field("Email") String Email,
            @Field("Code") String Code,
            @Field("NewPassword") String NewPassword,
            @Field("ConfirmPassword") String ConfirmPassword);

    @FormUrlEncoded
    @POST("api/User/AddUpdateUserAppSetting")
    Call<ResponseWrapper> updateUserAppSetting(
            @Field("UserId") String UserId,
            @Field("Notification") boolean Notification,
            @Field("LangId") int LangId);


    @FormUrlEncoded
    @POST("api/User/UpdateUser")
    Call<ResponseWrapper<UpdateUserEnt>> updateProfile(
            @Field("Id") String Id,
            @Field("Email") String Email,
            @Field("FullName") String FullName,
            @Field("PhoneNumber") String PhoneNumber,
            @Field("UserThumbnailImage") String UserThumbnailImage,
            @Field("Height") String Height,
            @Field("Weight") String Weight,
            @Field("EmergencyContact") String EmergencyContact,
            @Field("GenderId") String GenderId,
            @Field("IsDeleted") boolean IsDeleted
    );

    @FormUrlEncoded
    @POST("api/User/SetUserToken")
    Call<ResponseWrapper> updateToken(
            @Field("UserId") String UserId,
            @Field("FCMToken") String FCMToken,
            @Field("DeviceTypeId") String DeviceTypeId
    );

    @GET("api/User/GetUserFitnessClassesLite")
    Call<ResponseWrapper<ArrayList<BookingHistoryEnt>>> getUserFitnessClasses(
            @Query("userId") String userId);

    @GET("api/User/GetUserSubscriptions")
    Call<ResponseWrapper<ArrayList<UserSubscription>>> getSubsccriptionData(
            @Query("userId") String userId);

    @GET("api/User/GetUserSetting")
    Call<ResponseWrapper<ArrayList<AppDefaultSettingEnt>>> getUserSetting(
            @Query("userId") String userId);

    @GET("api/Account/IsUser")
    Call<ResponseWrapper<IsVerifiedEnt>> isVerified(
            @Query("userId") String userId);

    @GET("api/User/DeactivateUser")
    Call<ResponseWrapper> deactivateUser(
            @Query("userId") String userId);

    @GET("api/User/ActivateUser")
    Call<ResponseWrapper<UserEnt>> activateUser(
            @Query("userId") String userId);


    @GET("api/CancelBookingSurveyQuestion/GetAll")
    Call<ResponseWrapper<ArrayList<SurveyQuestionsEnt>>> surveyQusetions();


    @FormUrlEncoded
    @POST("api/User/IsClassCancellationAllowed")
    Call<ResponseWrapper> cancelClassCheck(
            @Field("UserId") String UserId,
            @Field("FitnessClassId") Integer fitnessID,
            @Field("SelectedFitnessClassDayId") String SelectedFitnessClassDayId );


    @POST("api/UserSurveyAnswerList/Add")
    Call<ResponseWrapper> surveyAnswers(
            @Body CancelBody models
    );



    @GET("api/User/GetAllStudioLite")
    Call<ResponseWrapper<ArrayList<Studio>>> getAllStudios(
            @Query("PageNumber") Integer PageNumber,
            @Query("PageSize") Integer PageSize
    );

    @GET("api/User/GetAllFitnessClassesLite")
    Call<ResponseWrapper<ArrayList<FitnessClassess>>> getAllClasses(
            @Query("DateTime") String DateTime,
            @Query("GenderId") String GenderId,
            @Query("PageNumber") Integer PageNumber,
            @Query("PageSize") Integer PageSize
    );




}