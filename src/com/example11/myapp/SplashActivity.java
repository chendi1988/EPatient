package com.example11.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;

public class SplashActivity extends Activity {
    private ImageView image;
    private Handler handler = new Handler();
    private boolean flag = true;     //判断用户是否是第一次安装,第一次启动,以后不在启动

    //SharedPreferences,Editor 第一次安装完成后进行保存，以后将不再启动Splash界面
    private SharedPreferences sp;
    private Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);

        //实例 SharedPreferences
        sp = getPreferences(Context.MODE_APPEND);
        editor = sp.edit();

        //启动页面启动后，2秒后跳转到下一界面
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                boolean isFirst = sp.getBoolean("isFirst", flag);//得到是否是第一次的状态
                if (isFirst) //是第一次
                {
                    Intent intent = new Intent(SplashActivity.this, KaKaLauncherActivity.class);
                    editor.putBoolean("isFirst", false); //启动之后将标识设置为false,以后不在启动SplashActivity界面
                    editor.commit();
                    startActivity(intent);
                    finish();
                } else if (!isFirst) //不是第一次
                {
//                    Intent intent = new Intent(SplashActivity.this, MyActivity.class);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}
