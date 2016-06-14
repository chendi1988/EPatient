package com.example11.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.example11.adapters.AnimAdapterUtil;
import com.example11.adapters.DoctorsAdapter;
import com.example11.adapters.GirdDropDownAdapter;
import com.example11.database.EDatabase;
import com.example11.database.MapDataHelper;
import com.example11.myapp.DoctorDetailActivity;
import com.example11.myapp.R;
import com.example11.utils.HttpPostUtil;
import com.example11.view.DialogLoading;
import com.example11.view.DropDownMenu;
import com.example11.view.XListView;
import net.tsz.afinal.FinalBitmap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static com.example11.utils.Contant.URL_DOCTOR;

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
    private GirdDropDownAdapter hospitalAdapter;
    private GirdDropDownAdapter departmentAdapter;
    private GirdDropDownAdapter famouAdapter;

    List<Map<String, String>> dqList = new ArrayList<Map<String, String>>();
    List<Map<String, String>> yyList = new ArrayList<Map<String, String>>();
    List<Map<String, String>> ksList = new ArrayList<Map<String, String>>();
    List<Map<String, String>> myList = new ArrayList<Map<String, String>>();

    Context context;

    String dq = "";
    String yy = "";
    String ks = "";
    String my = "";
    int index = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_01, null);
        view.setBackgroundColor(Color.WHITE);
        context = getActivity();
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);

        initView(view);

        return view;
    }

    private void initView(View view) {
        //init city menu
        dqList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_DQ_NAME);
        final ListView cityView = new ListView(view.getContext());
        cityAdapter = new GirdDropDownAdapter(view.getContext(), dqList);
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init hospital menu
        yyList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_YY_NAME);
        final ListView hospitalView = new ListView(view.getContext());
        hospitalView.setDividerHeight(0);
        hospitalAdapter = new GirdDropDownAdapter(view.getContext(), yyList);
        hospitalView.setAdapter(hospitalAdapter);

        //init department menu
        ksList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_KS_NAME);
        final ListView departmentView = new ListView(view.getContext());
        departmentView.setDividerHeight(0);
        departmentAdapter = new GirdDropDownAdapter(view.getContext(), ksList);
        departmentView.setAdapter(departmentAdapter);

        //init famous menu
        myList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_MY_NAME);
        final ListView famouView = new ListView(view.getContext());
        famouView.setDividerHeight(0);
        famouAdapter = new GirdDropDownAdapter(view.getContext(), myList);
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
                if (position == 0) {
                    dq = "";
                } else {
                    dq = dqList.get(position).get("ID");
                }
                index = 1;
                tag = false;
                datas = new ArrayList<Map<String, Object>>();
                mAdapter = new DoctorsAdapter(getActivity(), datas, fb);
                mListView.setAdapter(mAdapter);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : dqList.get(position).get("Name"));
                mDropDownMenu.closeMenu();
                new TaskLoadMore(1, false).execute();
            }
        });

        hospitalView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hospitalAdapter.setCheckItem(position);
                if (position == 0) {
                    yy = "";
                } else {
                    yy = yyList.get(position).get("ID");
                }
                index = 1;
                tag = false;
                datas = new ArrayList<Map<String, Object>>();
                mAdapter = new DoctorsAdapter(getActivity(), datas, fb);
                mListView.setAdapter(mAdapter);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : yyList.get(position).get("Name"));
                mDropDownMenu.closeMenu();
                new TaskLoadMore(1, false).execute();
            }
        });

        departmentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                departmentAdapter.setCheckItem(position);
                if (position == 0) {
                    ks = "";
                } else {
                    ks = ksList.get(position).get("ID");
                }
                index = 1;
                tag = false;
                datas = new ArrayList<Map<String, Object>>();
                mAdapter = new DoctorsAdapter(getActivity(), datas, fb);
                mListView.setAdapter(mAdapter);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : ksList.get(position).get("Name"));
                mDropDownMenu.closeMenu();
                new TaskLoadMore(1, false).execute();
            }
        });

        famouView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                famouAdapter.setCheckItem(position);
                if (position == 0) {
                    my = "";
                } else {
                    my = myList.get(position).get("ID");
                }
                index = 1;
                datas = new ArrayList<Map<String, Object>>();
                mAdapter = new DoctorsAdapter(getActivity(), datas, fb);
                mListView.setAdapter(mAdapter);
                mDropDownMenu.setTabText(position == 0 ? headers[3] : myList.get(position).get("Name"));
                mDropDownMenu.closeMenu();
                new TaskLoadMore(1, false).execute();
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
        mListView.setPullLoadEnable(false);
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
                datas.clear();
                new TaskLoadMore(1, false).execute();


            }

            @Override
            public void onFirshResh() {
            }

            @Override
            public void onLoadMore() {

                new TaskLoadMore((datas == null ? 0 : datas.size()) + 1, false).execute();

            }
        });

        mAdapter = new DoctorsAdapter(getActivity(), datas, fb);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putString("xm", datas.get(position - 1).get("xm").toString());
                bundle.putString("zjmz", datas.get(position - 1).get("zjmz").toString());
                bundle.putString("sc", datas.get(position - 1).get("sc").toString());
                bundle.putString("jj", datas.get(position - 1).get("jj").toString());

                Intent intent = new Intent();
                intent.putExtra("infos", bundle);
                intent.setClass(getActivity(), DoctorDetailActivity.class);
                startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());
            }
        });

        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mDropDownMenu.closeMenu();

                return false;
            }
        });

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

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mListView);
        mDropDownMenu.setBackgroundColor(Color.WHITE);

        new TaskLoadMore(1, true).execute();

    }


    class TaskLoadMore extends AsyncTask<Void, Void, String> {

        Map<String, String> map_more;

        boolean showDialog = false;

        DialogLoading loading;

        public TaskLoadMore(int index, boolean showDialog) {
            map_more = new HashMap<String, String>();
            map_more.put("index", index + "");
            map_more.put("dq", dq);
            map_more.put("yy", yy);
            map_more.put("ks", ks);
            map_more.put("my", my);

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
            return HttpPostUtil.getPostJsonResult(URL_DOCTOR, map_more, "");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (showDialog) {
                if (loading != null && loading.isShowing()) {
                    loading.dismiss();
                }
            }

            if (s != null) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optString("Status").equals("100")) {

                        if (s.equals("")) {

                            tag = true;

                        } else {

                            JSONArray jsonArray = jsonObject.getJSONArray("result");

                            Map<String, Object> map_item;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                map_item = new HashMap<String, Object>();
                                map_item.put("id", jsonObject.optString("id"));
                                map_item.put("name", jsonObject.optString("name"));
                                map_item.put("sex", jsonObject.optString("sex"));
                                map_item.put("phone", jsonObject.optString("phone"));
                                map_item.put("updatetime", jsonObject.optString("updatetime"));
                                map_item.put("ks", jsonObject.optString("ks"));
                                map_item.put("yy", jsonObject.optString("yy"));
                                map_item.put("xm", jsonObject.optString("xm"));
                                map_item.put("zjmz", jsonObject.optString("zjmz"));
                                map_item.put("sc", jsonObject.optString("sc"));
                                map_item.put("jj", jsonObject.optString("jj"));
                                map_item.put("dq", jsonObject.optString("dq"));
                                map_item.put("my", jsonObject.optString("my"));
                                datas.add(map_item);
                            }

                            if (jsonArray.length() < 5 && datas.size() < 5) {
                                mListView.setPullLoadEnable(false);
                            } else if (datas.size() > 5 && jsonArray.length() < 5) {
                                tag = true;
                            } else {
                                tag = false;
                                mListView.setPullLoadEnable(true);
                            }

                            mAdapter.notifyDataSetChanged();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            onLoad();

        }
    }

    private void onLoad() {

        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");

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
