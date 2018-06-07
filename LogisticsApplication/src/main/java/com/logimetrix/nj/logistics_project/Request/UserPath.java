package com.logimetrix.nj.logistics_project.Request;

/**
 * Created by Ankit on 7/28/2017.
 */

public class UserPath {
    String access_token,latitude,longitude,battery_status,travelled_distance,add_date_time,move_status,time_spend,velocity,battery_status_happen;


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
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

    public String getBattery_status() {
        return battery_status;
    }

    public void setBattery_status(String battery_status) {
        this.battery_status = battery_status;
    }

    public String getTravelled_distance() {
        return travelled_distance;
    }

    public void setTravelled_distance(String travelled_distance) {
        this.travelled_distance = travelled_distance;
    }

    public String getAdd_date_time() {
        return add_date_time;
    }

    public void setAdd_date_time(String add_date_time) {
        this.add_date_time = add_date_time;
    }

    public String getMove_status() {
        return move_status;
    }

    public void setMove_status(String move_status) {
        this.move_status = move_status;
    }

    public String getTime_spend() {
        return time_spend;
    }

    public void setTime_spend(String time_spend) {
        this.time_spend = time_spend;
    }

    public String getVelocity() {
        return velocity;
    }

    public void setVelocity(String velocity) {
        this.velocity = velocity;
    }

    public String getBattery_status_happen() {
        return battery_status_happen;
    }

    public void setBattery_status_happen(String battery_status_happen) {
        this.battery_status_happen = battery_status_happen;
    }

    public UserPath(String access_token, String latitude, String longitude, String battery_status, String travelled_distance, String add_date_time, String move_status, String time_spend, String velocity, String battery_status_happen){

        this.access_token = access_token;
        this.latitude = latitude;
        this.longitude = longitude;
        this.battery_status = battery_status;
        this.travelled_distance = travelled_distance;
        this.add_date_time = add_date_time;
        this.move_status = move_status;
        this.time_spend = time_spend;
        this.velocity=velocity;
        this.battery_status_happen=battery_status_happen;
    }
}
