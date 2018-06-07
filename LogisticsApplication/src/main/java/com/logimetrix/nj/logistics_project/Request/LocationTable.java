package com.logimetrix.nj.logistics_project.Request;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ankur_ on 8/18/2017.
 */

@DatabaseTable(tableName = "location_table")
public class LocationTable extends BaseClass{


    @DatabaseField
    String access_token;

    @DatabaseField
    String latitude;
    @DatabaseField
    String longitude;
    @DatabaseField
    String battery_status;
    @DatabaseField
    String travelled_distance;
    @DatabaseField
    String add_date_time;
    @DatabaseField
    String move_status;
    @DatabaseField
    String time_spend;
    @DatabaseField
    String velocity;
    @DatabaseField
    String DataOnOff;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
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


    public String getDataOnOff() {
        return DataOnOff;
    }

    public void setDataOnOff(String dataOnOff) {
        DataOnOff = dataOnOff;
    }

    public String getSync() {
        return Sync;
    }

    public void setSync(String sync) {
        Sync = sync;
    }


}
