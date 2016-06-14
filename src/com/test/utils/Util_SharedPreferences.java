package com.test.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chendi on 2016/6/2.
 *
 *
 * 描述：SharedPreferences操作工具类
 *
 */
public class Util_SharedPreferences {
    SharedPreferences sp = null;
    Editor ed = null;

    static Util_SharedPreferences U_sh;
    Map<String, String> map;

    public static Util_SharedPreferences getInstance() {
        if (U_sh == null) {
            return new Util_SharedPreferences();
        }
        return U_sh;

    }

    /**
     * 读取文件单个数据，以String类型返回
     *
     * @param c
     * @param XML_Name
     * @param item_key
     * @return 成功返回值 ，失败返回为“”
     */
    public String getItemDataByKey(Context c, String XML_Name, String item_key) {

        sp = c.getSharedPreferences(XML_Name, Activity.MODE_PRIVATE);

        return sp.getString(item_key, "");

    }

    public SharedPreferences getSharedPreferences(Context c,String XML_Name){

        sp = c.getSharedPreferences(XML_Name, Activity.MODE_PRIVATE);

        return sp;

    }

    /**
     * 以String类型写入xml
     *
     * @param c
     * @param File_Name 文件名
     * @param item_key
     * @param item_value
     *            String类型
     * @return 存储结果 成功true，失败false
     */
    public boolean setItemDataByKey(Context c, String File_Name,
                                    String item_key, String item_value) {

        sp = c.getSharedPreferences(File_Name, Activity.MODE_PRIVATE);
        ed = sp.edit();
        ed.putString(item_key, item_value);

        return ed.commit();

    }

    public boolean clearData(Context c, String File_Name) {

        sp = c.getSharedPreferences(File_Name, Activity.MODE_PRIVATE);
        ed = sp.edit();
        ed.clear();

        return ed.commit();

    }

    /**
     * 读取文件多个数据，以String类型返回在map中
     *
     * @param c
     * @param XML_Name
     * @param items_key
     * @return Map
     */
    public Map<String, String> getItemDataByArray(Context c, String XML_Name,
                                                  String[] items_key) {
        map = new HashMap<String, String>();
        sp = c.getSharedPreferences(XML_Name, Activity.MODE_PRIVATE);

        for (int i = 0; i < items_key.length; i++) {
            map.put(items_key[i], sp.getString(items_key[i], ""));
        }

        return map;

    }

    /**
     * 写入多个数据， 所有键值以String类型保存
     *
     * @param c
     * @param XML_Name
     * @param map
     *            需要保存数据的键值对
     * @return 存储结果 成功true，失败false
     */
    public boolean setItemsDataByMap(Context c, String XML_Name,
                                     Map<String, String> map) {

        sp = c.getSharedPreferences(XML_Name, Activity.MODE_PRIVATE);
        ed = sp.edit();
        for (Map.Entry<String, String> e : map.entrySet()) {
            ed.putString(e.getKey(), e.getValue());
        }

        return ed.commit();

    }
}
