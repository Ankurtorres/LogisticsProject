package com.logimetrix.nj.logistics_project.activities;
/**
 * Created by logimetrix on 1/8/16.
 */
public class AppConstants {

    public static String error_code="error_code";
    public static String response="response_string";

    public static String forsucessresponse="0";
    public static String forfailedresponse="1";
    public static String Ait="0";

    public static String newOrder="0";
    public static String process="1";
    public static String delivered="2";
    public static String received="3";
    public static String decline="4";

    public static int bold=0;
    public static int light=1;
    public static int regular=2;
    public static int semibold=3;

    public static String getStatus(String id){
        String str=null;
        if(id==null)
            str="newOrder";
        if(id.equals("0"))
            str= "newOrder";
        else if(id.equals("1"))
            str= "process";
        else if(id.equals("2"))
            str= "delivered";
        else if(id.equals("3"))
            str= "received";
        else if(id.equals("4"))
            str= "decline";

        return str;
    }



}
