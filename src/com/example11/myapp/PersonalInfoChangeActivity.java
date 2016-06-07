package com.example11.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by chendi on 2016/6/7.
 */
public class PersonalInfoChangeActivity extends Activity{

    TextView titlemsg;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_change_item);
    }
}
