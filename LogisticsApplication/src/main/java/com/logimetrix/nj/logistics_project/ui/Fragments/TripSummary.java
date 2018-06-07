package com.logimetrix.nj.logistics_project.ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logimetrix.nj.logistics_project.R;
import com.logimetrix.nj.logistics_project.Request.TakData;
import com.logimetrix.nj.logistics_project.constants.URLConstants;
import com.logimetrix.nj.logistics_project.interfacehelper.VolleyResponse;
import com.logimetrix.nj.logistics_project.response.TripDetails;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Ankur_ on 4/18/2018.
 */

public class TripSummary extends Fragment {

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.trip_summary, container, false);
        CardView card_trip_summary = (CardView)view.findViewById(R.id.card_trip_summary);
        card_trip_summary.setBackgroundResource(R.drawable.remarks);
        TextView reporting_date_id = (TextView)view. findViewById(R.id.reporting_date_id);

        TextView reporting_time_id = (TextView)view.findViewById(R.id.reporting_time_id);
        TextView unloading_date_id = (TextView)view. findViewById(R.id.unloading_date_id);
        TextView unloading_time_id = (TextView)view. findViewById(R.id.unloading_time_id);
        TextView out_date_id = (TextView) view.findViewById(R.id.out_date_id);
        TextView out_time_id = (TextView) view.findViewById(R.id.out_time_id);
        TextView packages_id = (TextView) view.findViewById(R.id.packages_id);
        TextView phone_id = (TextView) view.findViewById(R.id.phone_id);
        hitgetalldata();
        return view;

    }
    private void hitgetalldata() {
      //  network.requestWithJsonObject(URLConstants.trip_details,new TakData("YToyOntzOjU6InBob25lIjtzOjk6Ijc0MTg1Mjk2MyI7czo5Oi"),interfacedata);

    }
    VolleyResponse interfacedata=new VolleyResponse() {
        @Override
        public void onResponse(JSONObject obj) throws Exception {
           /* try {
                if (obj.getString("error_code").equals("200")&&obj.has("getSiteAllocationList") && !obj.isNull("getSiteAllocationList")) {
                    Log.i("rsponse",obj+"");
                    Type token = new TypeToken<List<TripDetails>>() {
                    }.getType();
                    trip_details = new Gson().fromJson(obj.getJSONArray("getSiteAllocationList").toString(), token);
                    if (trip_details != null) {
                        phone_id.setText(trip_details.get(0).getPhone());
                        reporting_date_id.setText(trip_details.get(0).getReporting_date());
                        reporting_time_id.setText(trip_details.get(0).getReporting_time());
                        unloading_date_id.setText(trip_details.get(0).getUnloading_date());
                        unloading_time_id.setText(trip_details.get(0).getUnloading_time());
                        out_date_id.setText(trip_details.get(0).getOut_date());
                        out_time_id.setText(trip_details.get(0).getOut_time());
                        packages_id.setText(trip_details.get(0).getNumber_of_packages());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }*/
        }
    };

}
