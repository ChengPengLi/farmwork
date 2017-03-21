package com.ky3h.farmwork.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lipengcheng on 2017/3/13.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String NB_NAME = "YANHUANGDB";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "user";

    public DbHelper(Context context, String db_Name) {

        super(context, db_Name, null, DB_VERSION);

    }

    public DbHelper(Context context) {

        super(context, NB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String name = this.getDatabaseName();
        if (NB_NAME.equals(name)) {
            db.execSQL("CREATE TABLE IF NOT EXISTS" + TABLE_NAME + "personid INTEGER primary key autoincrement,id INTEGER,userName,name vercha(20),gender vercha(10),birthDate verchar(20),status INTEGER,identityType verchar(10),idNumber verchar(20),address verchar(40),phone verchar(20),memberChildId verchar(20),memberImage verchar(40),isMedicare verchar(10),JSESSIONID verchar(20),token verchar(20) ,pass INTEGER)  ");
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
