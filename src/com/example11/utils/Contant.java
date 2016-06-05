package com.example11.utils;

/**
 * Created by chendi on 2016/6/1.
 */
public class Contant {

    public static final String COMPANYID = "426";
    public static final String URL_HOST = "http://123.206.62.86:8088/api.asmx/";

    public static final int FLAG_REGISTER = 0;
    public static final int FLAG_FORGET = 1;

    //获取验证码
    public static final String ACTION_IDENTIFY = "SENDERIFYCODE";
    public static final String URL_SEND_IFYCODE = URL_HOST + "send_verifycode";

    //注册
    public static final String ACTION_REGISTER = "POSTREGISTEREHT";
    public static final String URL_REGISTER = URL_HOST + "do_register";

    public static final String URL_LOGIN = URL_HOST + "do_login";


    public static final String SP_USER = "user";




}
