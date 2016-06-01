package com.example11.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by chendi on 2016/6/1.
 */
public class MapDataHelper {

    private static MapDataHelper map;

    public static MapDataHelper getInstance() {
        if (map == null) {
            map = new MapDataHelper();
        }
        return map;
    }

    private SQLiteDatabase db;

    public MapDataHelper() {
        db = DatabaseHelper.getInstance().getWritableDatabase();
    }
}
