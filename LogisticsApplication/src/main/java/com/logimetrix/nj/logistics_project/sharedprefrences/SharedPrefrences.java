package com.logimetrix.nj.logistics_project.sharedprefrences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.logimetrix.nj.logistics_project.response.UserData;

/**
 * Created by logimetrix on 8/7/16.
 */
public class SharedPrefrences {

    private static SharedPrefrences gardenSharedPrfs;
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    private static final String sharedprfstag = "sag";
    private static final String prf_login_status = "loginstatus";
    private static final String prf_loginid = "loginid";
    private static final String prf_imei_no = "imei";
    private static final String prf_user_detail = "userdetail";
    private static final String offline_timestamp = "2016-09-22 07:00:00";


    private SharedPrefrences(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(sharedprfstag, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public static SharedPrefrences getsharedprefInstance(Context con) {
        if (gardenSharedPrfs == null)
            gardenSharedPrfs = new SharedPrefrences(con);
        return gardenSharedPrfs;
    }

    public SharedPreferences getAppSharedPrefs() {
        return appSharedPrefs;
    }

    public void setAppSharedPrefs(SharedPreferences appSharedPrefs) {
        this.appSharedPrefs = appSharedPrefs;
    }

    public SharedPreferences.Editor getPrefsEditor() {
        return prefsEditor;
    }

    public void Commit() {
        prefsEditor.commit();
    }

    public void setBaseURL(String url) {
        this.prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("base_url", url);
        prefsEditor.commit();
    }

    public String getBaseURL() {
        return appSharedPrefs.getString("base_url", "http://pmms.logimetrix.me/api");
    }


    public void setIMEINo(String imeiNo) {
        this.prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString(prf_imei_no, imeiNo);
        prefsEditor.commit();
    }

    public void setRememberMe(boolean status) {
        this.prefsEditor = appSharedPrefs.edit();
        prefsEditor.putBoolean("rememberstatus", status);
        prefsEditor.commit();
    }

    public boolean isRememberMe() {

        return appSharedPrefs.getBoolean("rememberstatus", false);
    }

    public void setPassword(String status) {
        this.prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("password", status);
        prefsEditor.commit();
    }

    public String getPassword() {
        return appSharedPrefs.getString("password", "");
    }

    public void setUserDetails(String selfDetails) {
        this.prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("user_details", selfDetails);
        prefsEditor.commit();

    }
    public UserData getUserDetails()
    {
        String user_data=appSharedPrefs.getString("user_details","");
        UserData userData=new Gson().fromJson(user_data,UserData.class);
        return userData;
    }
    public void setdayStart(boolean status) {
        this.prefsEditor = appSharedPrefs.edit();
        prefsEditor.putBoolean("is_day_start", status);
        prefsEditor.commit();
    }
        public void setLati(String lati)
        {
            this.prefsEditor=appSharedPrefs.edit();
            prefsEditor.putString("latitude",lati);
            prefsEditor.commit();

        }
    public  String getLati()
    {
            return appSharedPrefs.getString("latitude","empty");
    }



    public void setJobId(String Jobid)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString("Jobid",Jobid);
        prefsEditor.commit();

    }
    public  String getJobId()
    {
        return appSharedPrefs.getString("Jobid","");
    }


    public void setLongi(String longi)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putString("longitude",longi);
        prefsEditor.commit();

    }
    public  String getLongi()
    {
        return appSharedPrefs.getString("longitude","empty");
    }
    public void setLastTime(String date){
        this.prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("last_time", date);
        prefsEditor.commit();
    }

    public String getLastTime(){
        return appSharedPrefs.getString("last_time", "empty");
    }
    public boolean isDayStart() {

        return appSharedPrefs.getBoolean("is_day_start", false);
    }
    public void setLoginStatus(boolean status)
    {
        this.prefsEditor=appSharedPrefs.edit();
        prefsEditor.putBoolean("prefStatus",status);
        prefsEditor.commit();
    }
    public boolean getLoginStatus()
    {
        return appSharedPrefs.getBoolean("prefStatus",false);
    }


    public void setStatusButton(String status){
        this.prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("status", status);
        prefsEditor.commit();
    }

    public String getStatusButton(){
        return appSharedPrefs.getString("status", "empty");
    }
}