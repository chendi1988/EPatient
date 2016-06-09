package com.example11.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    Context context;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_infos_layout);
        context = this;
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

                intent = new Intent();
                intent.putExtra("item",0);
                intent.setClass(context,PersonalInfoChangeActivity.class);
                startActivityForResult(intent,0);

                break;

            case R.id.personal_sex_item:

                break;

            case R.id.personal_age_item:

                intent = new Intent();
                intent.putExtra("item",2);
                intent.setClass(context,PersonalInfoChangeActivity.class);
                startActivityForResult(intent,2);

                break;

            case R.id.personal_height_item:

                intent = new Intent();
                intent.putExtra("item",3);
                intent.setClass(context,PersonalInfoChangeActivity.class);
                startActivityForResult(intent,3);

                break;

            case R.id.personal_weight_item:

                intent = new Intent();
                intent.putExtra("item",4);
                intent.setClass(context,PersonalInfoChangeActivity.class);
                startActivityForResult(intent,4);

                break;

            case R.id.personal_phone_item:

                break;
            case R.id.personal_address_item:

                intent = new Intent();
                intent.putExtra("item",6);
                intent.setClass(context,PersonalInfoChangeActivity.class);
                startActivityForResult(intent,6);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case 0:

                personal_real_name.setText(data.getStringExtra("key"));

                break;

            case 2:

                personal_age.setText(data.getStringExtra("key"));

                break;

            case 3:

                personal_height.setText(data.getStringExtra("key") + "cm");

                break;

            case 4:

                personal_weight.setText(data.getStringExtra("key") + "kg");

                break;

            case 6:

                personal_address.setText(data.getStringExtra("key"));

                break;

           default:
                break;
        }
    }
}
