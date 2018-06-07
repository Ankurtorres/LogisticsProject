package com.logimetrix.nj.logistics_project.activities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.logimetrix.nj.logistics_project.R;
import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;

public class Permissions extends AppCompatActivity {


    private static final int REQUEST_PERMISSION_PHONE_STATE = 2;
    private static final int REQUEST_PERMISSION_WRITE_STORAGE = 4;
    private static final int REQUEST_PERMISSION_FINE_LOCATION = 5;

    boolean check = true;

    SharedPrefrences shPrfs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        shPrfs = SharedPrefrences.getsharedprefInstance(this);

        Thread permissionThr = new Thread(new PermissionThread());
        permissionThr.start();
    }

    class PermissionThread implements Runnable {

        @Override
        public void run() {
            assignPermissions();

        }
    }

    private void assignPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_FINE_LOCATION);
        }

//        for (; check; ) {
//            if (AppConstants.per_location && AppConstants.per_storage && AppConstants.per_phone) {
//                finish();
//                check = false;
//            }
//        }

    }

    public static boolean getPermissionDetail(Context con) {
        if (ContextCompat.checkSelfPermission(con, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            return false;
        else if (ContextCompat.checkSelfPermission(con, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            return false;
        else if (ContextCompat.checkSelfPermission(con, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return false;
        else return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        /*switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    shPrfs = SharedPrefrences.getsharedprefInstance(this);
                    shPrfs.setIMEINo(android.os.Build.SERIAL);
                } else {
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    shPrfs = SharedPrefrences.getsharedprefInstance(this);
                    shPrfs.setIMEINo(telephonyManager.getDeviceId());
                    AppConstants.per_phone = true;
                }
                break;

            case REQUEST_PERMISSION_WRITE_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    AppConstants.per_storage = true;
                }
                break;

            case REQUEST_PERMISSION_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    AppConstants.per_location = true;
                }
                break;
        }*/
    }
}
