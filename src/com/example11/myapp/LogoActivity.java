package com.example11.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by chendi on 2016/6/7.
 */
public class LogoActivity extends Activity {

    private static final int sleepTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final View view = View.inflate(this, R.layout.activity_logo_start, null);
        setContentView(view);
        super.onCreate(savedInstanceState);

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
                    startActivity(new Intent(LogoActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }).start();
    }
}
