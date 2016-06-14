package com.test.baseactivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by chendi on 2016/5/28.
 * 应用程序Activity的基类
 * <p/>
 * 自定义事件 MobclickAgent.onEvent(context, "event_id");
 */
public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

}
