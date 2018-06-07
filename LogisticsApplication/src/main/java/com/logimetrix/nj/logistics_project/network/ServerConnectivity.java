package com.logimetrix.nj.logistics_project.network;

import org.json.JSONObject;

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
import org.json.JSONObject;

import java.util.List;
public class ServerConnectivity {
    public ServerConnectivity(){}
    public static JSONObject post(List<NameValuePair> data, String url){
        JSONObject response=null;
        Log.e("test", "url---> " + url);
        Log.e("test", "Params---> " + data.toString());

        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 10000);
            DefaultHttpClient httpClient = new DefaultHttpClient(params);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            response = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
        }catch (Exception e){}

        return response;
    }
}
