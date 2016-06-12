package com.example11.myapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example11.adapters.AnimAdapterUtil;
import com.example11.utils.ToastUtil;

/**
 * Created by chendi on 2016/6/13.
 */
public class ShipCardDetailActivity extends Activity {


    Button pay;

    private TextView name;

    private TextView moneys;
    private TextView detail;

    Bundle bundle;

    Context context;

    String money = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipcard_detail_layout);
        context = this;
        bundle = getIntent().getBundleExtra("goodinfos");
        money = bundle.getString("money");
        initView();
    }

    public void initView() {

        pay = (Button) findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(context, "立即购买");
            }
        });

        name = (TextView) findViewById(R.id.name);
//        name.setText(bundle.getString("goodname"));


        moneys = (TextView) findViewById(R.id.moneys);
        moneys.setText("￥" + money + "元");
        detail = (TextView) findViewById(R.id.detail);
        detail.setText(bundle.getString("dec"));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AnimAdapterUtil.anim_translate_back(this);
    }
}
