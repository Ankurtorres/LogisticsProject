package com.logimetrix.nj.logistics_project.Request;

/**
 * Created by Ankur_ on 8/10/2017.
 */
public class StartRide {
    String LoginId, access_token;

    public StartRide(String LoginId, String access_token ){
        this.LoginId = LoginId;
        this.access_token=access_token;


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

}
