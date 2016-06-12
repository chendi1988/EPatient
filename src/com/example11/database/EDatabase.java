package com.example11.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class EDatabase extends SQLiteOpenHelper {

	public static final String TABLE_DQ_NAME = "table_diqu";
	public static final String DQ_ID = "id";
	public static final String DQ_NAME = "name";
	public static final String DQ_ADDTIME = "addtime";

	public static final String TABLE_YY_NAME = "table_yiyuan";
	public static final String YY_ID = "id";
	public static final String YY_NAME = "name";
	public static final String YY_ADDTIME = "addtime";

	public static final String TABLE_KS_NAME = "table_keshi";
	public static final String KS_ID = "id";
	public static final String KS_NAME = "name";
	public static final String KS_ADDTIME = "addtime";

	public static final String TABLE_MY_NAME = "table_mingyi";
	public static final String MY_ID = "id";
	public static final String MY_NAME = "name";
	public static final String MY_ADDTIME = "addtime";

	private static final int VERSION = 1;
	private static final String NAME = "e.db";
	private static EDatabase db;

	public static EDatabase getInstance(Context context) {
		if (db == null) {
			db = new EDatabase(context);
		}
		return db;
	}

	public static EDatabase getInstance() {
		return db;
	}

	public EDatabase(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE " + TABLE_DQ_NAME + "(" + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + DQ_ID
				+ " TEXT, " + DQ_NAME + " TEXT, " + DQ_ADDTIME
				+ " TEXT )");

		db.execSQL("CREATE TABLE " + TABLE_YY_NAME + "(" + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + YY_ID
				+ " TEXT, " + YY_NAME + " TEXT, " + YY_ADDTIME
				+ " TEXT )");

		db.execSQL("CREATE TABLE " + TABLE_KS_NAME + "(" + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KS_ID
				+ " TEXT, " + KS_NAME + " TEXT, " + KS_ADDTIME
				+ " TEXT )");

		db.execSQL("CREATE TABLE " + TABLE_MY_NAME + "(" + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + MY_ID
				+ " TEXT, " + MY_NAME + " TEXT, " + MY_ADDTIME
				+ " TEXT )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("db", "old:" + oldVersion + " new:" + newVersion);
		// if (oldVersion == 1) {
		// }
		// oldVersion++;
	}

}
