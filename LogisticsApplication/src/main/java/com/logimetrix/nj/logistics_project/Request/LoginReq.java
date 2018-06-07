package com.logimetrix.nj.logistics_project.Request;


public class LoginReq {
    String LoginId, latitude, longitude, device_id;

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public LoginReq(String LoginId, String latitude, String longitude, String device_id) {
        this.LoginId = LoginId;
        this.latitude = latitude;
        this.longitude = longitude;

        this.device_id = device_id;
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}