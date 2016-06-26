package com.bean.yihuanton;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import com.bean.adapters.AnimAdapterUtil;
import com.bean.fragments.Fragment_Verify_Verify;
import com.bean.fragments.Fragment_Verify_pwd;

public class RegisteActivity extends FragmentActivity implements Fragment_Verify_pwd.GetType,Fragment_Verify_Verify.VerifyGetType {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

        context = this;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(getSupportFragmentManager().getBackStackEntryCount()==0){
                finish();
                AnimAdapterUtil.anim_translate_back(context);
                return true;
            }
        }
        return true;
    }


    @Override
    public int getType() {
        return getIntent().getIntExtra("type",0);
    }

    @Override
    public int getVType() {
        return getIntent().getIntExtra("type",0);
    }
}
