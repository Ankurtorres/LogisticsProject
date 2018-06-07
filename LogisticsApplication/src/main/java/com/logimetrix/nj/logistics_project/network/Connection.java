package com.logimetrix.nj.logistics_project.network;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.util.List;


public class Connection {

    private static String rspo;

    public static   String NetworkConnections(String url, List<NameValuePair> pairs){
        Log.w(url,"par="+pairs.toString());

        String responseEntity =null;
        try{
            HttpParams httpParameters=new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,10000);
            DefaultHttpClient client=new DefaultHttpClient(httpParameters);
            HttpPost post=new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response=client.execute(post);
            responseEntity= EntityUtils.toString(response.getEntity());
            rspo = responseEntity.replace("\t\t","");
            Log.w("Gps response", rspo);
        }
        catch(Exception e){
            e.printStackTrace();}

        return rspo;
    }

    public static boolean isGpsEnable(Context ctx){
        boolean gps_enabled=false;
        LocationManager lm = (LocationManager)ctx.getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return gps_enabled;
    }


}
