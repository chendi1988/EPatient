package com.example11.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example11.adapters.DoctorsAdapter;
import com.example11.myapp.R;
import com.example11.utils.HttpPostUtil;
import com.example11.utils.ToastUtil;
import com.example11.view.XListView;
import net.tsz.afinal.FinalBitmap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example11.utils.Contant.URL_GOODS;

public class Fragment_Main_01 extends Fragment implements XListView.IXListViewListener {
    private XListView mListView;
    private DoctorsAdapter mAdapter;
    private Handler mHandler;
    private int start = 1;
    private int diedline = 0;
    private int show_num = 0;

    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    private boolean tag = false;

    FinalBitmap fb;

    private boolean load_more = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.fragment_main_01, null);

        fb = FinalBitmap.create(getActivity());

        geneRefreshItems();
        mListView = (XListView) frag.findViewById(R.id.xListView);
        mListView.setPullLoadEnable(true);

        mAdapter = new DoctorsAdapter(getActivity(), list, fb);
        mListView.setAdapter(mAdapter);

        if (list != null && list.size() < 10) {
//			mListView.setPullLoadEnable(false);
        }

        mListView.setXListViewListener(this);
        mHandler = new Handler();

        return frag;
    }

    private void geneUploadItems() {

//		if (load_more != false) {
//
//			for (int i = 0; i != 50; ++i) {
//				// i从0开始
//				show_num = diedline + 1;
//				items.add("refresh cnt " + show_num);
//			}
//
//			diedline = show_num;
//
//			if (items.size() > 50) {
//
//				load_more = false;
//				// mListView.setPullLoadEnable(false);// 加载更多控件动态控制屏蔽
//				tag = true;
//			}
//
//		}

    }

    private void geneRefreshItems() {

        for (int i = 0; i != 50; ++i) {
            show_num = start + i;
//			items.add("refresh cnt " + show_num);
        }

        diedline = show_num;

    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");

        if (list != null && list.size() > 15) {
            mListView.setPullLoadEnable(true);
        }

        if (tag) {
            mListView.setFooterText(false, "到底了~");
        }
    }

    @Override
    public void onRefresh() {

        tag = false;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//				start = start - 1;
//				items.clear();
//				geneRefreshItems();
//				mAdapter = new DoctorsAdapter(getActivity(),
//						list, fb);
//				mListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {

        tag = false;

        if (list != null && list.size() > 0) {
            new TaskLoadMore(list.size() + 1).execute();
        } else {
            new TaskLoadMore(1).execute();
        }

//		mHandler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//
////				geneUploadItems();
////				mAdapter.notifyDataSetChanged();
//				onLoad();
//			}
//		}, 2000);
    }

    class TaskRefresh extends AsyncTask<Void, Void, String> {

        Map<String, String> map_ref;

        public TaskRefresh(int size) {
            map_ref = new HashMap<String, String>();
            map_ref.put("index", "1");

            if (size > 0) {
                map_ref.put("index", size + "");
            } else {
                map_ref.put("index", size + "");
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            return HttpPostUtil.getPostJsonResult(URL_GOODS, map_ref, "");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ToastUtil.showToast(getActivity(), "Fg_1" + s);
        }
    }


    class TaskLoadMore extends AsyncTask<Void, Void, String> {

        Map<String, String> map_more;

        public TaskLoadMore(int index) {
            map_more = new HashMap<String, String>();
            map_more.put("index", index + "");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            return HttpPostUtil.getPostJsonResult(URL_GOODS, map_more, "");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ToastUtil.showToast(getActivity(), "Fg_1_more" + s);

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optString("Status").equals("100")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        Map<String ,String> map_item;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            map_item = new HashMap<String, String>();
                            map_item.put("id",jsonObject.optString("id"));
                            map_item.put("photo",jsonObject.optString("photo"));
                            map_item.put("goodname",jsonObject.optString("goodname"));
                            map_item.put("money",jsonObject.optString("money"));
                            map_item.put("dec",jsonObject.optString("dec"));

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
