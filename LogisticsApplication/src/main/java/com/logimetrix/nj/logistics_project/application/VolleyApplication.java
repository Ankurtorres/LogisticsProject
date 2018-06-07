package com.logimetrix.nj.logistics_project.application;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.logimetrix.nj.logistics_project.R;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by logimetrix on 8/8/16.
 */

@ReportsCrashes(formKey = "",
        mailTo = "naveen@logimetrix.co.in",

        mode = ReportingInteractionMode.SILENT,
        resToastText = R.string.crash_text)
public class VolleyApplication extends android.app.Application {

    private static VolleyApplication mInstance;
    private static RequestQueue mRequest;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private VolleyApplication(Context context){

        this.mCtx=context;
    }

    public VolleyApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());*/
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        mRequest= Volley.newRequestQueue(this);
        mInstance=this;
        ACRA.init(this);
        VolleyApplication.mCtx=getApplicationContext();
    }

    public static Context getAppContext() {
        return VolleyApplication.mCtx;
    }

    public synchronized static VolleyApplication getInstance(){
        return mInstance;
    }

    public RequestQueue getRequestQueue(){

        return mRequest;
    }

    /*public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequest,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }*/

    /*public void initLogger(){
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL             // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                        : LogLevel.NONE)
                .tag("Sterlite")                                         // Specify TAG, default: "X-LOG"// Add a log interceptor
                .build();

        Printer androidPrinter = new AndroidPrinter();             // Printer that print the log using android.util.Log
        Printer consolePrinter = new ConsolePrinter();             // Printer that print the log to console using System.out
        Printer filePrinter = new FilePrinter                      // Printer that print the log to the file system

                .Builder("/sdcard/xlog/")                              // Specify the path to save log file
                .fileNameGenerator(new DateFileNameGenerator())        // Default: ChangelessFileNameGenerator("log")
                .backupStrategy(new NeverBackupStrategy())             // Default: FileSizeBackupStrategy(1024 * 1024)
                .build();

        XLog.init(                                                 // Initialize XLog
                config,                                                // Specify the log configuration, if not specified, will use new LogConfiguration.Builder().build()
                androidPrinter,                                        // Specify printers, if no printer is specified, AndroidPrinter(for Android)/ConsolePrinter(for java) will be used.
                consolePrinter,
                filePrinter);
    }*/





}

