package com.example11.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by chendi on 2016/6/1.
 *
 * 本地数据库
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_DOCTOR = "doctor_list";
    public static final String DOCTOR_ID = "id";
    public static final String DOCTOR_NAME = "name";
    public static final String DOCTOR_DESCRIPTION = "desction";
    public static final String DOCTOR_URL_HEADER = "headerurl";
    public static final String DOCTOR_URL_CENTER = "center";

    private static final int VERSION = 1;
    private static final String NAME = "database1.db";
    private static DatabaseHelper db;

    public static DatabaseHelper getInstance(Context context) {
        if (db == null) {
            db = new DatabaseHelper(context);
        }
        return db;
    }

    public static DatabaseHelper getInstance() {
        return db;
    }

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // iBeacon信息数据库
        db.execSQL("CREATE TABLE " + TABLE_DOCTOR + "(" + BaseColumns._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DOCTOR_ID
                + " VARCHAR(10), " + DOCTOR_NAME + " TEXT, " + DOCTOR_DESCRIPTION
                + " TEXT, " + DOCTOR_URL_HEADER + " TEXT, " + DOCTOR_URL_CENTER
                + " TEXT " + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         if (oldVersion == 1) {

                 }
                 oldVersion++;
    }
}
