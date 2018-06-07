package com.logimetrix.nj.logistics_project.Request;

/**
 * Created by hp 15 on 26-Aug-17.
 */

public class TakData {

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    String accesstoken;
    public TakData(String accesstoken)
    {
        this.accesstoken=accesstoken;
    }
}
