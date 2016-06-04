package com.example11.utils;

/**
 * Created by chendi on 2016/6/1.
 */
public class Contant {

    public static final int COMPANYID = 426;

    public static final String URL_HOST = "http://123.206.62.86:8088/";

    //获取验证码
    public static final String ACTION_IDENTIFY = "SENDERIFYCODE";
    public static final String URL_SEND_IFYCODE = URL_HOST + "AjaxHandler.ashx";

    //注册
    public static final String ACTION_REGISTER = "POSTREGISTEREHT";
    public static final String URL_REGISTER = URL_HOST + "AjaxHandler.ashx";

    public static final String url_login = URL_HOST + "api.asmx/do_login";

}
