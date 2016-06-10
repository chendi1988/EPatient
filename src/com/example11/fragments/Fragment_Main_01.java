package com.example11.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.example11.adapters.AnimAdapterUtil;
import com.example11.adapters.DoctorsAdapter;
import com.example11.adapters.GirdDropDownAdapter;
import com.example11.adapters.ListDropDownAdapter;
import com.example11.myapp.R;
import com.example11.myapp.WebViewActivity;
import com.example11.utils.Contant;
import com.example11.utils.HttpPostUtil;
import com.example11.utils.ToastUtil;
import com.example11.view.DialogLoading;
import com.example11.view.DropDownMenu;
import com.example11.view.XListView;
import net.tsz.afinal.FinalBitmap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static com.example11.utils.Contant.URL_GOODS;

public class Fragment_Main_01 extends Fragment {
    private XListView mListView;
    private DoctorsAdapter mAdapter;
    private Handler mHandler = new Handler();

    List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

    private boolean tag = false;

    FinalBitmap fb;

    private boolean load_more = true;

    View view;

    DropDownMenu mDropDownMenu;
    private String headers[] = {"城市", "医院", "科室", "名医"};
    private List<View> popupViews = new ArrayList<View>();

    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter hospitalAdapter;
    private ListDropDownAdapter departmentAdapter;
    private ListDropDownAdapter famouAdapter;

    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String hospitals[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String departments[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String famous[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_01, null);
        view.setBackgroundColor(Color.WHITE);

        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);

        initView(view);
        return view;
    }

    private void initView(View view) {
        //init city menu
        final ListView cityView = new ListView(view.getContext());
        cityAdapter = new GirdDropDownAdapter(view.getContext(), Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init hospital menu
        final ListView hospitalView = new ListView(view.getContext());
        hospitalView.setDividerHeight(0);
        hospitalAdapter = new ListDropDownAdapter(view.getContext(), Arrays.asList(hospitals));
        hospitalView.setAdapter(hospitalAdapter);

        //init department menu
        final ListView departmentView = new ListView(view.getContext());
        departmentView.setDividerHeight(0);
        departmentAdapter = new ListDropDownAdapter(view.getContext(), Arrays.asList(departments));
        departmentView.setAdapter(departmentAdapter);

        //init famous menu
        final ListView famouView = new ListView(view.getContext());
        famouView.setDividerHeight(0);
        famouAdapter = new ListDropDownAdapter(view.getContext(), Arrays.asList(famous));
        famouView.setAdapter(famouAdapter);

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(hospitalView);
        popupViews.add(departmentView);
        popupViews.add(famouView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });

        hospitalView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hospitalAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : hospitals[position]);
                mDropDownMenu.closeMenu();
            }
        });

        departmentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                departmentAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : departments[position]);
                mDropDownMenu.closeMenu();
            }
        });

        famouView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                famouAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[3] : famous[position]);
                mDropDownMenu.closeMenu();
            }
        });

        fb = FinalBitmap.create(getActivity());

        //init doctor list
        mListView = new XListView(view.getContext());
        mListView.setBackgroundColor(Color.WHITE);
        mListView.setFadingEdgeLength(0);
        mListView.setCacheColorHint(Color.alpha(R.color.touming));
        mListView.setScrollbarFadingEnabled(true);
        setListViewHeightBasedOnChildren(mListView);
        mListView.setPullLoadEnable(true);
        mListView.setDividerHeight(0);

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

        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onLoad();
                    }
                }, 3000);

            }

            @Override
            public void onFirshResh() {
            }

            @Override
            public void onLoadMore() {

                new TaskLoadMore(1, false).execute();

            }
        });

        mAdapter = new DoctorsAdapter(getActivity(), datas, fb);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("pos",position);
                intent.setClass(getActivity(), WebViewActivity.class);
                startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());
            }
        });

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mListView);
        mDropDownMenu.setBackgroundColor(Color.WHITE);

        new TaskLoadMore(1, true).execute();

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

        boolean showDialog = false;

        DialogLoading loading;

        public TaskLoadMore(int index, boolean showDialog) {
            map_more = new HashMap<String, String>();
            map_more.put("index", index + "");

            this.showDialog = showDialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (showDialog) {

                if (loading == null) {
                    loading = new DialogLoading(getActivity(), R.style.dialogwaitingstyle);
                }

                if (!loading.isShowing()) {
                    loading.show();
                }

            }
        }

        @Override
        protected String doInBackground(Void... params) {
//            return HttpPostUtil.getPostJsonResult(URL_GOODS, map_more, "");
            return Contant.DOCTORS;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ToastUtil.showToast(getActivity(), "Fg_1_more" + s);

            if (showDialog) {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                }
            }

            onLoad();

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optString("Status").equals("100")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        Map<String, Object> map_item;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            map_item = new HashMap<String, Object>();
                            map_item.put("headerURL", jsonObject.optString("headerURL"));
                            map_item.put("name", jsonObject.optString("name"));
                            map_item.put("descption", jsonObject.optString("descption"));
                            datas.add(map_item);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void onLoad() {

        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");

        if (datas != null && datas.size() > 15) {
            mListView.setPullLoadEnable(true);
        }

        if (tag) {
            mListView.setFooterText(false, "到底了~");
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除

        listView.setLayoutParams(params);
    }

}
