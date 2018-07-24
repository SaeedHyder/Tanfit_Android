package com.ingic.tanfit.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ingic.tanfit.activities.MainActivity;
import com.ingic.tanfit.entities.AppDefaultSettingEnt;
import com.ingic.tanfit.entities.FavoriteDataEnt;
import com.ingic.tanfit.entities.GetNearestStudiosEnt;
import com.ingic.tanfit.entities.IsVerifiedEnt;
import com.ingic.tanfit.entities.UserAllDataEnt;
import com.ingic.tanfit.entities.UserEnt;
import com.ingic.tanfit.entities.UserSubscription;
import com.ingic.tanfit.retrofit.GsonFactory;

import java.util.Locale;


public class BasePreferenceHelper extends PreferenceHelper {

    private Context context;

    protected static final String KEY_LOGIN_STATUS = "islogin";
    protected static final String KeyIsStudio = "KeyIsStudio";
    protected static final String IsGetUser = "IsGetUser";

    private static final String FILENAME = "preferences";

    protected static final String Firebase_TOKEN = "Firebasetoken";
    protected static final String UserId = "UserId";

    protected static final String Access_Token = "Access_Token";
    protected static final String Header_Access_Token = "Header_Access_Token";
    protected static final String KEY_USER = "KEY_USER";
    protected static final String KEY_USER_All_DATA = "KEY_USER_All_DATA";
    protected static final String Favorite_data = "Favorite_data";
    protected static final String UserSubscriptionData = "UserSubscriptionData";
    protected static final String APP_DEFAULT_SETTING = "APP_DEFAULT_SETTING";
    protected static final String KEY_NearestStudios = "KEY_NearestStudios";
    protected static final String KEY_DRIVER_SESSION = "DRIVERHOME";

    protected static final String NotificationCount = "NotificationCount";
    protected static final String KEY_DEFAULT_LANG = "keyLanguage";


    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void setLoginStatus( boolean isLogin ) {
        putBooleanPreference( context, FILENAME, KEY_LOGIN_STATUS, isLogin );
    }

    public boolean isLogin() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }


    public String getFirebase_TOKEN() {
        return getStringPreference(context, FILENAME, Firebase_TOKEN);
    }

    public void setFirebase_TOKEN(String _token) {
        putStringPreference(context, FILENAME, Firebase_TOKEN, _token);
    }
    public int getNotificationCount() {
        return getIntegerPreference(context, FILENAME, NotificationCount);
    }

    public void setNotificationCount(int count) {
        putIntegerPreference(context, FILENAME, NotificationCount, count);
    }

    public String getAccess_Token() {
        return getStringPreference(context, FILENAME, Access_Token);
    }

    public void setAccess_Token(String access_token) {
        putStringPreference(context, FILENAME, Access_Token, access_token);
    }

    public UserEnt getUser() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), UserEnt.class);
    }

    public void putUser(UserEnt user) {
        putStringPreference(context, FILENAME, KEY_USER, GsonFactory
                .getConfiguredGson().toJson(user));
    }

    public UserAllDataEnt getUserAllData() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_USER_All_DATA), UserAllDataEnt.class);
    }

    public void putUserAllData(UserAllDataEnt user) {
        putStringPreference(context, FILENAME, KEY_USER_All_DATA, GsonFactory
                .getConfiguredGson().toJson(user));
    }

    public GetNearestStudiosEnt getNearestStuidos() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_NearestStudios), GetNearestStudiosEnt.class);
    }

    public void putNearestStudios(GetNearestStudiosEnt user) {
        putStringPreference(context, FILENAME, KEY_NearestStudios, GsonFactory
                .getConfiguredGson().toJson(user));
    }

    public AppDefaultSettingEnt getAppDefaultSetting() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, APP_DEFAULT_SETTING), AppDefaultSettingEnt.class);
    }

    public void putAppDefaultSetting(AppDefaultSettingEnt user) {
        putStringPreference(context, FILENAME, APP_DEFAULT_SETTING, GsonFactory
                .getConfiguredGson().toJson(user));
    }


    public String getUserId() {
        return getStringPreference(context, FILENAME, UserId);
    }

    public void setUserId(String userId) {
        putStringPreference(context, FILENAME, UserId, userId);
    }

    public void setIsFromStudio( boolean isStudio ) {
        putBooleanPreference( context, FILENAME, KeyIsStudio, isStudio );
    }

    public boolean isStudio() {
        return getBooleanPreference(context, FILENAME, KeyIsStudio);
    }

    public void setIsGetUser( boolean getUser ) {
        putBooleanPreference( context, FILENAME, IsGetUser, getUser );
    }

    public boolean getIsUserGetData() {
        return getBooleanPreference(context, FILENAME, IsGetUser);
    }

    public FavoriteDataEnt getFavoriteData() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, Favorite_data), FavoriteDataEnt.class);
    }

    public void putFavoriteData(FavoriteDataEnt user) {
        putStringPreference(context, FILENAME, Favorite_data, GsonFactory
                .getConfiguredGson().toJson(user));
    }
    public UserSubscription getUserDefaultData() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, UserSubscriptionData), UserSubscription.class);
    }

    public void putUserDefaultData(UserSubscription user) {
        putStringPreference(context, FILENAME, UserSubscriptionData, GsonFactory
                .getConfiguredGson().toJson(user));
    }

    public IsVerifiedEnt getIsVerified() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, UserSubscriptionData), IsVerifiedEnt.class);
    }

    public void putIsVerified(IsVerifiedEnt user) {
        putStringPreference(context, FILENAME, UserSubscriptionData, GsonFactory
                .getConfiguredGson().toJson(user));
    }


    public void removeRideSessionPreferences() {
        removePreference(context, FILENAME, KEY_DRIVER_SESSION);

    }

    public void putLang(Activity activity, String lang) {
        Log.v("lang", "|" + lang);
        Resources resources = context.getResources();

        if (lang.equals("fa")){
            lang = "fa";}
        else{
            lang = "en";}

        putStringPreference(context, FILENAME, KEY_DEFAULT_LANG, lang);
        //Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        android.content.res.Configuration conf = resources.getConfiguration();
//        conf.setLocale(new Locale(lang));
        conf.locale = new Locale(lang);
        resources.updateConfiguration(conf, dm);
        ((MainActivity) activity).restartActivity();

    }



    public String getLang() {
        return getStringPreference(context, FILENAME, KEY_DEFAULT_LANG);
    }

    public boolean isLanguagePersian() {
        return getLang().equalsIgnoreCase("fa");
    }

}
