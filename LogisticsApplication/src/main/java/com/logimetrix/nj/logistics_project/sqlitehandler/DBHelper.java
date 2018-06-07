package com.logimetrix.nj.logistics_project.sqlitehandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.logimetrix.nj.logistics_project.Request.LocationTable;

/**
 * Created by logimetrix on 1/8/16.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static String DATABASE_NAME= Environment.getExternalStorageDirectory()+"/logisticsapp.db";
    private static final int DATABASE_VERSION=1;


    private Dao<LocationTable,Long> getLocation;
    private static DBHelper databaseHelper;


    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, LocationTable.class);

        }catch(Exception e){
            e.printStackTrace();
            Log.e(DBHelper.class.getName(),"Unable to create database",e);
        }
    }
    /*public static DBHelper getInstance(Context context) {
        if (databaseHelper == null) {
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                DATABASE_NAME = android.os.Environment.getExternalStorageDirectory() + "/Dairy/dairy.db";
            }
            databaseHelper = new DBHelper(context);
        }
        return databaseHelper;
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

            /*
            TableUtils.dropTable(connectionSource,StateWithProducts.class,false);
            TableUtils.dropTable(connectionSource, OrderDetailPojo.class,false);*/
            try {
                  onCreate(database, connectionSource);
                }catch (Exception e)
                {
                    e.printStackTrace();}
                    }


    public  Dao<LocationTable,Long> getlocation() throws Exception {//Dao-It is a object/interface, which
        // is used to access data from database of data storage.
        //// Create the getDao methods of all database tables to access those from android code.
        // Insert, delete, read, update everything will be happened through DAOs

        //http://www.androidbegin.com/tutorial/android-ormlite-with-sqlite-database-tutorial/
        //ORMLITE tutorials
        if(getLocation==null)
            getLocation=getDao(LocationTable.class);
        return getLocation;
    }




    public void saveLocation(LocationTable location) throws Exception {
        getlocation().create(location);
    }
}
