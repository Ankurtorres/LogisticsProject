package com.logimetrix.nj.logistics_project;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;


public class GpsControl {

    private LocationManager locationManager;
    private MyLocationListener locListener_first;
    public static Location bestLocationResult;
    private Context context;


    public GpsControl(Context context) {
        this.context = context;

    }

    /****************************
     * Only work for gps code is commented for network provider
     *******************************/

    public void startGps() {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locListener_first = new MyLocationListener(context);
        boolean gps_enabled = false;
        //		boolean network_enabled = false;
        Log.e("Start GPS:", "==========");
        float bestAccuracy = 500.0f;
        List<String> matchingProviders = locationManager.getAllProviders();

        for (String provider : matchingProviders) {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                float accuracy = location.getAccuracy();
                if (accuracy < bestAccuracy) {
                    bestLocationResult = location;
                    bestAccuracy = accuracy;
                }
            }
        }

        Log.e("bestLocationResult", "providerName=" + bestLocationResult + " bestAccuracy=" + bestAccuracy);
        try {
            if (bestLocationResult != null) {
                Log.e("location", "location lat: " + bestLocationResult.getLatitude() + " longi: " + bestLocationResult.getLongitude());
                MyLocationListener.latitude = bestLocationResult.getLatitude();

                MyLocationListener.longitude = bestLocationResult.getLongitude();

            } else {
                Log.e("location", "location It is NULL " + bestLocationResult.getLatitude() + " It is NULL " + bestLocationResult.getLongitude());
                MyLocationListener.latitude = 0;
                MyLocationListener.longitude = 0;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            System.out.println("ex.." + ex);
        }
        /***************      Only work for gps code is commented for network provider *******************************/
        //		try {
        //			network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        //		}
        //		catch (Exception ex) {
        //			System.out.println("ex........."+ex);
        //		}
        if (!gps_enabled)
        //				&& !network_enabled)
        {
            Toast.makeText(context, "Gps Unavailable", Toast.LENGTH_LONG).show();
        }
        if (gps_enabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener_first);
        }

        /****************************       Only work for gps code is commented for network provider *******************************/
        //		if (network_enabled)
        //		{
        //			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener_first);
        //		}
    }


    public void stopGps() {
        if (locationManager != null && locListener_first != null) {
            locationManager.removeUpdates(locListener_first);
        }
    }


    public boolean isGPSenabled() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean bool = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return bool;
    }

    public static double getLatitude() {
        if (bestLocationResult != null)
            return bestLocationResult.getLatitude();
        else
            return 0;
    }

    public static double getLongitude() {
        if (bestLocationResult != null)
            return bestLocationResult.getLongitude();
        else
            return 0;
    }

    public float getSPeed(){
        if (bestLocationResult != null)
            return bestLocationResult.getSpeed();
        else
            return 0;
    }
}
