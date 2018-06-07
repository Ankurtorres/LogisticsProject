package com.logimetrix.nj.logistics_project.response;

/**
 * Created by Ankur_ on 4/12/2018.
 */

public class JourneyDetails {

    String LoginId,access_token,job_id;


    public JourneyDetails(String LoginId, String access_token, String job_id ){
        this.LoginId = LoginId;
        this.access_token=access_token;

        this.job_id=job_id;


    }
    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
}
