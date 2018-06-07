package com.logimetrix.nj.logistics_project.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.logimetrix.nj.logistics_project.application.VolleyApplication;
import com.logimetrix.nj.logistics_project.constants.URLConstants;
import com.logimetrix.nj.logistics_project.interfacehelper.VolleyResponse;
import com.logimetrix.nj.logistics_project.response.UserData;
import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by logimetrix on 30/7/16.
 */
public class Network {
    private static Network network;
     private Context context;
    SharedPrefrences sharedPrefrences;
    private final ArrayList<NameValuePair> pairs;
    private static SharedPrefrences sharedPrefrences1;


    private Network(Context context) {
        this.context=context;
        pairs = new ArrayList<>();

    }

    public static Network getInstance(Context context) {
        if (network == null)
            network = new Network(context);
        sharedPrefrences1 = SharedPrefrences.getsharedprefInstance(context);
        return network;
    }
    public Map<String,String> getCommonMap(){

        Map<String,String> map=new HashMap<>();
        UserData user=sharedPrefrences1.getUserDetails();
        map.put("access_token",user.getAccess_token());
        // map.put("loginId",lr.getLoginID());

        return map;
    }

//    public JSONObject login(String loginId, String latitude, String longitude, String deviceId) {
//        JSONObject response ;
//        pairs.clear();
//        pairs.add(new BasicNameValuePair("LoginID", loginId));
//        pairs.add(new BasicNameValuePair("user_latitude", latitude));
//        pairs.add(new BasicNameValuePair("user_longitude",longitude));
//        pairs.add(new BasicNameValuePair("device_id", deviceId));
//        pairs.add(new BasicNameValuePair("device_type", "android"));
//        pairs.add(new BasicNameValuePair("api_name", "login"));
//        response = ServerConnectivity.post(pairs, URLConstants.login);
//        return response;
//    }

//    public JSONObject updateStatus(String oi,String os) {
//        JSONObject response ;
//        UserDetail ud=shPrfs.getUserDetail();
//        pairs.clear();
//        pairs.add(new BasicNameValuePair("LoginID", ud.getLoginID()));
//        pairs.add(new BasicNameValuePair("order_status", os));
//        pairs.add(new BasicNameValuePair("appkey", ud.getDevice_token()));
//        pairs.add(new BasicNameValuePair("order_id", oi));
//        response = ServerConnectivity.post(pairs, URLConstants.changeStatus);
//        return response;
//    }
//
//    public Map<String,String> getCommonMap(){
//        Map<String,String> map=new HashMap<>();
//        map.put("appkey",lr.getDevice_token());
//        map.put("loginId",lr.getLoginID());
//
//        return map;
//    }
    public void startVolleyService(final String url, final Map<String,String> implementedMap, final VolleyResponse vr ){
        Log.w("request",implementedMap.toString());
         StringRequest jsonReq = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.w("Response",url+" "+response);
                                        //vr.onResponse(response);
                                    }catch(Exception e){e.printStackTrace();}
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            return implementedMap;
                        }
                    };
        jsonReq.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    VolleyApplication.getInstance().getRequestQueue().add(jsonReq);
    }


    public void requestWithJsonObject(final String url, Object object, final VolleyResponse vr){
        JSONObject json=null;
        try {
             json = new JSONObject(new Gson().toJson(object));
            Log.w("URL ",url+" Request "+json.toString());
        }catch(Exception e){e.printStackTrace();}
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.w("URL ",url+",Response= "+response.toString());
                    vr.onResponse(response);
                }catch(Exception e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("network error",error.toString());
                        NetworkResponse networkResponse = error.networkResponse;
                        String errorMessage = "Unknown error";
                        if (networkResponse == null) {
                            if (error.getClass().equals(TimeoutError.class)) {
                                errorMessage = "Request timeout";
                            } else if (error.getClass().equals(NoConnectionError.class)) {
                                errorMessage = "Failed to connect server";
                            }
                        } else {
                            String result = new String(networkResponse.data);
                            try {
                                JSONObject response = new JSONObject(result);
                                String status = response.getString("status");
                                String message = response.getString("message");

                                Log.e("Error Status", status);
                                Log.e("Error Message", message);

                                if (networkResponse.statusCode == 404) {
                                    errorMessage = "Resource not found";
                                } else if (networkResponse.statusCode == 401) {
                                    errorMessage = message+" Please login again";
                                } else if (networkResponse.statusCode == 400) {
                                    errorMessage = message+ " Check your inputs";
                                } else if (networkResponse.statusCode == 500) {
                                    errorMessage = message+" Something is getting wrong";
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("Error", errorMessage);
                        error.printStackTrace();
                    }
                }
        );
 //       jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,1,1f) {
 //       });
//
        VolleyApplication.getInstance().getRequestQueue().add(jsonObjectRequest);
    }

//    public void requestWithJsonArray(final String url, JSONArray jsonArray, VolleyResponse vr){
//        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//            }
//        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                    }
//                }
//        );
//        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000,1,1f) {
//        });
//
//        VolleyApplication.getInstance().getRequestQueue().add(jsonArrayRequest);
//    }

    private byte[] getInByteArray(String path){
        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/sterlite","/img1488280203275.png");
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] b=stream.toByteArray();
        Log.w("byte ",b.toString());
        return b;
    }


}
