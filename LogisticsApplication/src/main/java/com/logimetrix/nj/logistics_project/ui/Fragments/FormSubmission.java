package com.logimetrix.nj.logistics_project.ui.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logimetrix.nj.logistics_project.R;
import com.logimetrix.nj.logistics_project.Request.TakData;
import com.logimetrix.nj.logistics_project.constants.URLConstants;
import com.logimetrix.nj.logistics_project.interfacehelper.VolleyResponse;
import com.logimetrix.nj.logistics_project.network.Network;
import com.logimetrix.nj.logistics_project.response.TripDetails;
import com.logimetrix.nj.logistics_project.sharedprefrences.SharedPrefrences;
import com.logimetrix.nj.logistics_project.ui.DashBoard;
import com.logimetrix.nj.logistics_project.ui.EndJourney;
import com.logimetrix.nj.logistics_project.ui.GraphicsUtil;
import com.logimetrix.nj.logistics_project.ui.IsEmptyString;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

/**
 * Created by Ankur_ on 8/28/2017.
 */

public class FormSubmission extends Fragment implements View.OnClickListener {

    private SharedPrefrences sharedPrefrences;

    private EditText remarks;
    private EditText damage_batch, damage_invoice, damage_value;
    private EditText leakage_invoice, leakage_value, leakage_batch;
    private EditText shortage_batch,shortage_invoice,shortage_value,shortage_description;
    private ImageView main_camera, im1, im2, im3;
    private ImageView user_sign, client_sign;
    private ImageView shortage_cross;
    private ImageView damage_camera, damage_cross, damage_image1, damage_image2, damage_image3;
    private ImageView leakage_camera, leakage_cross, leakage_image1, leakage_image2, leakage_image3;
    private TextView phone_id,reporting_date_id,reporting_time_id,unloading_date_id,unloading_time_id,out_date_id,out_time_id,packages_id;
    private TextView shortage, damage, leakage;
    private TextView lRinfo;  private TextView truck_no;
    private Button SaveButton, ClearButton, CancelButton;
    private Button shortage_submit;
    private Button damage_submit;
    private Button leakage_submit;
    private Button job_completed;
    private LinearLayout camera_layout;
    private LinearLayout leakage_cameraLayout;
    private LinearLayout damage_camera_layout;

    private FrameLayout lRframe;

    private SignaturePad signaturePad;
    Bitmap signatureBitmap;
    private static final int CLICK_IMAGE1 = 1,CLICK_IMAGE2 = 2,CLICK_IMAGE3 = 3,CLICK_IMAGE4 = 4,CLICK_IMAGE5 = 5,CLICK_IMAGE6 = 6,CLICK_IMAGE7 = 7,CLICK_IMAGE8 = 8,CLICK_IMAGE9 = 9;
    File profilepic_destination1, profilepic_destination2, profilepic_destination3,profilepic_destination4,profilepic_destination5,profilepic_destination6,profilepic_destination7,profilepic_destination8,profilepic_destination9;
    String StoredPath, StoredPath1;
    String Filepath1 = "", Filepath2 = "", Filepath3 = "", Filepath4 = "", Filepath5 = "",Filepath6 = "",Filepath7 = "",Filepath8 = "",Filepath9 = "",Filepath10 = "",Filepath11 = "";

    String get_remarks;
    String get_dmg_invoice,get_dmg_batch, get_dmg_value;
    String get_leakage_invoice,get_leakage_batch,get_leakage_value;
    String get_shortage_invoice,get_shortage_batch, get_shortage_value,get_shortage_description;

    private CardView card_trip_summary;
    private Network network;
    List<TripDetails> trip_details;
    GraphicsUtil graphicsUtil;
    private SpotsDialog spotsDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_main,container,false);

        graphicsUtil=new GraphicsUtil();

        spotsDialog = new SpotsDialog(getActivity());
        camera_layout = (LinearLayout) view.findViewById(R.id.camera_layout);

        main_camera = (ImageView)view. findViewById(R.id.main_camera);
        main_camera.setOnClickListener(this);
        im1 = (ImageView) view.findViewById(R.id.im1);
        im1.setOnClickListener(this);
        im2 = (ImageView) view.findViewById(R.id.im2);
        im2.setOnClickListener(this);
        im3 = (ImageView)view. findViewById(R.id.im3);
        im3.setOnClickListener(this);
        user_sign = (ImageView) view.findViewById(R.id.user_sign); user_sign.setOnClickListener(this);
        client_sign = (ImageView)view. findViewById(R.id.client_sign); client_sign.setOnClickListener(this);
        damage = (TextView)view. findViewById(R.id.tv_damage); damage.setOnClickListener(this);
        shortage = (TextView)view. findViewById(R.id.tv_shortage); shortage.setOnClickListener(this);
        leakage = (TextView)view. findViewById(R.id.tv_leakage); leakage.setOnClickListener(this);
        remarks = (EditText) view.findViewById(R.id.et_remarks);
        job_completed = (Button)view. findViewById(R.id.complete_btn);
        job_completed.setOnClickListener(this);

        String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/sdcard/LOGISTIC";
        String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        StoredPath = DIRECTORY + pic_name + ".png";

        String DIRECTORY1 = Environment.getExternalStorageDirectory().getPath() + "/sdcard/LOGISTICS";
        String pic_name1 = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        StoredPath1 = DIRECTORY1 + pic_name1 + ".png";
    //    hitgetalldata();

        return  view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        network = Network.getInstance(getActivity());
        sharedPrefrences = SharedPrefrences.getsharedprefInstance(getActivity());
        ((DashBoard)getActivity()).changeHeightLayout(0);
     //   ((DashBoard) getActivity()).setDrawerEnabled(false);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //case R.id.lr_frame:
            //  lr_info();
            // break;
            case R.id.tv_damage:
                damage();
                break;
            case R.id.tv_shortage:
                shortage();
                break;
            case R.id.tv_leakage:
                leakage();
                break;
            case R.id.main_camera:
                main_camera();
                break;
            case R.id.im1:
                Intent photoCaptureIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(photoCaptureIntent1, "Capture Image"), CLICK_IMAGE1);
                break;
            case R.id.im2:
                Intent photoCaptureIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(photoCaptureIntent2, "Capture Image"), CLICK_IMAGE2);
                break;
            case R.id.im3:
                Intent photoCaptureIntent3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(photoCaptureIntent3, "Capture Image"), CLICK_IMAGE3);
                break;
            case R.id.et_remarks:
                //getremarks();
                if (remarks != null) {
                    get_remarks = remarks.getText().toString();
                }
                else {
                    Toast.makeText(getActivity(), "Please add remarks", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.user_sign:
                user();
                break;
            case R.id.client_sign:
                client();
                break;
            case R.id.complete_btn:
                Toast.makeText(getActivity(),"Fill form", Toast.LENGTH_LONG).show();
             checkFormFilled();
             break;
        }

    }

    private void checkFormFilled() {
        if (Filepath1.isEmpty())
        {
            Toast.makeText(getActivity(), "Capture first image", Toast.LENGTH_SHORT).show();
        } else if (Filepath2.isEmpty()) {
            Toast.makeText(getActivity(), "Capture second image", Toast.LENGTH_SHORT).show();
        } else if (Filepath3.isEmpty()) {
            Toast.makeText(getActivity(), "Capture third image", Toast.LENGTH_SHORT).show();
        } else if(IsEmptyString.IsEmpty(remarks))
            Toast.makeText(getActivity(),"Write remarks",Toast.LENGTH_SHORT).show();
        else if (Filepath4.isEmpty()) {
            Toast.makeText(getActivity(), "Please insert user signature", Toast.LENGTH_SHORT).show();
        } else if (Filepath5.isEmpty()) {
            Toast.makeText(getActivity(), "Please insert client signature", Toast.LENGTH_SHORT).show();
        } else {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("Do you really want to complete this job ?");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //dialog.cancel();
                            //sendData();
                            sendImagedata();

                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    private void sendImagedata() {
        new saveimagedata().execute();
        Log.e("image", "date");
    }
    public void main_camera() {
        camera_layout.setVisibility(View.GONE);
        im1.setVisibility(View.VISIBLE);
        im2.setVisibility(View.VISIBLE);
        im3.setVisibility(View.VISIBLE);
    }

    public void damage() {
        damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog DmgShrtgeDialog = new Dialog(getActivity());
                DmgShrtgeDialog.setContentView(R.layout.damage);
                DmgShrtgeDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                DmgShrtgeDialog.show();
                damage_camera_layout = (LinearLayout) DmgShrtgeDialog.findViewById(R.id.cam_layout);
                damage_batch = (EditText) DmgShrtgeDialog.findViewById(R.id.et_damage_batch);
                damage_invoice = (EditText) DmgShrtgeDialog.findViewById(R.id.et_damage_invoice);
                damage_value = (EditText) DmgShrtgeDialog.findViewById(R.id.et_damage_value);
                damage_cross = (ImageView) DmgShrtgeDialog.findViewById(R.id.damage_cross);
                damage_camera = (ImageView) DmgShrtgeDialog.findViewById(R.id.damage_cam);
                damage_image1 = (ImageView) DmgShrtgeDialog.findViewById(R.id.damage_image1);
                damage_image2 = (ImageView) DmgShrtgeDialog.findViewById(R.id.damage_image2);
                damage_image3 = (ImageView) DmgShrtgeDialog.findViewById(R.id.damage_image3);
                damage_submit = (Button) DmgShrtgeDialog.findViewById(R.id.damage_submit_btn);
                damage_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        damage_camera_layout.setVisibility(View.GONE);
                        damage_image1.setVisibility(View.VISIBLE);
                        damage_image2.setVisibility(View.VISIBLE);
                        damage_image3.setVisibility(View.VISIBLE);
                    }
                });
                damage_image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoCaptureIntent4 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(Intent.createChooser(photoCaptureIntent4, "Capture Image"), CLICK_IMAGE4);
                    }
                });
                damage_image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoCaptureIntent5 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(Intent.createChooser(photoCaptureIntent5, "Capture Image"), CLICK_IMAGE5);
                    }
                });
                damage_image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoCaptureIntent6 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(Intent.createChooser(photoCaptureIntent6, "Capture Image"), CLICK_IMAGE6);
                    }
                });
                damage_cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DmgShrtgeDialog.dismiss();
                    }
                });
                damage_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!IsEmptyString.IsEmpty(damage_invoice)) {
                            if (!IsEmptyString.IsEmpty(damage_batch)) {
                                if (!IsEmptyString.IsEmpty(damage_value)) {

                                    if (!Filepath6.isEmpty()) {
                                        if (!Filepath7.isEmpty()) {
                                            if (!Filepath8.isEmpty()) {
                                                DmgShrtgeDialog.dismiss();
                                                Toast.makeText(getActivity(), "Damage form submitted..", Toast.LENGTH_SHORT).show();

                                            }
                                            else
                                                Toast.makeText(getActivity(), "Capture third image", Toast.LENGTH_LONG).show();

                                        }
                                        else
                                            Toast.makeText(getActivity(), "Capture second image", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                        Toast.makeText(getActivity(), "Capture first image", Toast.LENGTH_LONG).show();

                                }
                                else {
                                    Toast.makeText(getActivity(), "Enter value of item", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Enter batch number", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Enter invoice number", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    public void shortage() {
        shortage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog DmgShrtgeDialog = new Dialog(getActivity());
                DmgShrtgeDialog.setContentView(R.layout.shortage);
                DmgShrtgeDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                DmgShrtgeDialog.show();
                shortage_batch = (EditText) DmgShrtgeDialog.findViewById(R.id.et_shortage_batch);
                shortage_invoice = (EditText) DmgShrtgeDialog.findViewById(R.id.et_shortage_invoice);
                shortage_value = (EditText) DmgShrtgeDialog.findViewById(R.id.et_shortage_value);
                shortage_description = (EditText) DmgShrtgeDialog.findViewById(R.id.et_shortage_description);
                shortage_cross = (ImageView) DmgShrtgeDialog.findViewById(R.id.shortage_cross);
                shortage_submit = (Button) DmgShrtgeDialog.findViewById(R.id.shortage_submit_btn);
                shortage_cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DmgShrtgeDialog.dismiss();
                    }
                });
                shortage_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!IsEmptyString.IsEmpty(shortage_invoice)) {
                            if (!IsEmptyString.IsEmpty(shortage_batch)) {
                                if (!IsEmptyString.IsEmpty(shortage_value)) {
                                    if(!IsEmptyString.IsEmpty(shortage_description)) {
                                        DmgShrtgeDialog.dismiss();
                                        Toast.makeText(getActivity(), "Shortage form submitted..", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getActivity(), "Write description", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Enter value of item", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Enter batch number", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Enter invoice number", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    public void leakage() {
        leakage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog DmgShrtgeDialog = new Dialog(getActivity());
                DmgShrtgeDialog.setContentView(R.layout.leakage);
                DmgShrtgeDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                DmgShrtgeDialog.show();
                leakage_batch = (EditText) DmgShrtgeDialog.findViewById(R.id.et_leakage_batch);
                leakage_invoice = (EditText) DmgShrtgeDialog.findViewById(R.id.et_leakage_invoice);
                leakage_value = (EditText) DmgShrtgeDialog.findViewById(R.id.et_leakage_value);
                leakage_cross = (ImageView) DmgShrtgeDialog.findViewById(R.id.leakage_cross);
                leakage_camera = (ImageView) DmgShrtgeDialog.findViewById(R.id.leakage_cam);
                leakage_cameraLayout = (LinearLayout) DmgShrtgeDialog.findViewById(R.id.leakage_cam_layout);
                leakage_image1 = (ImageView) DmgShrtgeDialog.findViewById(R.id.leakage_image1);
                leakage_image2 = (ImageView) DmgShrtgeDialog.findViewById(R.id.leakage_image2);
                leakage_image3 = (ImageView) DmgShrtgeDialog.findViewById(R.id.leakage_image3);
                leakage_submit = (Button) DmgShrtgeDialog.findViewById(R.id.leakage_submit_btn);
                leakage_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leakage_cameraLayout.setVisibility(View.GONE);
                        leakage_image1.setVisibility(View.VISIBLE);
                        leakage_image2.setVisibility(View.VISIBLE);
                        leakage_image3.setVisibility(View.VISIBLE);
                    }
                });
                leakage_image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoCaptureIntent7 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(Intent.createChooser(photoCaptureIntent7, "Capture Image"), CLICK_IMAGE7);
                    }
                });
                leakage_image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoCaptureIntent8 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(Intent.createChooser(photoCaptureIntent8, "Capture Image"), CLICK_IMAGE8);
                    }
                });
                leakage_image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoCaptureIntent9 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(Intent.createChooser(photoCaptureIntent9, "Capture Image"), CLICK_IMAGE9);
                    }
                });
                leakage_cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DmgShrtgeDialog.dismiss();
                    }
                });
                leakage_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!IsEmptyString.IsEmpty(leakage_invoice)) {
                            if (!IsEmptyString.IsEmpty(leakage_batch)) {
                                if (!IsEmptyString.IsEmpty(leakage_value)) {

                                    if (!Filepath9.isEmpty()) {
                                        if (!Filepath10.isEmpty()) {
                                            if (!Filepath11.isEmpty()) {
                                                DmgShrtgeDialog.dismiss();
                                                Toast.makeText(getActivity(), "Leakage form submitted..", Toast.LENGTH_SHORT).show();

                                            }
                                            else
                                                Toast.makeText(getActivity(), "Capture third image", Toast.LENGTH_LONG).show();

                                        }
                                        else
                                            Toast.makeText(getActivity(), "Capture second image", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                        Toast.makeText(getActivity(), "Capture first image", Toast.LENGTH_LONG).show();

                                }
                                else {
                                    Toast.makeText(getActivity(), "Enter value of item", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Enter batch number", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Enter invoice number", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

   /* public void getremarks() {
        if (remarks != null) {
            get_remarks = remarks.getText().toString();
        }

    }*/


    public void user() {
        final Dialog dialog3 = new Dialog(getActivity());
        dialog3.setContentView(R.layout.signature);
        dialog3.show();
        signaturePad = (SignaturePad) dialog3.findViewById(R.id.signaturePad);
        SaveButton = (Button) dialog3.findViewById(R.id.saveButton);
        ClearButton = (Button) dialog3.findViewById(R.id.clearButton);
        CancelButton = (Button) dialog3.findViewById(R.id.cancelButton);
        SaveButton.setEnabled(false);
        ClearButton.setEnabled(false);
        CancelButton.setEnabled(true);
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                SaveButton.setEnabled(true);
                ClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                SaveButton.setEnabled(false);
                ClearButton.setEnabled(false);
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signatureBitmap = signaturePad.getSignatureBitmap();
                //Saving image
                Log.v("tag", "Width: " + v.getWidth());
                Log.v("tag", "Height: " + v.getHeight());

                Bitmap newBitmap = Bitmap.createBitmap(signatureBitmap.getWidth(), signatureBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(newBitmap);
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(signatureBitmap, 0, 0, null);
                try {
                    FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                    // Convert the output file to Image such as .png
                    newBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                    mFileOutStream.flush();
                    mFileOutStream.close();

                } catch (Exception e) {
                    Log.v("log_tag", e.toString());
                }
                dialog3.dismiss();
                Toast.makeText(getActivity(), "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                signaturePad.clear();
                user_sign.setVisibility(View.VISIBLE);
                user_sign.setImageBitmap(signatureBitmap);
                Filepath4 = user_sign.toString();
            }
        });
        ClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            }
        });
    }

    public void client() {
        final Dialog dialog3 = new Dialog(getActivity());
        dialog3.setContentView(R.layout.signature);
        dialog3.show();
        signaturePad = (SignaturePad) dialog3.findViewById(R.id.signaturePad);
        SaveButton = (Button) dialog3.findViewById(R.id.saveButton);
        ClearButton = (Button) dialog3.findViewById(R.id.clearButton);
        CancelButton = (Button) dialog3.findViewById(R.id.cancelButton);
        SaveButton.setEnabled(false);
        ClearButton.setEnabled(false);
        CancelButton.setEnabled(true);
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                SaveButton.setEnabled(true);
                ClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                SaveButton.setEnabled(false);
                ClearButton.setEnabled(false);
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signatureBitmap = signaturePad.getSignatureBitmap();
                //Saving image
                Bitmap newBitmap = Bitmap.createBitmap(signatureBitmap.getWidth(), signatureBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(newBitmap);
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(signatureBitmap, 0, 0, null);

                try {
                    FileOutputStream mFileOutStream = new FileOutputStream(StoredPath1);
                    // Convert the output file to Image such as .png
                    newBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                    mFileOutStream.flush();
                    mFileOutStream.close();

                } catch (Exception e) {
                    Log.v("log_tag", e.toString());
                }
                //save(view, StoredPath1);
                dialog3.dismiss();
                Toast.makeText(getActivity(), "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
                client_sign.setVisibility(View.VISIBLE);
                client_sign.setImageBitmap(signatureBitmap);
                Filepath5 = client_sign.toString();
            }
        });
        ClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CLICK_IMAGE1) {
                try {
                    Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                    profilepic_destination1 = saveImage();
                    FileOutputStream fo;
                    try {
                        profilepic_destination1.createNewFile();
                        fo = new FileOutputStream(profilepic_destination1);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Bitmap bitmap = graphicsUtil.getRoundedShapeMyProfile(bitmap1, bitmap1.getWidth());//updated by ankit
                    Bitmap bitmapblur = graphicsUtil.blur(bitmap1, getActivity());
                    Drawable d = new BitmapDrawable(getResources(), bitmapblur);
                    im1.setMaxHeight(bitmap.getHeight());
                    im1.setMaxWidth(bitmap.getHeight());
                    im1.setImageBitmap(graphicsUtil.getCircleBitmap(bitmap, 16));

                    Filepath1 = im1.toString();
                } catch (Exception e) {
                }
            } else if (requestCode == CLICK_IMAGE2) {
                try {
                    Bitmap bitmap2 = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                    profilepic_destination2 = saveImage();
                    FileOutputStream fo;
                    try {
                        profilepic_destination2.createNewFile();
                        fo = new FileOutputStream(profilepic_destination2);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = graphicsUtil.getRoundedShapeMyProfile(bitmap2, bitmap2.getWidth());//updated by ankit
                    Bitmap bitmapblur = graphicsUtil.blur(bitmap2, getActivity());
                    Drawable d = new BitmapDrawable(getResources(), bitmapblur);
                    im2.setMaxHeight(bitmap.getHeight());
                    im2.setMaxWidth(bitmap.getHeight());
                    im2.setImageBitmap(graphicsUtil.getCircleBitmap(bitmap, 16));

                    Filepath2 = im2.toString();
                } catch (Exception e) {
                }
            } else if (requestCode == CLICK_IMAGE3) {
                try {
                    Bitmap bitmap3 = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                    profilepic_destination3 = saveImage();
                    FileOutputStream fo;
                    try {
                        profilepic_destination3.createNewFile();
                        fo = new FileOutputStream(profilepic_destination3);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = graphicsUtil.getRoundedShapeMyProfile(bitmap3, bitmap3.getWidth());//updated by ankit
                    Bitmap bitmapblur = graphicsUtil.blur(bitmap3, getActivity());
                    Drawable d = new BitmapDrawable(getResources(), bitmapblur);
                    im3.setMaxHeight(bitmap.getHeight());
                    im3.setMaxWidth(bitmap.getHeight());
                    im3.setImageBitmap(graphicsUtil.getCircleBitmap(bitmap, 16));

                    Filepath3 = im3.toString();
                } catch (Exception e) {
                }
            }
            else if (requestCode == CLICK_IMAGE4) {
                try {
                    Bitmap bitmap4 = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap4.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                    profilepic_destination4 = saveImage();
                    FileOutputStream fo;
                    try {
                        profilepic_destination4.createNewFile();
                        fo = new FileOutputStream(profilepic_destination4);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = graphicsUtil.getRoundedShapeMyProfile(bitmap4, bitmap4.getWidth());//updated by ankit
                    Bitmap bitmapblur = graphicsUtil.blur(bitmap4, getActivity());
                    Drawable d = new BitmapDrawable(getResources(), bitmapblur);
                    damage_image1.setMaxHeight(bitmap.getHeight());
                   damage_image1.setMaxWidth(bitmap.getHeight());
                    damage_image1.setImageBitmap(graphicsUtil.getCircleBitmap(bitmap, 16));

                    Filepath6 = damage_image1.toString();
                } catch (Exception e) {
                }
            }
            else if (requestCode == CLICK_IMAGE5) {
                try {
                    Bitmap bitmap5 = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap5.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                    profilepic_destination5 = saveImage();
                    FileOutputStream fo;
                    try {
                        profilepic_destination5.createNewFile();
                        fo = new FileOutputStream(profilepic_destination5);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = graphicsUtil.getRoundedShapeMyProfile(bitmap5, bitmap5.getWidth());//updated by ankit
                    Bitmap bitmapblur = graphicsUtil.blur(bitmap5, getActivity());
                    Drawable d = new BitmapDrawable(getResources(), bitmapblur);
                    damage_image2.setMaxHeight(bitmap.getHeight());
                    damage_image2.setMaxWidth(bitmap.getHeight());
                    damage_image2.setImageBitmap(graphicsUtil.getCircleBitmap(bitmap, 16));
                    Filepath7 = damage_image2.toString();
                } catch (Exception e) {
                }
            }
            else if (requestCode == CLICK_IMAGE6) {
                try {
                    Bitmap bitmap6 = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap6.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                    profilepic_destination6 = saveImage();
                    FileOutputStream fo;
                    try {
                        profilepic_destination6.createNewFile();
                        fo = new FileOutputStream(profilepic_destination6);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = graphicsUtil.getRoundedShapeMyProfile(bitmap6, bitmap6.getWidth());//updated by ankit
                    Bitmap bitmapblur = graphicsUtil.blur(bitmap6, getActivity());
                    Drawable d = new BitmapDrawable(getResources(), bitmapblur);
                    damage_image3.setMaxHeight(bitmap.getHeight());
                    damage_image3.setMaxWidth(bitmap.getHeight());
                    damage_image3.setImageBitmap(graphicsUtil.getCircleBitmap(bitmap, 16));
                    Filepath8 = damage_image3.toString();
                } catch (Exception e) {
                }
            }
            else if (requestCode == CLICK_IMAGE7) {
                try {
                    Bitmap bitmap7 = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap7.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                    profilepic_destination7 = saveImage();
                    FileOutputStream fo;
                    try {
                        profilepic_destination7.createNewFile();
                        fo = new FileOutputStream(profilepic_destination7);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = graphicsUtil.getRoundedShapeMyProfile(bitmap7, bitmap7.getWidth());//updated by ankit
                    Bitmap bitmapblur = graphicsUtil.blur(bitmap7, getActivity());
                    Drawable d = new BitmapDrawable(getResources(), bitmapblur);
                    leakage_image1.setMaxHeight(bitmap.getHeight());
                    leakage_image1.setMaxWidth(bitmap.getHeight());
                    leakage_image1.setImageBitmap(graphicsUtil.getCircleBitmap(bitmap, 16));
                    Filepath9 = leakage_image1.toString();
                } catch (Exception e) {
                }
            }
            else if (requestCode == CLICK_IMAGE8) {
                try {
                    Bitmap bitmap8 = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap8.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                    profilepic_destination8 = saveImage();
                    FileOutputStream fo;
                    try {
                        profilepic_destination8.createNewFile();
                        fo = new FileOutputStream(profilepic_destination8);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = graphicsUtil.getRoundedShapeMyProfile(bitmap8, bitmap8.getWidth());//updated by ankit
                    Bitmap bitmapblur = graphicsUtil.blur(bitmap8, getActivity());
                    Drawable d = new BitmapDrawable(getResources(), bitmapblur);
                    leakage_image2.setMaxHeight(bitmap.getHeight());
                    leakage_image2.setMaxWidth(bitmap.getHeight());
                    leakage_image2.setImageBitmap(graphicsUtil.getCircleBitmap(bitmap, 16));
                    Filepath10 = leakage_image2.toString();
                } catch (Exception e) {
                }
            }
            else if (requestCode == CLICK_IMAGE9) {
                try {
                    Bitmap bitmap9 = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap9.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                    profilepic_destination9 = saveImage();
                    FileOutputStream fo;
                    try {
                        profilepic_destination9.createNewFile();
                        fo = new FileOutputStream(profilepic_destination9);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = graphicsUtil.getRoundedShapeMyProfile(bitmap9, bitmap9.getWidth());//updated by ankit
                    Bitmap bitmapblur = graphicsUtil.blur(bitmap9, getActivity());
                    Drawable d = new BitmapDrawable(getResources(), bitmapblur);
                    leakage_image3.setMaxHeight(bitmap.getHeight());
                    leakage_image3.setMaxWidth(bitmap.getHeight());
                    leakage_image3.setImageBitmap(graphicsUtil.getCircleBitmap(bitmap, 16));
                    Filepath11 = leakage_image3.toString();
                } catch (Exception e) {
                }
            }
        }
    }

    public File saveImage() {
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/sdcard/LOGISTIC");
        if (!folder.exists())
            folder.mkdirs();
        String img = "img" + System.currentTimeMillis() + ".png";
        File image = new File(folder, img);
        return image;
    }

    class saveimagedata extends AsyncTask<Void, Void, String> {
        public saveimagedata() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spotsDialog.show();
            spotsDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.e("doing","bak");
            return taskdata();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                listner.onResponse(new JSONObject(s));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String taskdata() {
            Log.e("taskdata", "insertimagefunction");
            String result = "";
            HttpClient client = new DefaultHttpClient();
            org.apache.http.client.methods.HttpPost post = new org.apache.http.client.methods.HttpPost(URLConstants.multipart_test_data);
            org.apache.http.entity.mime.MultipartEntity reqEntity = new org.apache.http.entity.mime.MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            Log.e("test", "reg url" + URLConstants.multipart_test_data);

/*
            try {
                reqEntity.addPart("access_token", new StringBody(sharedPrefrences.getUserDetails().getAccess_token()));

                reqEntity.addPart("job_id", new StringBody(String.valueOf(sharedPrefrences.getJobId())));

                    reqEntity.addPart("LoginId", new StringBody(String.valueOf(sharedPrefrences.getPassword())));


                    
                           ContentBody file = new FileBody(new File(String.valueOf(StoredPath1)), "image/jpg");
                           reqEntity.addPart("signature2", file);

                post.setEntity(reqEntity);
                Log.e("params", reqEntity.toString() + "");
                HttpResponse response = client.execute(post);
                HttpEntity resEntity = response.getEntity();
                if (reqEntity != null) {
                    result = EntityUtils.toString(resEntity);
                    Log.e("result", "of image" + result);
                }





            }catch (Exception e)
            {
                e.printStackTrace();
            }
*/





            try {

                reqEntity.addPart("access_token", new StringBody(sharedPrefrences.getUserDetails().getAccess_token()));

                reqEntity.addPart("job_id", new StringBody(String.valueOf(sharedPrefrences.getJobId())));

                reqEntity.addPart("LoginId", new StringBody(String.valueOf(sharedPrefrences.getPassword())));



                reqEntity.addPart("remarks",new StringBody(String.valueOf(remarks.getText().toString())));

                if(damage_invoice!=null) {
                reqEntity.addPart("damage_invoice_number",new StringBody(String.valueOf(damage_invoice.getText().toString())));}
                if(damage_batch!=null){
                reqEntity.addPart("damage_batch_number",new StringBody(String.valueOf(damage_batch.getText().toString())));}
                if(damage_value!=null){
                reqEntity.addPart("damage_value",new StringBody(String.valueOf(damage_value.getText().toString())));}
                if(shortage_invoice!=null){
                reqEntity.addPart("shortage_invoice_number",new StringBody(String.valueOf(shortage_invoice.getText().toString())));}
                if(shortage_batch!=null){
                reqEntity.addPart("shortage_batch_number",new StringBody(String.valueOf(shortage_batch.getText().toString())));}
                if(shortage_value!=null){
                reqEntity.addPart("shortage_value",new StringBody((String.valueOf(shortage_value.getText().toString()))));}
                if(shortage_description!=null){
                reqEntity.addPart("shortage_description",new StringBody(String.valueOf(shortage_description.getText().toString())));}
                if(leakage_invoice!=null){
                reqEntity.addPart("leakage_invoice_number",new StringBody(String.valueOf(leakage_invoice.getText().toString())));}
                if(leakage_batch!=null){
                reqEntity.addPart("leakage_batch_number",new StringBody(String.valueOf(leakage_batch.getText().toString())));}
                if(leakage_value!=null){
                reqEntity.addPart("leakage_value",new StringBody(String.valueOf(leakage_value.getText().toString())));}



                // reqEntity.addPart("damage1",new StringBody(""));
                //  reqEntity.addPart("shortage2",new StringBody(""));


                if (profilepic_destination1 != null) {
                    if (new File(String.valueOf(profilepic_destination1)).exists()) {
                        ContentBody file = new FileBody(new File(String.valueOf(profilepic_destination1)), "image/jpg");
                        reqEntity.addPart("photograph1", file);
                    } else {
                        ContentBody pic = new StringBody("");
                        reqEntity.addPart("photograph1", pic);
                    }
                }
                if (profilepic_destination2 != null) {
                    if (new File(String.valueOf(profilepic_destination2)).exists()) {
                        ContentBody file = new FileBody(new File(String.valueOf(profilepic_destination2)), "image/jpg");
                        reqEntity.addPart("photograph2", file);
                    } else {
                        ContentBody pic = new StringBody("");
                        reqEntity.addPart("photograph2", pic);
                    }
                }

                if (profilepic_destination3 != null) {
                    if (new File(String.valueOf(profilepic_destination3)).exists()) {
                        ContentBody file = new FileBody(new File(String.valueOf(profilepic_destination3)), "image/jpg");
                        reqEntity.addPart("photograph3", file);
                    } else {
                        ContentBody pic = new StringBody("");
                        reqEntity.addPart("photograph3", pic);
                    }
                }
                if(profilepic_destination4!=null){
                    if(new File(String.valueOf(profilepic_destination4)).exists()){
                        ContentBody file=new FileBody(new File(String.valueOf(profilepic_destination4)),"image/jpg");
                        reqEntity.addPart("damage_photo_1",file);
                    }else {
                        ContentBody pic=new StringBody("");
                        reqEntity.addPart("damage_photo_1",pic);
                    }
                }

                if(profilepic_destination5!=null){
                    if(new File(String.valueOf(profilepic_destination5)).exists()){
                        ContentBody file=new FileBody(new File(String.valueOf(profilepic_destination5)),"image/jpg");
                        reqEntity.addPart("damage_photo_2",file);
                    }else {
                        ContentBody pic=new StringBody("");
                        reqEntity.addPart("damage_photo_2",pic);
                    }
                }
                if(profilepic_destination6!=null){
                    if(new File(String.valueOf(profilepic_destination6)).exists()){
                        ContentBody file=new FileBody(new File(String.valueOf(profilepic_destination6)),"image/jpg");
                        reqEntity.addPart("damage_photo_3",file);
                    }else {
                        ContentBody pic=new StringBody("");
                        reqEntity.addPart("damage_photo_3",pic);
                    }
                }
                if(profilepic_destination7!=null){
                    if(new File(String.valueOf(profilepic_destination7)).exists()){
                        ContentBody file=new FileBody(new File(String.valueOf(profilepic_destination7)),"image/jpg");
                        reqEntity.addPart("leakage_photo_1",file);
                    }else {
                        ContentBody pic=new StringBody("");
                        reqEntity.addPart("leakage_photo_1",pic);
                    }
                }
                if(profilepic_destination8!=null){
                    if(new File(String.valueOf(profilepic_destination8)).exists()){
                        ContentBody file=new FileBody(new File(String.valueOf(profilepic_destination8)),"image/jpg");
                        reqEntity.addPart("leakage_photo_2",file);
                    }else {
                        ContentBody pic=new StringBody("");
                        reqEntity.addPart("leakage_photo_2",pic);
                    }
                }
                if(profilepic_destination9!=null){
                    if(new File(String.valueOf(profilepic_destination9)).exists()){
                        ContentBody file=new FileBody(new File(String.valueOf(profilepic_destination9)),"image/jpg");
                        reqEntity.addPart("leakage_photo_3",file);
                    }else {
                        ContentBody pic=new StringBody("");
                        reqEntity.addPart("leakage_photo_3",pic);
                    }
                }
                if (StoredPath != null) {
                    if (new File(String.valueOf(StoredPath)).exists()) {
                        ContentBody file = new FileBody(new File(String.valueOf(StoredPath)), "image/jpg");
                        reqEntity.addPart("signature1", file);
                    } else {
                        ContentBody pic = new StringBody("");
                        reqEntity.addPart("signature1", pic);
                    }
                }
                if (StoredPath1 != null) {
                    if (new File(String.valueOf(StoredPath1)).exists()) {
                        ContentBody file = new FileBody(new File(String.valueOf(StoredPath1)), "image/jpg");
                        reqEntity.addPart("signature2", file);
                    } else {
                        ContentBody pic = new StringBody("");
                        reqEntity.addPart("signature2", pic);
                    }
                }
                post.setEntity(reqEntity);
                Log.e("params", reqEntity.toString() + "");
                HttpResponse response = client.execute(post);
                HttpEntity resEntity = response.getEntity();
                if (reqEntity != null) {
                    result = EntityUtils.toString(resEntity);
                    Log.e("result", "of image" + result);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    VolleyResponse listner = new VolleyResponse() {
        @Override
        public void onResponse(JSONObject obj) throws Exception {
            /*Log.e("Response", obj.toString());
            if (obj.getString("error_code").equals("200")) {
                // alertDialog.dismiss();*/

            if(spotsDialog.isShowing())
            {
                spotsDialog.dismiss();
            }
                Toast.makeText(getActivity(),"Form submitted successfully", Toast.LENGTH_LONG).show();
                //   ((JobslistActivity)getActivity()).fragmentInit(Jobslistfrag.class);
                Intent il=new Intent(getActivity(),EndJourney.class);
                startActivity(il);
//               getActivity().finish();
            }


    };

}
