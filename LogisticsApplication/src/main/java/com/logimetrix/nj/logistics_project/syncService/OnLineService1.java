package com.logimetrix.nj.logistics_project.syncService;

import android.app.Service;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.os.BatteryManager;
        import android.os.Handler;
        import android.os.IBinder;
        import android.os.Message;
        import android.support.annotation.Nullable;
        import android.util.Log;
        import android.widget.Toast;

        import com.j256.ormlite.android.apptools.OpenHelperManager;
        import com.logimetrix.nj.logistics_project.GpsControl;
        import com.logimetrix.nj.logistics_project.Request.LocationTable;
        import com.logimetrix.nj.logistics_project.Request.UserPath;
        import com.logimetrix.nj.logistics_project.constants.URLConstants;
        import com.logimetrix.nj.logistics_project.interfacehelper.VolleyResponse;
        import com.logimetrix.nj.logistics_project.network.InternetConnection;
        import com.logimetrix.nj.logistics_project.network.Network;
        import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;
        import com.logimetrix.nj.logistics_project.sqlitehandler.DBHelper;

        import org.json.JSONObject;

        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.concurrent.TimeUnit;

public class OnLineService1 extends Service {
    private Network network;
    private SharedPrefrences shrPrfs;
    private SimpleDateFormat sdf;
    private double lat1=0.0;
    private double long1=0.0;
    private static String myDate = "";
    public  static  long i=1;
    String status;
    private Double distance;
    int battery=0;
    GpsControl gps;
    private String travelled_distance;
    private String timeDate;
    private String  velo;
    private int batteryvalue;
    long timeInSec;
    private String health_battery;
    private String source_battery;
    private String status_battery;
    private String health;
    private String source;
    private String status_battery1;
    private DBHelper dbHelper;
    private LocalToServer localtoServer;
    private Intent batteryintent;
    private BatteryMonitorBroadcast batteryMonitorBroadcast;

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                i++;

                if (i % 1 == 0) {
                    startSync();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };



    @Override
    public void onCreate() {
        super.onCreate();
        network = Network.getInstance(this);
        shrPrfs = SharedPrefrences.getsharedprefInstance(this);
        Log.d("Service  started","");
        dbHelper = OpenHelperManager.getHelper(this,DBHelper.class);
        //dbHelper = DBHelper.getInstance(this);


        localtoServer = new LocalToServer();


        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        batteryintent = new Intent(getApplicationContext(), BatteryMonitorBroadcast.class);
        batteryMonitorBroadcast = new BatteryMonitorBroadcast();
        registerReceiver(batteryMonitorBroadcast, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        sendBroadcast(batteryintent);
        gps = new GpsControl(OnLineService1.this);
        gps.startGps();
        if(gps !=null) {
            lat1 = GpsControl.getLatitude();
            long1 = GpsControl.getLongitude();

            try {
                shrPrfs.setLastTime(sdf.format(new Date()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            lat1 = 0.0;
            long1 = 0.0;
        }
        shrPrfs.setLati(String.valueOf(lat1));
        shrPrfs.setLongi(String.valueOf(long1));
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.TIME_TICK");
        registerReceiver(broadcastReceiver, filter);
    }
    private long timeDiffer(String lastTime,String presentTime){
        long timeDiff=0;
        try{
            timeDiff= TimeUnit.MILLISECONDS.toSeconds(sdf.parse(presentTime).getTime()-sdf.parse(lastTime).getTime());
        }catch(Exception e){e.printStackTrace();}
        return timeDiff;
    }

    private void startSync() {
        getBatteryStatus();
        setDataFill();
        Double lat2 = 0.0, long2 = 0.0;
        gps= new GpsControl(this);
        gps.startGps();
        if (gps != null) {
            lat2 = GpsControl.getLatitude();
            long2 = GpsControl.getLongitude();
        } else {
            lat2 = 0.0;
            long2 = 0.0;
        }
        distance = distance(lat1, long1, lat2, long2);//function for calculating distance
        timeInSec=timeDiffer(shrPrfs.getLastTime(),sdf.format(new Date()));


        double velocity = 0.0;
        if ((distance!=0 && timeInSec!=0))
            //calculation of velocity
            velocity = ((distance * 1.609354) / timeInSec) * 3600;
        else {
            velocity = Double.valueOf("0");
        }

        travelled_distance = String.valueOf(distance * 1.609354);       //converting double distance to String
        timeDate = myDate;
        //Copying value of myDate to timeDate
        velo = String.valueOf(velocity) ;           //Converting double value of velocity to String
        int battery_status = battery;
        health = health_battery;
        source = source_battery;
        status_battery1 = status_battery;

        Log.e("BR gps vals", String.valueOf(lat2) + "," + String.valueOf(long2));
        shrPrfs.setLati(String.valueOf(lat2));
        shrPrfs.setLongi(String.valueOf(long2));
        lat1 = Double.parseDouble(shrPrfs.getLati());
        long1 = Double.parseDouble(shrPrfs.getLongi());
        Double dis = Double.parseDouble(travelled_distance);
        {
            if (dis < 2) {
                status = "0";

            } else {
                status = "1";
            }
        }

        if(InternetConnection.isConnected(this)) {
            LocationTable locationTable = gpsTable(shrPrfs.getUserDetails().getAccess_token(), String.valueOf(GpsControl.getLatitude()),
                    String.valueOf(GpsControl.getLongitude()), String.valueOf(battery_status),
                    travelled_distance, sdf.format(new Date()), status, timeDate, velo);


            if (locationTable != null)
                localtoServer.syncLocationData(locationTable, this);
        }
                else
                {

                    LocationTable gpstable=new LocationTable();
                    gpstable.setAccess_token(shrPrfs.getUserDetails().getAccess_token());
                    gpstable.setLatitude(String.valueOf(GpsControl.getLatitude()));
                    gpstable.setLongitude(String.valueOf(GpsControl.getLongitude()));
                    gpstable.setBattery_status(String.valueOf(battery_status));
                    gpstable.setTravelled_distance(travelled_distance);
                    gpstable.setAdd_date_time(sdf.format(new Date()));
                    gpstable.setMove_status(status);
                    gpstable.setTime_spend(timeDate);
                    gpstable.setVelocity(velo);
                    gpstable.setSync("0");
                    gpstable.setDataOnOff("0");
                    try {
                        dbHelper.saveLocation(gpstable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


        }

    private LocationTable gpsTable( String access_token,String latitude,String longitude,String battery_status,String travelled_distance,
                                    String add_date_time,String move_status,String time_spend,String velocity)
    {
        LocationTable gpstable=new LocationTable();
        gpstable.setAccess_token(access_token);
        gpstable.setLatitude(latitude);
        gpstable.setLongitude(longitude);
        gpstable.setBattery_status(battery_status);
        gpstable.setTravelled_distance(travelled_distance);
        gpstable.setAdd_date_time(add_date_time);
        gpstable.setMove_status(move_status);
        gpstable.setTime_spend(time_spend);
        gpstable.setVelocity(velocity);
        //gpstable.setBattery_status_happen(battery_status_happen);
        gpstable.setSync("0");
        if(InternetConnection.isConnected(this))
        {
            gpstable.setDataOnOff("1");
        }
        else {gpstable.setDataOnOff("0");}

        if(latitude.equals(0)||longitude.equals(0))
        {
            gpstable=null;
        }
        return gpstable;
    }

    private void getBatteryStatus() {
        Intent intent = new Intent(Intent.ACTION_BATTERY_CHANGED);
        try {
            int level = intent.getIntExtra("level", 0);
            int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            String BStatus = "No Data";
            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                BStatus = "Charging";
            }
            if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                BStatus = "Discharging";
            }
            if (status == BatteryManager.BATTERY_STATUS_FULL) {
                BStatus = "Full";
            }
            if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                BStatus = "Not Charging";
            }
            if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                BStatus = "Unknown";
            }



            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            String BattPowerSource = "No Data";
            if (chargePlug == BatteryManager.BATTERY_PLUGGED_AC) {
                BattPowerSource = "AC";
            }
            if (chargePlug == BatteryManager.BATTERY_PLUGGED_USB) {
                BattPowerSource = "USB";
            }

            String BattLevel = String.valueOf(level);

            int BHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
            String BatteryHealth = "No Data";
            if (BHealth == BatteryManager.BATTERY_HEALTH_COLD) {
                BatteryHealth = "Cold";
            }
            if (BHealth == BatteryManager.BATTERY_HEALTH_DEAD) {
                BatteryHealth = "Dead";
            }
            if (BHealth == BatteryManager.BATTERY_HEALTH_GOOD) {
                BatteryHealth = "Good";
            }
            if (BHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                BatteryHealth = "Over-Voltage";
            }
            if (BHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                BatteryHealth = "Overheat";
            }
            if (BHealth == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                BatteryHealth = "Unknown";
            }
            if (BHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                BatteryHealth = "Unspecified Failure";
            }
            health_battery = BatteryHealth;
            source_battery = BattPowerSource;
            status_battery = BStatus;
            battery = convertbatteryleveltotext(batteryvalue);
            //Do whatever with the data here


        } catch (Exception e) {
            Log.v("Battery Info", "Battery Info Error");
        }

    }

    private int convertbatteryleveltotext(int batteryvalue) {
        return batteryvalue;
    }

    private void setDataFill() {
        Calendar calendar = Calendar.getInstance();
        System.out.println("Current Time==> " + calendar.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mDate = sdf.format(calendar.getTime());
        System.out.println("Current Date==> " + mDate);
        myDate = mDate;
    }

    VolleyResponse vr=new VolleyResponse() {
        @Override
        public void onResponse(JSONObject obj) throws Exception {
            try {

                if (obj.getString("error_code").equals("200")) {

                }
            }catch(Exception e){e.printStackTrace();}
        }
    };
    private Double distance(double latitude1, double longitude1, Double latitude2, Double longitude2) {
        double theta = longitude1 - longitude2;
        double dist = Math.sin(deg2rad(latitude1)) * Math.sin(deg2rad(latitude2)) + Math.cos(deg2rad(latitude1)) * Math.cos(deg2rad(latitude2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private  class BatteryMonitorBroadcast extends  BroadcastReceiver{

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            Log.e("", arg1.toString());
            Log.v("", "******************** Battery Low ************************" + arg1.getIntExtra("level", 0));
            batteryvalue = arg1.getIntExtra("level", 0);
        }
    }
}
