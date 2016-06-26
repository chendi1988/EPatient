package com.bean.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDataHelper {

    private static MapDataHelper map;

    public static MapDataHelper getInstance(Context context) {
        if (map == null) {
            map = new MapDataHelper(context);
        }
        return map;
    }

    private SQLiteDatabase db;

    public MapDataHelper(Context context) {
        db = EDatabase.getInstance(context).getWritableDatabase();
    }

    public void insertToTabtable(String tableName, String ID, String Name, String AddTime) {

        Cursor cursor = db.query(tableName, null,
                "name=?", new String[]{Name}, null,
                null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            ContentValues values = new ContentValues();

//            String str = cursor.getString(cursor
//                    .getColumnIndex("id"));
//            if (!ID.equals(str)) {
//                values.put("id", ID);
//            }

            String str = cursor.getString(cursor
                    .getColumnIndex("name"));
            if (!Name.equals(str)) {
                values.put("name", Name);
            }

//            str = cursor.getString(cursor
//                    .getColumnIndex("addtime"));
//            if (!AddTime.equals(str)) {
//                values.put("addtime", AddTime);
//            }

            if (values.size() > 0) {
                db.update(tableName, values,
                        "name=?",
                        new String[]{Name});
            }
        } else {

            ContentValues values = new ContentValues();

//            values.put("id", ID);
            values.put("name", Name);
//            values.put("addtime", AddTime);

            db.insert(tableName, null, values);
        }
        cursor.close();

    }


    public List<Map<String, String>> getAllList(String tableName) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map;

        Cursor cursor = db.query(tableName, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            map = new HashMap<String, String>();
//            map.put("ID", cursor.getString(cursor
//                    .getColumnIndex("id")));
            map.put("Name", cursor.getString(cursor
                    .getColumnIndex("name")));
//            map.put("AddTime", cursor.getString(cursor
//                    .getColumnIndex("addtime")));
            list.add(map);
        }
        cursor.close();
        return list;
    }

}
