package com.example11.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import com.example11.database.EDatabase;
import com.example11.database.MapDataHelper;
import com.example11.database.MyDatabaseHelper;
import com.example11.utils.Contant;
import com.example11.utils.HttpGetUtil;
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
