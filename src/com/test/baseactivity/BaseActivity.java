package com.test.baseactivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.test.demo.MainActivity;

/**
 * 应用程序Activity的基类
 */
public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager();
        // 添加Activity到堆栈
        AppManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        // AnimAdapterUtil.anim_translate_back(this);
        // 避免MainActivity在退出时显示动画效果

        if (!AppManager.getAppManager().getClass().equals(MainActivity.class)) {
//            AnimAdapterUtil.anim_translate_back(this);
        }

    }
}
