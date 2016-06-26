package com.bean.utils;

/**
 * Created by chendi on 2016/6/1.
 */
public class Contant {

    public static final int PAGESIZE = 5; //列表每次请求加载的数据条数

    public static final String COMPANYID = "426";
    public static final String HOST = "http://114.215.133.30:2222";
    public static final String URL_HOST = "http://114.215.133.30:2222/api/user/";

    public static final int FLAG_REGISTER = 0;
    public static final int FLAG_FORGET = 1;

    public static final String URL_ACTION = "http://123.206.62.86:8088/ArticleList.aspx?ID=426";

    //获取验证码
    public static final String ACTION_IDENTIFY = "SENDERIFYCODE";
    public static final String URL_SEND_IFYCODE = URL_HOST + "check_register_info";

    //检测验证码
    public static final String URL_CHECKV_VCODE = URL_HOST + "check_validatecode";

    //注册
    public static final String ACTION_REGISTER = "POSTREGISTEREHT";
    public static final String URL_REGISTER = URL_HOST + "user_register";

    //登录
    public static final String URL_LOGIN = URL_HOST + "request_token";

    //修改或找回密码
    public static final String URL_UPDATE_PWD = URL_HOST + "request_resetPwd";



    //更新用户信息
    public static final String URL_UPDATE_INFO = URL_HOST + "update_user";

    //医生列表加载
    public static final String URL_DOCTOR = URL_HOST + "query_doctor_list";

    //医生详情
    public static final String URL_DOCTOR_INFO = URL_HOST + "get_doctor_info";

    //我的医生列表
    public static final String URL_DOCTOR_MY = URL_HOST + "my_doctor_list";

    //商品列表加载
    public static final String URL_GOODS = URL_HOST + "get_goods";

    //地区
    public static final String URL_DQ = URL_HOST + "get_area";
    //医院
    public static final String URL_YY = URL_HOST + "get_hospital_list";
    //科室
    public static final String URL_KS = URL_HOST + "get_doctordept_list";
    //名医
    public static final String URL_MY = URL_HOST + "get_my";

    //获取订单
    public static final String URL_ORDER = URL_HOST + "getOrderList";


    //用戶信息保存
    public static final String SP_USER = "user.xml";


    public static final int START_PERSONAL_ACTIVITY = 0;

    public static final String URL_LOGIN_OUT = URL_HOST + "exit_login";





    public static String GOODS = "{\"Status\":\"100\",\"result\":[\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"id\":5421,\"photo\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"goodname\":\"会员卡\",\"money\":\"1.00\",\"dec\":\"哈哈哈哈哈哈哈哈\"}\n" +
            "]}";


    public static String DOCTORS = "{\"Status\":\"100\",\"result\":[\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"},\n" +
            "{\"headerURL\":\"UpLoadFile/Company_0426/20160605031638Hydrangeas.jpg\",\"name\":\"会员卡\",\"descption\":\"哈哈哈哈哈哈哈哈\"}\n" +
            "]}";

}
