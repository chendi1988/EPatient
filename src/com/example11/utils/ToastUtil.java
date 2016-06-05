package com.example11.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chendi on 2016/6/5.
 */
public class ToastUtil {

    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
