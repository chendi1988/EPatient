package com.bean.yihuanton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.bean.adapters.AnimAdapterUtil;
import com.mhealth365.ecgsdkdemo.EcgSdkDemo;

/**
 * Created by chendi on 2016/6/21.
 */
public class HealthListActivity extends Activity{

    Button go_back;

    RelativeLayout div_xindiantu;

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_list_layout);

        context = this;

        initView();
    }

    public void initView() {

        go_back = (Button) findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        div_xindiantu = (RelativeLayout) findViewById(R.id.div_xindiantu);
        div_xindiantu.setOnClickListener(onClick);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.div_xindiantu:
                    Intent intent = new Intent();
                    intent.setClass(context, EcgSdkDemo.class);
                    startActivity(intent);
                    AnimAdapterUtil.anim_translate_next(context);
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AnimAdapterUtil.anim_translate_back(this);
    }
}
