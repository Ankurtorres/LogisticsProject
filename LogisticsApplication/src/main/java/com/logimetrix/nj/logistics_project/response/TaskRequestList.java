package com.logimetrix.nj.logistics_project.response;

import java.io.Serializable;

import java.io.Serializable;

/**
 * Created by Pavilion on 14-08-2017.
 */

public class TaskRequestList implements Serializable {

    private String job_id;
    private String trucker_id;
    private String date;
    private String load_capacity;
    private String discription_of_material;
    private String loaded_quantity;
    private String unit;
    private String consigner;
    private String consignee;
    private String completion_amount;
    private String completion_time;
    private String transporter;
    private String station_from;
    private String station_to;
    private String status;
    private String lro_code;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getTrucker_id() {
        return trucker_id;
    }

    public void setTrucker_id(String trucker_id) {
        this.trucker_id = trucker_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLoad_capacity() {
        return load_capacity;
    }

    public void setLoad_capacity(String load_capacity) {
        this.load_capacity = load_capacity;
    }

    public String getDiscription_of_material() {
        return discription_of_material;
    }

    public void setDiscription_of_material(String discription_of_material) {
        this.discription_of_material = discription_of_material;
    }

    public String getLoaded_quantity() {
        return loaded_quantity;
    }

    public void setLoaded_quantity(String loaded_quantity) {
        this.loaded_quantity = loaded_quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getConsigner() {
        return consigner;
    }

    public void setConsigner(String consigner) {
        this.consigner = consigner;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCompletion_amount() {
        return completion_amount;
    }

    public void setCompletion_amount(String completion_amount) {
        this.completion_amount = completion_amount;
    }

    public String getCompletion_time() {
        return completion_time;
    }

    public void setCompletion_time(String completion_time) {
        this.completion_time = completion_time;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public String getStation_from() {
        return station_from;
    }

    public void setStation_from(String station_from) {
        this.station_from = station_from;
    }

    public String getStation_to() {
        return station_to;
    }

    public void setStation_to(String station_to) {
        this.station_to = station_to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLro_code() {
        return lro_code;
    }

    public void setLro_code(String lro_code) {
        this.lro_code = lro_code;
    }
}