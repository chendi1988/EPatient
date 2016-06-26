package com.bean.yihuanton;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import com.bean.adapters.AnimAdapterUtil;
import com.bean.adapters.PayHistoryAdapter;
import com.bean.utils.Contant;
import com.bean.utils.HttpPostUtil;
import com.bean.view.XListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bean.utils.Contant.URL_ORDER;

/**
 * Created by chendi on 2016/6/20.
 */
public class PayHistoryList extends Activity{

    XListView xListView;

    private PayHistoryAdapter mAdapter;
    private Handler mHandler;

    List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    private boolean tag = false;

    Button go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_history_list);

        initView();

    }

    public void initView(){

        go_back = (Button) findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        xListView = (XListView) findViewById(R.id.xListView);
        xListView.setDividerHeight(0);
        xListView.setPullLoadEnable(false);// 屏蔽加载更多刷新操作

        mAdapter = new PayHistoryAdapter(this,list);

        XListView.IXListViewListener ixListViewListener = new XListView.IXListViewListener() {
            @Override
            public void onFirshResh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onLoad();
                    }
                }, 10000);

            }

            @Override
            public void onRefresh() {

                tag = false;
                list.clear();
                new TaskLoadMore(1).execute();

            }

            @Override
            public void onLoadMore() {

                new TaskLoadMore(list != null ? list.size() + 1 : 1).execute();

            }
        };

        xListView.setAdapter(mAdapter);
        xListView.setXListViewListener(ixListViewListener);

        mHandler = new Handler();

        new TaskLoadMore(1).execute();
    }

    private void onLoad() {

        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");

        if (tag) {
            xListView.setFooterText(false, "到底了~");
        }
    }

    class TaskLoadMore extends AsyncTask<Void, Void, String> {

        Map<String, String> map_more;
        long start = 0;

        public TaskLoadMore(int index) {
            map_more = new HashMap<String, String>();
            map_more.put("index", index + "");
            map_more.put("phone", getIntent().getStringExtra("phone"));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            return HttpPostUtil.getPostJsonResult(URL_ORDER, map_more, "");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.optString("status").equals("100")) {

                        if (jsonObject.getString("result").equals("") && list.size() == 0) {
                            xListView.setPullLoadEnable(false);
                        }else if(jsonObject.getString("result").equals("")){
                            tag = true;
                        }

                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        Map<String, String> map_item;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            map_item = new HashMap<String, String>();
                            map_item.put("paymoney", jsonObject.optString("paymoney"));
                            map_item.put("createtime", jsonObject.optString("createtime"));
                            map_item.put("goodinfo", jsonObject.optString("goodinfo"));
                            list.add(map_item);
                        }

                        if (jsonArray.length() < Contant.PAGESIZE && list.size() < Contant.PAGESIZE) {
                            xListView.setPullLoadEnable(false);
                        } else if (list.size() >= Contant.PAGESIZE && jsonArray.length() < Contant.PAGESIZE) {
                            tag = true;
                        } else {
                            tag = false;
                            xListView.setPullLoadEnable(true);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            mAdapter.notifyDataSetChanged();
            onLoad();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AnimAdapterUtil.anim_translate_back(this);
    }
}
