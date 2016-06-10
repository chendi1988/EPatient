package com.example11.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import com.example11.EntityClass.EntityDq;
import com.example11.EntityClass.EntityKs;
import com.example11.EntityClass.EntityMy;
import com.example11.EntityClass.EntityYy;
import com.example11.adapters.AnimAdapterUtil;
import com.example11.database.DBManager;
import com.example11.database.MyDatabaseHelper;
import com.example11.utils.Contant;
import com.example11.utils.HttpGetUtil;
import com.example11.utils.ToastUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chendi on 2016/6/7.
 */
public class LogoActivity extends Activity {

    private static final int sleepTime = 500;

    Context context;

    boolean loadDqErr = false;
    int numDQ = 0;
    boolean loadYyErr = false;
    int numYY = 0;
    boolean loadKsErr = false;
    int numKS = 0;
    boolean loadMyErr = false;
    int numMY = 0;

    long start = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final View view = View.inflate(this, R.layout.activity_logo_start, null);
        setContentView(view);
        super.onCreate(savedInstanceState);

        context = this;
        MyDatabaseHelper.getInstance(this,EntityDq.class);
        MyDatabaseHelper.getInstance(this,EntityYy.class);
        MyDatabaseHelper.getInstance(this,EntityKs.class);
        MyDatabaseHelper.getInstance(this,EntityMy.class);

        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(500);
        view.startAnimation(animation);

        start = System.currentTimeMillis();
        new GetDQsTask().execute();
        new GetYYsTask().execute();
        new GetKSsTask().execute();
        new GetMYsTask().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    class GetDQsTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            return HttpGetUtil.getGetJsonResult(Contant.URL_DQ);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null){
                try {
                    JSONObject dqJson = new JSONObject(s);
                    if(dqJson.optString("Status").equals("100")){
                        EntityDq entityDq = null;
                        JSONArray dqArr = dqJson.getJSONArray("result");
                        for(int i = 0;i<dqArr.length();i++){
                            entityDq = new EntityDq();
                            dqJson = dqArr.getJSONObject(i);
                            entityDq.setID(dqJson.getString("ID"));
                            entityDq.setName(dqJson.getString("Name"));
                            entityDq.setAddTime(dqJson.getString("AddTime"));
                            DBManager.getInstance(context,EntityDq.class).insert(entityDq);
                        }

                    }
                    loadDqErr = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                loadDqErr = true;
            }

            numDQ++;
            tryStartActivity();

        }
    }

    class GetYYsTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            return HttpGetUtil.getGetJsonResult(Contant.URL_YY);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null){
                try {
                    JSONObject dqJson = new JSONObject(s);
                    if(dqJson.optString("Status").equals("100")){
                        EntityYy entityYy = null;
                        JSONArray dqArr = dqJson.getJSONArray("result");
                        for(int i = 0;i<dqArr.length();i++){
                            entityYy = new EntityYy();
                            dqJson = dqArr.getJSONObject(i);
                            entityYy.setID(dqJson.getString("ID"));
                            entityYy.setName(dqJson.getString("Name"));
                            entityYy.setAddTime(dqJson.getString("AddTime"));
                            DBManager.getInstance(context,EntityYy.class).insert(entityYy);
                        }

                    }
                    loadYyErr = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                loadYyErr = true;
            }

            numYY++;
            tryStartActivity();
        }
    }

    class GetKSsTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            return HttpGetUtil.getGetJsonResult(Contant.URL_KS);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null){
                try {
                    JSONObject dqJson = new JSONObject(s);
                    if(dqJson.optString("Status").equals("100")){
                        EntityKs entityKs = null;
                        JSONArray dqArr = dqJson.getJSONArray("result");
                        for(int i = 0;i<dqArr.length();i++){
                            entityKs = new EntityKs();
                            dqJson = dqArr.getJSONObject(i);
                            entityKs.setID(dqJson.getString("ID"));
                            entityKs.setName(dqJson.getString("Name"));
                            entityKs.setAddTime(dqJson.getString("AddTime"));
                            DBManager.getInstance(context,EntityKs.class).insert(entityKs);
                        }

                    }
                    loadKsErr = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                loadKsErr = true;
            }

            numKS++;
            tryStartActivity();
        }
    }

    class GetMYsTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            return HttpGetUtil.getGetJsonResult(Contant.URL_MY);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null){
                try {
                    JSONObject dqJson = new JSONObject(s);
                    if(dqJson.optString("Status").equals("100")){
                        EntityMy entityMy = null;
                        JSONArray dqArr = dqJson.getJSONArray("result");
                        for(int i = 0;i<dqArr.length();i++){
                            entityMy = new EntityMy();
                            dqJson = dqArr.getJSONObject(i);
                            entityMy.setID(dqJson.getString("ID"));
                            entityMy.setName(dqJson.getString("Name"));
                            entityMy.setAddTime(dqJson.getString("AddTime"));
                            DBManager.getInstance(context,EntityMy.class).insert(entityMy);
                        }

                    }
                    loadMyErr = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                loadMyErr = true;
            }

            numMY++;
            tryStartActivity();
        }
    }

    public void tryStartActivity(){

        if((loadDqErr && numDQ < 3) || (loadYyErr && numYY < 3) || (loadKsErr && numKS < 3) || (loadMyErr && numMY < 3)){
            if(loadDqErr && numDQ < 3){
                new GetDQsTask().execute();
            } else if(loadYyErr && numYY < 3){
                new GetYYsTask().execute();
            } else if(loadKsErr && numKS < 3){
                new GetKSsTask().execute();
            } else if(loadMyErr && numMY < 3){
                new GetMYsTask().execute();
            }else{
                ToastUtil.showToast(context,"缓存数据失败");
            }

        }else{
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
                AnimAdapterUtil.anim_translate_next(context);
            } else {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
                startActivity(new Intent(LogoActivity.this, MainActivity.class));
                finish();
                AnimAdapterUtil.anim_translate_next(context);
            }
        }
    }
}
