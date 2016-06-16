package com.test.demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import cn.jpush.android.api.JPushInterface;
import com.test.database.EDatabase;
import com.test.database.MapDataHelper;
import com.test.database.MyDatabaseHelper;
import com.test.utils.Contant;
import com.test.utils.HttpGetUtil;
import jpushdemo.ExampleUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chendi on 2016/6/7.
 */
public class LogoActivity extends Activity {

    private static final int sleepTime = 2000;

    Context context;

    boolean loadDqSuc = false;
    boolean loadYySuc = false;
    boolean loadKsSuc = false;
    boolean loadMySuc = false;

    public static boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final View view = View.inflate(this, R.layout.activity_logo_start, null);
        setContentView(view);
        super.onCreate(savedInstanceState);
        context = this;
        new MyDatabaseHelper(context);
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        view.startAnimation(animation);

        init();
        registerMessageReceiver();  // used for receive msg
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init(){
        JPushInterface.init(getApplicationContext());
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            public void run() {
                long start = System.currentTimeMillis();
                //执行加载数据的逻辑

                String Diqus = null;
                for(int i = 0 ;i<3;i++){
                    Diqus = HttpGetUtil.getGetJsonResult(Contant.URL_DQ);
                    if(Diqus!=null){
                        try {
                            JSONObject dqJson = new JSONObject(Diqus);
                            if(dqJson.optString("Status").equals("100")){
                                JSONArray dqArr = dqJson.getJSONArray("result");
                                for(int dq = 0;dq<dqArr.length();dq++){
                                    dqJson = dqArr.getJSONObject(dq);

                                    insert(EDatabase.TABLE_DQ_NAME, dqJson);
                                }
                                loadDqSuc = true;
                                break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                String Yiyuans = null;
                for(int j = 0 ;j<3;j++){
                    Yiyuans = HttpGetUtil.getGetJsonResult(Contant.URL_YY);
                    if(Yiyuans!=null){
                        try {
                            JSONObject YyJson = new JSONObject(Yiyuans);
                            if(YyJson.optString("Status").equals("100")){
                                JSONArray dqArr = YyJson.getJSONArray("result");
                                for(int yy = 0;yy<dqArr.length();yy++){
                                    YyJson = dqArr.getJSONObject(yy);
                                    insert(EDatabase.TABLE_YY_NAME, YyJson);
                                }
                                loadYySuc = true;
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                String Keshis = null;
                for(int k = 0 ;k<3;k++){
                    Keshis = HttpGetUtil.getGetJsonResult(Contant.URL_KS);
                    if(Keshis!=null){
                        try {
                            JSONObject ksJson = new JSONObject(Keshis);
                            if(ksJson.optString("Status").equals("100")){
                                JSONArray dqArr = ksJson.getJSONArray("result");
                                for(int ks = 0;ks<dqArr.length();ks++){
                                    ksJson = dqArr.getJSONObject(ks);
                                    insert(EDatabase.TABLE_KS_NAME, ksJson);
                                }
                                loadKsSuc = true;
                                break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                String Mingyis = null;
                for(int l = 0 ;l<3;l++){
                    Mingyis = HttpGetUtil.getGetJsonResult(Contant.URL_MY);
                    if(Mingyis!=null){
                        try {
                            JSONObject myJson = new JSONObject(Mingyis);
                            if(myJson.optString("Status").equals("100")){
                                JSONArray dqArr = myJson.getJSONArray("result");
                                for(int dq = 0;dq<dqArr.length();dq++){
                                    myJson = dqArr.getJSONObject(dq);
                                    insert(EDatabase.TABLE_MY_NAME, myJson);
                                }
                                loadMySuc = true;
                                break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                long costTime = System.currentTimeMillis() - start;
                //等待sleeptime时长
                if (sleepTime - costTime > 0) {
                    try {
                        Thread.sleep(sleepTime - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //进入主页面
                    startActivity(new Intent(LogoActivity.this, MainActivity.class));
                    finish();
                } else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    startActivity(new Intent(LogoActivity.this, MainActivity.class));
                    finish();
                }
            }
        }).start();
    }

    public void insert(String tableName,JSONObject jsonObject){
        MapDataHelper.getInstance(context).insertToTabtable(tableName,jsonObject.optString("ID"),jsonObject.optString("Name"),jsonObject.optString("AddTime"));
    }
}
