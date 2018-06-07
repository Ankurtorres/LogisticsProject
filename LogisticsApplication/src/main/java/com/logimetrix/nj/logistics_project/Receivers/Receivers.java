package com.logimetrix.nj.logistics_project.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;
import com.logimetrix.nj.logistics_project.ui.LoginActivity;

/**
 * Created by Ankit on 7/13/2017.
 */

public class Receivers extends BroadcastReceiver {
    SharedPrefrences sharedPrefrences;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        sharedPrefrences= SharedPrefrences.getsharedprefInstance(context);
        if(action.equals("logistics.do.logout")){
            sharedPrefrences=SharedPrefrences.getsharedprefInstance(context);
            sharedPrefrences.setLoginStatus(false);
            sharedPrefrences.setdayStart(false);
            Intent loginIntent = new Intent(context, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(loginIntent);
        }
    }
}
