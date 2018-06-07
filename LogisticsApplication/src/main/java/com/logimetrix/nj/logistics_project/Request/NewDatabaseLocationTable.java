package com.logimetrix.nj.logistics_project.Request;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ankur_ on 4/17/2018.
 */


@DatabaseTable(tableName = "newlocation_table")

public class NewDatabaseLocationTable extends BaseClass {
    /**
     * Created by Ankur_ on 8/18/2017.
     */

        @DatabaseField
        String latitude ;
        @DatabaseField
        String longitude ;
        @DatabaseField
        String battery_status ;
        @DatabaseField
        String add_date_time ;
        @DatabaseField
        String data_status ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getSync() {
        return Sync;
    }

    public void setSync(String sync) {
        Sync = sync;
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

    public String getAdd_date_time() {
        return add_date_time;
    }

    public void setAdd_date_time(String add_date_time) {
        this.add_date_time = add_date_time;
    }

    public String getData_status() {
        return data_status;
    }

    public void setData_status(String data_status) {
        this.data_status = data_status;
    }

    public String getMoving_status() {
        return moving_status;
    }

    public void setMoving_status(String moving_status) {
        this.moving_status = moving_status;
    }

    @DatabaseField
        String moving_status ;

    }

