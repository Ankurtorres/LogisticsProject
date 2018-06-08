package com.logimetrix.nj.logistics_project.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.logimetrix.nj.logistics_project.GpsControl;
import com.logimetrix.nj.logistics_project.R;
import com.logimetrix.nj.logistics_project.Request.LoginReq;
import com.logimetrix.nj.logistics_project.constants.URLConstants;
import com.logimetrix.nj.logistics_project.interfacehelper.VolleyResponse;
import com.logimetrix.nj.logistics_project.network.Connection;
import com.logimetrix.nj.logistics_project.network.InternetConnection;
import com.logimetrix.nj.logistics_project.network.Network;
import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    Network network;
    @BindView(R.id.userID)
    EditText userId;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.remember_id)
    CheckBox remember_id;
    ProgressDialog progress;
    SharedPrefrences shrPrfs;
    private GpsControl gpsControl;
    private SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        spotsDialog = new SpotsDialog(this);
        network = Network.getInstance(this);
        gpsControl = new GpsControl(getApplicationContext());
        gpsControl.startGps();
        shrPrfs = SharedPrefrences.getsharedprefInstance(this);
        if (shrPrfs.isRememberMe())
        {
            userId.setText(shrPrfs.getPassword());
        }


    }

    @OnClick(R.id.login)
    public void loginForm() {
        if(((CheckBox)findViewById(R.id.remember_id)).isChecked())
        {
            shrPrfs.setRememberMe(true);
             }else
        {
            shrPrfs.setRememberMe(false);

        }

        if ((InternetConnection.isConnected(LoginActivity.this))) {
            if (!IsEmptyString.IsEmpty(userId)) {
                    shrPrfs.setPassword(userId.getText().toString());
                    doLogin(userId.getText().toString());
            }
            else
                Toast.makeText(this, "Enter the Vehicle Number.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Please check your Internet Connection..", Toast.LENGTH_SHORT).show();
    }


    public void doLogin(String phoneno) {
        spotsDialog.show();
        network.requestWithJsonObject(URLConstants.login, new LoginReq(
                        phoneno
                        , String.valueOf(GpsControl.getLatitude()),
                        String.valueOf(GpsControl.getLongitude()),
                        URLConstants.getImeiNo(LoginActivity.this)
                )
                , loginResponse);
    }

    VolleyResponse loginResponse = new VolleyResponse() {
        @Override
        public void onResponse(JSONObject obj) throws Exception {
            {
                if(obj.getString("error_code").equals("1")) {
                    shrPrfs.setUserDetails(obj.getJSONObject("data").toString());
                    shrPrfs.setLoginStatus(true);
                    if (spotsDialog.isShowing())
                        spotsDialog.dismiss();

                        {
                            Intent i = new Intent(LoginActivity.this, StartJourney.class);
                            startActivity(i);
                        }

                    Log.w("Login Response...", obj.toString());
                    finish();


                    Toast.makeText(LoginActivity.this, obj.getString("response_string").toString(), Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(LoginActivity.this, obj.getString("response_string").toString(), Toast.LENGTH_SHORT).show();
                 spotsDialog.dismiss();

            }


        }
    };
    }