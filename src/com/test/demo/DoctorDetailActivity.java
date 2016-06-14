package com.test.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.test.adapters.AnimAdapterUtil;

/**
 * Created by chendi on 2016/6/13.
 */
public class DoctorDetailActivity extends Activity {


    private TextView titlemsg;

    private TextView zjmz;
    private TextView mzjs;
    private TextView sc_title;
    private TextView sc;
    private TextView jj_title;
    private TextView jj;

    Bundle bundle;

    Button go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_detail_layout);

        bundle = getIntent().getBundleExtra("infos");

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

        titlemsg = (TextView) findViewById(R.id.titlemsg);
        titlemsg.setText(bundle.getString("xm"));

//        zjmz = (TextView) findViewById(R.id.zjmz);
//        TextPaint tp = zjmz.getPaint();
//        tp.setFakeBoldText(true);
//
//        sc_title = (TextView) findViewById(R.id.sc_title);
//        tp = sc_title.getPaint();
//        tp.setFakeBoldText(true);
//        jj_title = (TextView) findViewById(R.id.jj_title);
//        tp = jj_title.getPaint();
//        tp.setFakeBoldText(true);

        mzjs = (TextView) findViewById(R.id.mzjs);
        mzjs.setText(bundle.getString("zjmz").toString());
        sc = (TextView) findViewById(R.id.sc);
        sc.setText(bundle.getString("sc").toString());
        jj = (TextView) findViewById(R.id.jj);
        jj.setText(bundle.getString("jj").toString());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AnimAdapterUtil.anim_translate_back(this);
    }
}
