package com.example11.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by chendi on 2016/6/7.
 */
public class PersonalInfos extends Activity implements View.OnClickListener{

    RelativeLayout personal_real_name_item;
    RelativeLayout personal_sex_item;
    RelativeLayout personal_age_item;
    RelativeLayout personal_height_item;
    RelativeLayout personal_weight_item;
    RelativeLayout personal_phone_item;
    RelativeLayout personal_address_item;

    TextView personal_real_name;
    TextView personal_sex;
    TextView personal_age;
    TextView personal_height;
    TextView personal_weight;
    TextView personal_phone;
    TextView personal_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_infos_layout);

        initView();
    }

    public void initView(){

        personal_real_name_item = (RelativeLayout)findViewById(R.id.personal_real_name_item);
        personal_real_name_item.setOnClickListener(this);
        personal_sex_item = (RelativeLayout)findViewById(R.id.personal_sex_item);
        personal_sex_item.setOnClickListener(this);
        personal_age_item = (RelativeLayout)findViewById(R.id.personal_age_item);
        personal_age_item.setOnClickListener(this);
        personal_height_item = (RelativeLayout)findViewById(R.id.personal_height_item);
        personal_height_item.setOnClickListener(this);
        personal_weight_item = (RelativeLayout)findViewById(R.id.personal_weight_item);
        personal_weight_item.setOnClickListener(this);
        personal_phone_item = (RelativeLayout)findViewById(R.id.personal_phone_item);
        personal_phone_item.setOnClickListener(this);
        personal_address_item = (RelativeLayout)findViewById(R.id.personal_address_item);
        personal_address_item.setOnClickListener(this);

         personal_real_name = (TextView) findViewById(R.id.personal_real_name);
         personal_sex = (TextView) findViewById(R.id.personal_real_name);
         personal_age = (TextView) findViewById(R.id.personal_real_name);
         personal_height = (TextView) findViewById(R.id.personal_real_name);
         personal_weight = (TextView) findViewById(R.id.personal_real_name);
         personal_phone = (TextView) findViewById(R.id.personal_real_name);
         personal_address = (TextView) findViewById(R.id.personal_real_name);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.personal_real_name_item:

                break;

            case R.id.personal_sex_item:

                break;

            case R.id.personal_age_item:

                break;

            case R.id.personal_height_item:

                break;

            case R.id.personal_weight_item:

                break;

            case R.id.personal_phone_item:

                break;
            case R.id.personal_address_item:

                break;
        }

    }
}
