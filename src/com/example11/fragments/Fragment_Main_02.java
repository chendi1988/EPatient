package com.example11.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import com.example11.adapters.AnimAdapterUtil;
import com.example11.adapters.GoodsAdapter;
import com.example11.myapp.R;
import com.example11.myapp.ShipCardDetailActivity;
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

public class Fragment_Main_02 extends Fragment {

    private XListView mListView;
    private GoodsAdapter mAdapter;
    private Handler mHandler;

    List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    private boolean tag = false;

    FinalBitmap fb;

    private boolean load_more = true;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_02, null);
        fb = FinalBitmap.create(getActivity());

        mListView = (XListView) view.findViewById(R.id.xListView);
//        mListView.setVerticalScrollBarEnabled(true);

        mListView.setDividerHeight(0);

        mAdapter = new GoodsAdapter(getActivity(), list, fb);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putString("id", list.get(position - 1).get("id"));
                bundle.putString("goodname", list.get(position - 1).get("goodname"));
                bundle.putString("money", list.get(position - 1).get("money"));
                bundle.putString("dec", list.get(position - 1).get("dec"));
                Intent intent = new Intent();
                intent.putExtra("goodinfos", bundle);
                intent.setClass(getActivity(), ShipCardDetailActivity.class);
                startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());
            }
        });
        mListView.setPullLoadEnable(false);// 屏蔽加载更多刷新操作

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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

        mListView.setXListViewListener(ixListViewListener);

        mHandler = new Handler();

        new TaskLoadMore(1).execute();

        return view;
    }

    XListView.IXListViewListener ixListViewListener = new XListView.IXListViewListener() {
        @Override
        public void onFirshResh() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onLoad();
                }
            }, 10000);

            ToastUtil.showToast(getActivity(), "初次加载");
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


    private void onLoad() {

        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");

        if (tag) {
            mListView.setFooterText(false, "到底了~");
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
//            return Contant.GOODS;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.length() == 5) {
//                        mListView.setPullLoadEnable(true);
                    } else {
                        tag = true;
                    }

                    if (jsonObject.optString("Status").equals("100")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        Map<String, String> map_item;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            map_item = new HashMap<String, String>();
                            map_item.put("id", jsonObject.optString("id"));
                            map_item.put("photo", jsonObject.optString("photo"));
                            map_item.put("name", jsonObject.optString("goodname"));
                            map_item.put("money", jsonObject.optString("money"));
                            map_item.put("dec", jsonObject.optString("dec"));
                            list.add(map_item);
                        }

                    }

                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            onLoad();
        }
    }

}
