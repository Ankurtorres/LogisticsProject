package com.logimetrix.nj.logistics_project.Request;


public class EndRide {


    String access_token, job_id ,LoginId;

    public EndRide(String access_token, String job_id , String LoginId)
    {
        this.access_token  = access_token ;
        this.job_id  = job_id ;
        this.LoginId = LoginId;
    }
}