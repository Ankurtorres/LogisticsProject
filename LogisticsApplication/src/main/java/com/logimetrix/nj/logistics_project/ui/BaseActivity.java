package com.logimetrix.nj.logistics_project.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ankur_ on 8/11/2017.
 */

public class BaseActivity extends AppCompatActivity {
    public boolean isSeviceRunning(String ServiceClassName){
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(ServiceClassName)) {
                return true;
            }
        }
        return false;

    }
    public void changeColr(int viewId,int color){
        View view=(View)findViewById(viewId);
        GradientDrawable bgShape = (GradientDrawable)view.getBackground();
        bgShape.setColor(color);
    }
    public void changeColr(View v, int viewId, int color){
        View view=(View)v.findViewById(viewId);
        GradientDrawable bgShape = (GradientDrawable)view.getBackground();
        bgShape.setColor(color);
    }
    public void changeStroke(View v,int viewId,int color,int width){
        View view=(View)v.findViewById(viewId);
        GradientDrawable bgShape = (GradientDrawable)view.getBackground();
        bgShape.setStroke(width,color);
    }
    public void isReadPhoneState(Activity activity, Context context){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int result=this.checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if(result!= PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_PHONE_STATE},100);
        }
    }

    public void setTextFonts(int viewId,int typefacetype){
        TextView tv=(TextView)findViewById(viewId);
        Typeface face=null;
        switch (typefacetype){
            case 0:
                face=Typeface.createFromAsset(getAssets(),
                        "fonts/proximanova-bold.ttf");
                break;

            case 1:
                face=Typeface.createFromAsset(getAssets(),
                        "fonts/proximanova-light.ttf");
                break;

            case 2:
                face=Typeface.createFromAsset(getAssets(),
                        "fonts/proximanova-regular.ttf");
                break;

            case 3:
                face=Typeface.createFromAsset(getAssets(),
                        "fonts/proximanova-semibold.ttf");
                break;
        }
        tv.setTypeface(face);

    }


}