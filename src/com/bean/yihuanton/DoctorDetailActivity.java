package com.bean.yihuanton;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bean.adapters.AnimAdapterUtil;
import com.bean.utils.Contant;
import com.bean.utils.HttpPostUtil;
import com.bean.view.DialogLoading;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_detail_layout);

        context = this;
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
//        mzjs.setText(bundle.getString("zjmz").toString());
        sc = (TextView) findViewById(R.id.sc);
//        sc.setText(bundle.getString("sc").toString());
        jj = (TextView) findViewById(R.id.jj);
//        jj.setText(bundle.getString("jj").toString());

        new LoadInfos().execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AnimAdapterUtil.anim_translate_back(this);
    }


    class LoadInfos extends AsyncTask<Void, Void, String> {

        Map<String, String> map;
        DialogLoading loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (loading == null) {
                loading = new DialogLoading(context, R.style.dialogwaitingstyle);
            }

            if (!loading.isShowing()) {
                loading.show();
            }

            map = new HashMap<String, String>();
            map.put("id", bundle.getString("id").toString());

        }

        @Override
        protected String doInBackground(Void... params) {
            return HttpPostUtil.getPostJsonResult(Contant.URL_DOCTOR_INFO, map, null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                if (s != null) {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.optString("status").equals("100")) {
                        jsonObject = new JSONObject(jsonObject.getString("result"));

                        mzjs.setText(jsonObject.optString("zjmz"));
                        sc.setText(jsonObject.optString("goodat"));
                        jj.setText(jsonObject.optString("introduce"));

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (loading != null && loading.isShowing()) {
                loading.dismiss();
            }


        }

    }
}
