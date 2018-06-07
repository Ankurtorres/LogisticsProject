package com.logimetrix.nj.logistics_project;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {

    public static double latitude = 0.0;
    public static double longitude = 0.0;
    private Context context;


    public MyLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        //Log.e("location accuracy", "-----"+location.getProvider());
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (location == null || currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isMoreAccurate = accuracyDelta < 0;
        if (!isMoreAccurate) {
            return true;
        } else {
            return false;
        }
    }
}

