package com.bean.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.bean.EntityClass.EntityDq;
import com.bean.EntityClass.EntityKs;
import com.bean.EntityClass.EntityMy;
import com.bean.EntityClass.EntityYy;

import java.lang.reflect.Field;

/**
 * Created by chendi on 2016/6/1.
 *
 * 数据库帮助类
 *
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static MyDatabaseHelper db;

    public static final String DATABASE_NAME="database1.db";

    private static final String TAG = "MyDatabaseHelper";

    public static Class mClazz;

    public static MyDatabaseHelper getInstance(Context context,Class clazz) {
        if (db == null) {
            db = new MyDatabaseHelper(context);
        }
        mClazz = clazz;

        return db;
    }

    public static MyDatabaseHelper getInstance(Context context) {
        if (db == null) {
            db = new MyDatabaseHelper(context);
        }
        return db;
    }

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {

        }
        oldVersion++;
    }

    /**
     * 根据指定类名创建表
     */
    private void createTable(SQLiteDatabase db) {
        //根据传过来的class名字创建数据表
        db.execSQL(getCreateTableSql(EntityDq.class));
        db.execSQL(getCreateTableSql(EntityYy.class));
        db.execSQL(getCreateTableSql(EntityKs.class));
        db.execSQL(getCreateTableSql(EntityMy.class));
    }

    /**
     * 得到建表语句
     *
     * @param clazz 指定类
     * @return sql语句
     */
    private String getCreateTableSql(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        //将类名作为表名
        String tabName = DBUtils.getTableName(clazz);
        sb.append("create table ").append(tabName).append(" (uid  INTEGER PRIMARY KEY AUTOINCREMENT, ");
        //得到类中所有属性对象数组
        Field[] fields = clazz.getDeclaredFields();
        for (Field fd : fields) {
            String fieldName = fd.getName();
            String fieldType = fd.getType().getName();
            if (fieldName.equalsIgnoreCase("_id") || fieldName.equalsIgnoreCase("uid")) {
                continue;
            } else {
                sb.append(fieldName).append(DBUtils.getColumnType(fieldType)).append(", ");
            }
        }
        int len = sb.length();
        sb.replace(len - 2, len, ")");
        Log.d(TAG, "the result is " + sb.toString());
        return sb.toString();
    }
}
