package com.logimetrix.nj.logistics_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.logimetrix.nj.logistics_project.GpsControl;
import com.logimetrix.nj.logistics_project.R;
import com.logimetrix.nj.logistics_project.Request.StartRide;
import com.logimetrix.nj.logistics_project.activities.Dialogs;
import com.logimetrix.nj.logistics_project.constants.URLConstants;
import com.logimetrix.nj.logistics_project.interfacehelper.VolleyResponse;
import com.logimetrix.nj.logistics_project.network.Connection;
import com.logimetrix.nj.logistics_project.network.InternetConnection;
import com.logimetrix.nj.logistics_project.network.Network;
import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;
import com.logimetrix.nj.logistics_project.syncService.Constants;
import com.logimetrix.nj.logistics_project.syncService.ForegroundService;
import com.logimetrix.nj.logistics_project.syncService.LocalToServer;
import com.logimetrix.nj.logistics_project.syncService.OnLineService1;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import newSyncService.AnotherService;
import newSyncService.MainServiceClass;

public class StartJourney extends BaseActivity {
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.start_d)
    Button start_journey;
    android.app.AlertDialog alertDialog;
    private SharedPrefrences sharedPrefrences;
    private Network network;
    private SimpleDateFormat sdf1;
    private TextView time1;
    private ImageView imgView;
    private TextView tv;
    private String day_start_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_journey);
        ButterKnife.bind(this);
        network = Network.getInstance(this);
        sharedPrefrences = SharedPrefrences.getsharedprefInstance(this);
        sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        GpsControl gpsControl=new GpsControl(this);
        gpsControl.startGps();
        tv = (TextView) findViewById(R.id.welcome_text);
        tv.setText("Hi" + " \t " +  sharedPrefrences.getUserDetails().getName());
        time1 = (TextView) findViewById(R.id.time);
        imgView = (ImageView) findViewById(R.id.sun);
        alertDialog=new SpotsDialog(this);
        TimerDifference();

    }
    @OnClick(R.id.start_d)
    public void startJourney() {
        if(InternetConnection.isConnected(StartJourney.this)) {
                    doStart();
                    //startForeground();
        }else {
                    Toast.makeText(StartJourney.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();}}
/*    private void startForeground() {
        Intent startIntent = new Intent(StartJourney.this, ForegroundService.class);
        startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(startIntent);}*/

    private void TimerDifference() {
        try {
            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
            Log.e("Time","current_time"+timeOfDay);
            if (timeOfDay >= 0 && timeOfDay < 12) {
                time1.setText("Good morning !!");
                imgView.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.sunrise));
            }
            else if (timeOfDay >= 12 && timeOfDay < 16) {
                time1.setText("Good afternoon !!");
                imgView.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.sun));
            } else if (timeOfDay >= 16 && timeOfDay <= 24) {
              time1.setText("Good evening !!");
                imgView.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.sunset));}
        }catch (Exception e){e.printStackTrace();}}
    private void doStart() {
        if(Connection.isGpsEnable(this)) {
            alertDialog.show();
            alertDialog.setCancelable(false);
            Date cDate = new Date();
            day_start_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cDate);
            network.requestWithJsonObject(URLConstants.startjorney, new StartRide(sharedPrefrences.getPassword(),sharedPrefrences.getUserDetails().getAccess_token()), vr);
            Toast.makeText(this, "Your Journey Started", Toast.LENGTH_SHORT).show();}
        else {
            Toast.makeText(this, "Hey! Please enable Gps", Toast.LENGTH_LONG).show();
            Intent intet=new Intent(this,Dialogs.class);
            intet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intet);}}

    VolleyResponse vr=new VolleyResponse() {
        @Override
        public void onResponse(JSONObject obj) throws Exception {
            if (obj.getString("error_code").equals("0")) {

                if (alertDialog.isShowing())
                    alertDialog.dismiss();
                Toast.makeText(StartJourney.this, obj.getString("response_string").toString(), Toast.LENGTH_LONG).show();
            } else {

                sharedPrefrences.setdayStart(true);
                if (alertDialog.isShowing())
                    alertDialog.dismiss();
                sharedPrefrences.setJobId(obj.getString("data"));
                startActivity(new Intent(StartJourney.this,DashBoard.class));
                finish();
                Toast.makeText(StartJourney.this, obj.getString("response_string").toString(), Toast.LENGTH_LONG).show();
            }}
    };}
