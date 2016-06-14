package com.test.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chendi on 2016/6/4.
 */
public class CheckFormatUtil {

    /**
     * 验证是否为手机号码
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNum(String phoneNum){

        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNum);
        return m.matches();

    }


    public static void showToast(Context context, Map<String,Object> map){

        if(map.get("status").equals("100")){

            Toast.makeText(context,"验证码已发生，请及时查收",Toast.LENGTH_SHORT).show();

        }else{

        }

    }

}
