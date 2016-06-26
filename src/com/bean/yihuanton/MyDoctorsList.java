package com.bean.yihuanton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import com.bean.adapters.AnimAdapterUtil;
import com.bean.adapters.DoctorsAdapter;
import com.bean.view.XListView;
import com.bean.utils.Contant;
import com.bean.utils.HttpPostUtil;
import com.bean.utils.Util_SharedPreferences;
import com.bean.view.DialogLoading;
import com.zbar.lib.CaptureActivity;
import net.tsz.afinal.FinalBitmap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chendi on 2016/6/20.
 */
public class MyDoctorsList extends Activity {

    private DoctorsAdapter mAdapter;
    private Handler mHandler = new Handler();

    List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

    private boolean tag = false;

    FinalBitmap fb;

    private XListView xList;

    Context context;

    Button go_back;

    TextView add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydoctors_list);

        context = this;

        initView();
    }

    private void initView() {

        go_back = (Button) findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        add = (TextView) findViewById(R.id.add_doc);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(context, CaptureActivity.class);
                startActivityForResult(intent,0);
                AnimAdapterUtil.anim_translate_next(context);

            }
        });

        fb = FinalBitmap.create(this);

        xList = (XListView) findViewById(R.id.xListView);
        xList.setDividerHeight(0);
        mAdapter = new DoctorsAdapter(context, datas, fb);
        xList.setAdapter(mAdapter);

        xList.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                setFilter(false);
            }

            @Override
            public void onFirshResh() {

            }

            @Override
            public void onLoadMore() {

                new TaskLoadMyMoreDoc(datas == null ? 0 : datas.size() + 1, false).execute();

            }
        });

        xList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putString("id", datas.get(position - 1).get("id").toString());

                Intent intent = new Intent();
                intent.putExtra("infos", bundle);
                intent.setClass(context, DoctorDetailActivity.class);
                startActivity(intent);
                AnimAdapterUtil.anim_translate_next(context);
            }
        });


        xList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://空闲状态
                        mAdapter.onSetScrollState(false);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING://滚动状态
                        mAdapter.onSetScrollState(true);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸后滚动

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        new TaskLoadMyMoreDoc(0, true).execute();

    }

    private void setFilter(boolean showDialog) {
        datas.clear();
        new TaskLoadMyMoreDoc(0, showDialog).execute();
    }

    class TaskLoadMyMoreDoc extends AsyncTask<Void, Void, String> {

        long start = 0;

        Map<String, String> map_more;

        boolean showDialog = false;

        DialogLoading loading;

        public TaskLoadMyMoreDoc(int index, boolean showDialog) {
            map_more = new HashMap<String, String>();
            map_more.put("item", index + "");
            map_more.put("token", Util_SharedPreferences.getInstance().getItemDataByKey(context, Contant.SP_USER, "token"));

            this.showDialog = showDialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            start = System.currentTimeMillis();

            if (showDialog) {

                if (loading == null) {
                    loading = new DialogLoading(context, R.style.dialogwaitingstyle);
                }

                if (!loading.isShowing()) {
                    loading.show();
                }

            }
        }

        @Override
        protected String doInBackground(Void... params) {
            return HttpPostUtil.getPostJsonResult(Contant.URL_DOCTOR_MY, map_more, "");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optString("status").equals("100")) {

                        jsonObject = new JSONObject(jsonObject.getString("result"));

                        JSONArray jsonArray = jsonObject.getJSONArray("item");

                        int pageLeng = Integer.parseInt(jsonObject.optString("pageitem"));

                        if (jsonObject.getString("item").equals("") && datas.size() == 0) {
                            xList.setPullLoadEnable(false);
                        } else if (jsonObject.getString("item").equals("")) {
                            tag = true;
                        }

                        Map<String, Object> map_item;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            map_item = new HashMap<String, Object>();
                            map_item.put("id", jsonObject.optString("id"));
                            map_item.put("name", jsonObject.optString("name"));
                            map_item.put("photo", jsonObject.optString("photo"));
                            map_item.put("dept", jsonObject.optString("dept"));
                            map_item.put("hospital", jsonObject.optString("hospital"));
                            map_item.put("position", jsonObject.optString("position"));
                            datas.add(map_item);
                        }

                        if (jsonArray.length() < Contant.PAGESIZE && datas.size() < Contant.PAGESIZE) {
                            xList.setPullLoadEnable(false);
                        } else if (datas.size() >= Contant.PAGESIZE && jsonArray.length() < Contant.PAGESIZE) {
                            tag = true;
                        } else {
                            tag = false;
                            xList.setPullLoadEnable(true);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            mAdapter.notifyDataSetChanged();

            if (showDialog) {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                }
            }

            onLoad();

        }
    }

    private void onLoad() {

        xList.stopRefresh();
        xList.stopLoadMore();
        xList.setRefreshTime("刚刚");

        if (tag) {
            xList.setFooterText(false, "到底了~");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AnimAdapterUtil.anim_translate_back(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}