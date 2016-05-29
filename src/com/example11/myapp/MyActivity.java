package com.example11.myapp;

import android.os.Bundle;
import android.view.Window;
import com.example11.base.BackHandledFragment;
import com.example11.base.BackHandledInterface;
import com.example11.baseactivity.BaseFragmentActivity;

public class MyActivity extends BaseFragmentActivity implements BackHandledInterface {

    private BackHandledFragment mBackHandedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed(); //退出
            } else {
                getSupportFragmentManager().popBackStack(); //fragment 出栈
            }
        }
    }
}