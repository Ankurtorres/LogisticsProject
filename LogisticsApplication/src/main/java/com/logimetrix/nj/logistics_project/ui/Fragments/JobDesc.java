package com.logimetrix.nj.logistics_project.ui.Fragments;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logimetrix.nj.logistics_project.GpsControl;
import com.logimetrix.nj.logistics_project.R;
import com.logimetrix.nj.logistics_project.response.JourneyDetails;
import com.logimetrix.nj.logistics_project.response.TaskRequestList;
import com.logimetrix.nj.logistics_project.activities.Dialogs;
import com.logimetrix.nj.logistics_project.constants.URLConstants;
import com.logimetrix.nj.logistics_project.interfacehelper.VolleyResponse;
import com.logimetrix.nj.logistics_project.network.Connection;
import com.logimetrix.nj.logistics_project.network.InternetConnection;
import com.logimetrix.nj.logistics_project.network.Network;
import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;
import com.logimetrix.nj.logistics_project.ui.DashBoard;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import newSyncService.AnotherService;
import newSyncService.MainServiceClass;

import static com.logimetrix.nj.logistics_project.R.id.job_complete;

/**
 * Created by Ankur_ on 8/24/2017.
 */

public class JobDesc extends android.support.v4.app.Fragment implements View.OnClickListener {

    private Network network;
    private SharedPrefrences sharedPrefrences;
    Context context;
    List<TaskRequestList> task_list;
    private View view;
    private TextView truck_no;
    private TextView date;
    private TextView phone;
    private TextView chassis;
    private TextView load;
    private TextView description;

    private TextView quantity;
    private TextView unit;
    private TextView consigner;
    private TextView consignee;
    private TextView transporter;
    private TextView invoiceno;
    private TextView invoicedate;
    private TextView loading;
    private TextView destination;
    private Button completion;
    private Network network1;
    private GpsControl gpsControl;
    private FloatingActionButton floatingActionButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefrences = SharedPrefrences.getsharedprefInstance(getActivity());
        network = Network.getInstance(getActivity());
        gpsControl = new GpsControl(context);
       ((DashBoard)getActivity()).changeHeightLayout(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_job_desc, container, false);

        view.findViewById(R.id.start_date);

        date = (TextView) view.findViewById(R.id.date_id);
        phone = (TextView) view.findViewById(R.id.task_adapter_due_date);
        //chassis = (TextView) view.findViewById(R.id.layout_chassis);
        load = (TextView) view.findViewById(R.id.layout_capacity);
        description = (TextView) view.findViewById(R.id.load_description);
        quantity = (TextView) view.findViewById(R.id.load_weight);
        unit = (TextView) view.findViewById(R.id.load_unit);
        consigner = (TextView) view.findViewById(R.id.consignr1);
        consignee = (TextView) view.findViewById(R.id.consig);
        transporter = (TextView) view.findViewById(R.id.pack2);
        invoiceno = (TextView) view.findViewById(R.id.load_invoice_no);
        invoicedate = (TextView) view.findViewById(R.id.load_invoice_date);
        loading = (TextView) view.findViewById(R.id.load_point);
        destination = (TextView) view.findViewById(R.id.load_destination);
        completion = (Button) view.findViewById(job_complete);
        completion.setOnClickListener(this);
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab);
        startServices();
        hitGetTaskData();
        return view;
    }

    private void hitGetTaskData() {


        if (InternetConnection.isConnected(getActivity())) {
            //Map<String, String> map = Network.getInstance(context).getCommonMap();
            network.requestWithJsonObject(URLConstants.full_lr_details, new JourneyDetails(sharedPrefrences.getPassword(),sharedPrefrences.getUserDetails().getAccess_token(),sharedPrefrences.getJobId()), taskInterfaceHelper);
        } else {
            Toast.makeText(context, "Hey! Please connect to Internet", Toast.LENGTH_SHORT).show();
        }

    }

    VolleyResponse taskInterfaceHelper = new VolleyResponse() {
        @Override
        public void onResponse(JSONObject obj) throws Exception {
            try {
                if (obj.has("data") && !obj.isNull("data"))
                {
                    TaskRequestList task_list=new Gson().fromJson(String.valueOf(obj.getJSONObject("data")),TaskRequestList.class);
                    if(task_list!=null)
                    {
                        date.setText(task_list.getDate());
                        phone.setText(sharedPrefrences.getUserDetails().getPhone());
                        load.setText(task_list.getLoad_capacity());
                        description.setText(task_list.getDiscription_of_material());
                        quantity.setText(task_list.getLoaded_quantity());
                        unit.setText(task_list.getUnit());
                        consigner.setText(task_list.getConsigner());
                        consignee.setText(task_list.getConsignee());
                        transporter.setText(task_list.getTransporter());
                        invoiceno.setText(task_list.getCompletion_amount());
                        invoicedate.setText(task_list.getCompletion_time());
                        loading.setText(task_list.getStation_from());
                        destination.setText(task_list.getStation_to());
                   /*     sharedPrefrences.setLati(String.valueOf(task_list.getLatitude()));
                        sharedPrefrences.setLongi(String.valueOf(task_list.getLongitude()));*/
                       if (task_list.getStatus().equals("1"))
                        {
                            completion.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_back_green));
                        } else {
                        completion.setBackground(getActivity().getResources().getDrawable(R.drawable.rect_back_orange));
                    }
                    }else
                    {
                        Toast.makeText(context, obj.getString("response_string"), Toast.LENGTH_SHORT).show();
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.job_complete:
                if (InternetConnection.isConnected(getActivity())) {
                    if(Connection.isGpsEnable(getActivity())) {
                            //distance(gpsControl.getLatitude(),gpsControl.getLongitude(),Double.parseDouble(taskRequestlist.getLatitude()),Double.parseDouble(taskRequestlist.getLongitude()));
                            /*Location startPoint = new Location("locationA");
                            startPoint.setLatitude(GpsControl.getLatitude());
                            startPoint.setLongitude(GpsControl.getLongitude());

                            Location endPoint = new Location("locationA");
                            endPoint.setLatitude(Double.parseDouble(sharedPrefrences.getLati()));
                            endPoint.setLongitude(Double.parseDouble(sharedPrefrences.getLongi()));

                            double distance = startPoint.distanceTo(endPoint);
                            Log.w("distance", "between two points" + distance + "meters");
                           // Log.w("distance", "alloted" + sharedPrefrences.getUserDetails().getUserdistance() + "meters");

                            if (distance <200) {
*/

                                //Toast.makeText(getActivity(), "hi, you reached", Toast.LENGTH_SHORT).show();
                                /*Intent intent=new Intent(JobslistActivity.this,CompleteForm.class);
                                startActivity(intent);*/
                                //getTaskData(task_list.get(0));
                           fragmentStart(FormSubmission.class);
                        }
                    else{
                        Toast.makeText(getActivity(), "Hey! Please enable Gps", Toast.LENGTH_LONG).show();
                        Intent intentt=new Intent(context,Dialogs.class);
                        intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentt);

                    }
                    } else {
                    Toast.makeText(getActivity(), "Hey! Please connect to Internet", Toast.LENGTH_SHORT).show();

                    }
                break;
                }
                //getTaskData(taskRequestlist);



        }
    private void startServices(){


        if (!isSeviceRunning("MainServiceClass"))
            getActivity().startService(new Intent(getActivity(), MainServiceClass.class));
        if (!isSeviceRunning("AnotherService"))
           getActivity().startService(new Intent(getActivity(), AnotherService.class));
       /* if(!isSeviceRunning("LocalToServer"))
            startService(new Intent(this, LocalToServer.class));*/
    }

    public boolean isSeviceRunning(String ServiceClassName){
        final ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(ServiceClassName)) {
                return true;
            }
        }
        return false;

    }

    private void fragmentStart(Class fragment) {
        Fragment fragmentClass = null;
        try {
            fragmentClass = (Fragment) fragment.newInstance();

        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ((DashBoard)getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.contentFrame1, fragmentClass)
                .addToBackStack(null).commit();
    }

}

