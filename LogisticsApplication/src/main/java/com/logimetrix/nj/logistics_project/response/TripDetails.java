package com.logimetrix.nj.logistics_project.response;

import java.io.Serializable;

/**
 * Created by hp 15 on 26-Aug-17.
 */

public class TripDetails implements Serializable {
    private String reporting_date,reporting_time,unloading_date,unloading_time,out_date,out_time,phone;
    private  int number_of_packages;

    public String getReporting_date() {
        return reporting_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setReporting_date(String reporting_date) {
        this.reporting_date = reporting_date;
    }

    public String getReporting_time() {
        return reporting_time;
    }

    public void setReporting_time(String reporting_time) {
        this.reporting_time = reporting_time;
    }

    public String getUnloading_date() {
        return unloading_date;
    }

    public void setUnloading_date(String unloading_date) {
        this.unloading_date = unloading_date;
    }

    public String getUnloading_time() {
        return unloading_time;
    }

    public void setUnloading_time(String unloading_time) {
        this.unloading_time = unloading_time;
    }

    public String getOut_date() {
        return out_date;
    }

    public void setOut_date(String out_date) {
        this.out_date = out_date;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public int getNumber_of_packages() {
        return number_of_packages;
    }

    public void setNumber_of_packages(int number_of_packages) {
        this.number_of_packages = number_of_packages;
    }
}
