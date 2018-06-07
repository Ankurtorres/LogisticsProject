package com.logimetrix.nj.logistics_project.constants;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by logimetrix on 30/7/16.
 */
public class URLConstants {
    public static String domain="http://test.maanvi.org/";
    public static String api="apprequest/";

    public static String url=domain+api;
    public static String login=url+"login";
    public static String startjorney=url+"start_journey/";
    public static String backgroundService=url+"start_service/";
    public static String full_lr_details=url+"job_discription/";

    public static String save_site_image=url+"save-site-image";
    public static String trip_details=url+"get-task-details";

    public static String save_offline_data=url+"save_offline_data";

    public static String stop_journey=url+"stop_journey";

    public static String multipart_test_data=url+"trip_summary";

    public static String getDeviceId(Context context){
        String device_id=((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (device_id!=null)
            return device_id;
        else
           return Build.SERIAL;
    }

    public static String getImeiNo(Context con){
        String str=null;
        try {
            String deviceId = ((TelephonyManager) con.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            if (deviceId != null) {
                str=deviceId;
            } else {
                String android_id = Settings.Secure.getString(con.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                if(android_id!=null)
                    str=android_id;
            }
        }catch(Exception e){
            e.printStackTrace();
            str="2365489563";
        }

        return str;
    }
}
