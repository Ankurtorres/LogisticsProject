package newSyncService;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.logimetrix.nj.logistics_project.Request.LocationTable;
import com.logimetrix.nj.logistics_project.Request.NewDatabaseLocationTable;
import com.logimetrix.nj.logistics_project.constants.URLConstants;
import com.logimetrix.nj.logistics_project.network.Connection;
import com.logimetrix.nj.logistics_project.network.InternetConnection;
import com.logimetrix.nj.logistics_project.network.Network;
import com.logimetrix.nj.logistics_project.response.UserData;
import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;
import com.logimetrix.nj.logistics_project.sqlitehandler.DBHelper;
import com.logimetrix.nj.logistics_project.sqlitehandler.NewDBHelper;
import com.logimetrix.nj.logistics_project.syncService.LocalToServer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankur_ on 4/17/2018.
 */

public class AnotherService extends Service {

    private DBHelper dbHelper;
    long id;
    int process=1;

    public  final BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals("android.intent.action.TIME_TICK"))
            {
                try {
                    syncProcessStart();
                }catch (Exception e) {
                    e.printStackTrace();}
                process++;
            }
        }

    };
    private SharedPrefrences sharedPrefrences;
    private Network network;
    private SimpleDateFormat simpleDateFormat;
    private NewDBHelper newDBHelper;

    private void syncProcessStart() throws Exception
    {

        QueryBuilder<NewDatabaseLocationTable, Long> locationTableBuilder = newDBHelper.getlocation().queryBuilder();
        if(InternetConnection.isConnected(this) && locationTableBuilder!=null && locationTableBuilder.where().eq("Sync",0).query().size()>0)
        {
            syncLocationData(locationTableBuilder.where().eq("Sync",0).queryForFirst(),this);
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {


        super.onCreate();
       /* dbHelper = OpenHelperManager.getHelper(this,DBHelper.class);*/
        newDBHelper = OpenHelperManager.getHelper(this,NewDBHelper.class);
        sharedPrefrences = SharedPrefrences.getsharedprefInstance(this);
        network = Network.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.TIME_TICK");
        registerReceiver(broadcastReceiver, filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }


    public  void syncLocationData(NewDatabaseLocationTable locationTable, Context context)
    {
        SharedPrefrences sharedPrefrences=SharedPrefrences.getsharedprefInstance(context);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        UserData userData=sharedPrefrences.getUserDetails();
        List<NameValuePair> list=new ArrayList<>();
        list.add(new BasicNameValuePair("access_token",userData.getAccess_token()));
        list.add(new BasicNameValuePair("latitude",locationTable.getLatitude()));
        list.add(new BasicNameValuePair("longitude",locationTable.getLongitude()));
        list.add(new BasicNameValuePair("battery_status",locationTable.getBattery_status()));
        /*list.add(new BasicNameValuePair("velocity",locationTable.getVelocity()));
        list.add(new BasicNameValuePair("add_date_time",locationTable.getAdd_date_time()));
       list.add(new BasicNameValuePair("time_spend",locationTable.getTime_spend()));*/
        Double dis=Double.parseDouble(String.valueOf("1000"));
        if(dis<2) {
            list.add(new BasicNameValuePair("moving_status","0"));
        }else
            list.add(new BasicNameValuePair("moving_status","1"));
        list.add(new BasicNameValuePair("LoginId",sharedPrefrences.getPassword()));
        /*list.add(new BasicNameValuePair("travelled_distance",locationTable.getTravelled_distance()));*/
        list.add(new BasicNameValuePair("data_status",locationTable.getData_status()));
        list.add(new BasicNameValuePair("job_id",sharedPrefrences.getJobId()));
        new AnotherService.SyncGps(list,locationTable).execute();
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        super.unregisterReceiver(receiver);
    }

    class SyncGps extends AsyncTask<Void,String,String>
    {
        NewDatabaseLocationTable locationTable;
        List<NameValuePair> list;
        private final NewDBHelper dbHelper1;

        public SyncGps(List<NameValuePair> list,NewDatabaseLocationTable locationTable)
        {
            this.list=list;
            this.locationTable=locationTable;
            dbHelper1 = OpenHelperManager.getHelper(AnotherService.this,NewDBHelper.class);
        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String response_string) {
            super.onPostExecute(response_string);
            Log.e("gps","response"+response_string);
           /* try {
                if (response_string!=null && new JSONObject(response_string).getString("error_code").equals("200") )
                {
                    *//*UpdateBuilder<LocationTable,Long> locationUpdate=dbHelper1.getlocation().updateBuilder();
                    locationUpdate.where().eq("id",locationTable.getId());
                    locationUpdate.updateColumnValue("Sync",1);
                    locationUpdate.update();
                }*//*
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();}*/}
        @Override
        protected String doInBackground(Void... params) {
            Log.e("request","requestof_list"+list);
            return Connection.NetworkConnections(URLConstants.backgroundService,list);
        }
    }


}
