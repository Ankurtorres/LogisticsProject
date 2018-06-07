package com.logimetrix.nj.logistics_project.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
public class Dialogs extends AppCompatActivity{

    Dialog dia;
    public static final String selectDialog="dialogKey";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingDialog(this);
    }

    private void settingDialog(final Context con){
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        dia= new AlertDialog.Builder(con)
                .setTitle("Location Services Disabled")
                .setMessage("Please enable location services.")
                .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        con.startActivity(new Intent(action));
                        dialog.dismiss();Dialogs.this.finish();
                    }
                })

                .show();
        dia.setCancelable(false);
        dia.setCanceledOnTouchOutside(false);
    }
    @Override
    public void onBackPressed() {
        dia.show();

    }
}
