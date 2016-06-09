package com.example11.myapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

public class RegisteActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(getSupportFragmentManager().getBackStackEntryCount()==0){
                finish();
                return true;
            }
        }
        return true;
    }
}
