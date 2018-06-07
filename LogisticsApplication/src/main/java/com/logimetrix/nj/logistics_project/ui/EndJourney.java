package com.logimetrix.nj.logistics_project.ui;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.logimetrix.nj.logistics_project.GpsControl;
import com.logimetrix.nj.logistics_project.R;
import com.logimetrix.nj.logistics_project.Request.EndRide;
import com.logimetrix.nj.logistics_project.activities.Dialogs;
import com.logimetrix.nj.logistics_project.constants.URLConstants;
import com.logimetrix.nj.logistics_project.interfacehelper.VolleyResponse;
import com.logimetrix.nj.logistics_project.network.Connection;
import com.logimetrix.nj.logistics_project.network.InternetConnection;
import com.logimetrix.nj.logistics_project.network.Network;
import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;
import com.logimetrix.nj.logistics_project.ui.BaseActivity;
import com.logimetrix.nj.logistics_project.ui.LoginActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import dmax.dialog.SpotsDialog;
import newSyncService.AnotherService;
import newSyncService.MainServiceClass;

public class EndJourney extends BaseActivity implements View.OnClickListener {
    Button journey_end;
    SharedPrefrences sharedPrefrences;
    Network network;
    android.app.AlertDialog alertDialog;
    GpsControl gpsControl;
    private String day_start_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_journey);
        network = Network.getInstance(this);
        alertDialog = new SpotsDialog(this);
        gpsControl = new GpsControl(getApplicationContext());
        gpsControl.startGps();
        journey_end = (Button) findViewById(R.id.end_d);
        journey_end.setOnClickListener(this);
        sharedPrefrences = SharedPrefrences.getsharedprefInstance(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.end_d:

                if (InternetConnection.isConnected(this)) {
                    doDayend();
                } else {
                    Toast.makeText(this, "Hey! Please connect to Internet", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void doDayend() {
        if (Connection.isGpsEnable(this)) {
            alertDialog.show();
            Date cDate = new Date();
            day_start_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cDate);
            network.requestWithJsonObject(URLConstants.stop_journey, new EndRide(sharedPrefrences.getUserDetails().getAccess_token(),
                    sharedPrefrences.getJobId(),sharedPrefrences.getPassword()), journeyEndResponse);

        } else {
            Toast.makeText(this, "Hey! Please enable Gps", Toast.LENGTH_LONG).show();
            Intent intet = new Intent(this, Dialogs.class);
            intet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intet);
        }
    }

    VolleyResponse journeyEndResponse = new VolleyResponse() {
        @Override
        public void onResponse(JSONObject obj) throws Exception {
            if (obj.getString("error_code").equals("1")) {
                stopService(new Intent(EndJourney.this, MainServiceClass.class));
                    stopService(new Intent(EndJourney.this, AnotherService.class));
                sharedPrefrences.setLoginStatus(false);
                sharedPrefrences.setdayStart(false);
                Intent daye = new Intent(EndJourney.this, LoginActivity.class);
                startActivity(daye);
                finish();
                if (alertDialog.isShowing()) alertDialog.dismiss();
                Toast.makeText(EndJourney.this, "Thanks! ", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(EndJourney.this, obj.getString("response_string").toString(), Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        }
    };
    private void startServices(){


        if (!isSeviceRunning("MainServiceClass"))
            startService(new Intent(this, MainServiceClass.class));
        if (!isSeviceRunning("AnotherService"))
            startService(new Intent(this, AnotherService.class));
       /* if(!isSeviceRunning("LocalToServer"))
            startService(new Intent(this, LocalToServer.class));*/
    }
   private void stopServices()
    {

        if (isSeviceRunning("MainServiceClass"))
            stopService(new Intent(EndJourney.this, MainServiceClass.class));
        if (isSeviceRunning("AnotherService"))
            stopService(new Intent(EndJourney.this, AnotherService.class));
    }
}
