package com.logimetrix.nj.logistics_project.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.logimetrix.nj.logistics_project.R;
import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Splash extends AppCompatActivity {
Handler handler=new Handler();
    private SharedPrefrences shPrfs;
    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        shPrfs = SharedPrefrences.getsharedprefInstance(this);

        if (Build.VERSION.SDK_INT < 23) {
            redirect();
        }
        else {
            if (checkPermission()) {
                //Toast.makeText(Splash.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
                redirect();
            } else {

                requestPermission();
            }
        }
    }
    private void redirect() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               if(shPrfs.getLoginStatus()==true){

                        finish();
                        startActivity(new Intent(Splash.this, DashBoard.class));
                    }else{
                        startActivity(new Intent(Splash.this,LoginActivity.class));
                    }
                finish();
            }
        }, 3 * 1000);
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(Splash.this, new String[]
                {
                        CAMERA,
                        READ_CONTACTS,
                        READ_PHONE_STATE,
                        ACCESS_FINE_LOCATION,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        CALL_PHONE

                }, RequestPermissionCode);

    }


    public boolean checkPermission() {

        int firstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int secondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CONTACTS);
        int thirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int fourthPermissionResult=ContextCompat.checkSelfPermission(getApplicationContext(),ACCESS_FINE_LOCATION);
        int fifthPermissionResult=ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
        int sixthPermissionResult=ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
        int seventhPermissionResult=ContextCompat.checkSelfPermission(getApplicationContext(),CALL_PHONE);
        return      firstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                secondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                thirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                fourthPermissionResult==PackageManager.PERMISSION_GRANTED &&
                fifthPermissionResult==PackageManager.PERMISSION_GRANTED &&
                sixthPermissionResult==PackageManager.PERMISSION_GRANTED &&
                seventhPermissionResult==PackageManager.PERMISSION_GRANTED ;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadContactsPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhoneStatePermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadFineLocationPermission=grantResults[3]==PackageManager.PERMISSION_GRANTED;
                    boolean ReadExternalStoragePermission=grantResults[4]==PackageManager.PERMISSION_GRANTED;
                    boolean ReadCameraPermission= grantResults[5]==PackageManager.PERMISSION_GRANTED;


                    if (CameraPermission && ReadContactsPermission && ReadPhoneStatePermission &&ReadFineLocationPermission && ReadExternalStoragePermission &&ReadCameraPermission ) {
                        Toast.makeText(Splash.this, "Permission Granted", Toast.LENGTH_LONG).show();
                        redirect();
                    } else {

                        if (shouldShowRequestPermissionRationale(CAMERA) || shouldShowRequestPermissionRationale(READ_CONTACTS) || shouldShowRequestPermissionRationale(READ_PHONE_STATE) || shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) || shouldShowRequestPermissionRationale( READ_EXTERNAL_STORAGE)) {
                            showMessageOKCancel("You need to allow  permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{CAMERA,READ_CONTACTS,READ_PHONE_STATE,ACCESS_FINE_LOCATION,READ_EXTERNAL_STORAGE,CALL_PHONE},
                                                        RequestPermissionCode);
                                            }
                                        }
                                    });
                            return;
                        } else {
                            finish();
                            Toast.makeText(Splash.this, "Please Click on Permissions and provide all Required permissions.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", Splash.this.getPackageName(), null);
                            intent.setData(uri);
                            Splash.this.startActivity(intent);
                        }
                    }
                }

                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Splash.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Splash.this.finish();
                    }
                })
                .create()
                .show();
    }
}
