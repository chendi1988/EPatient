package com.bean.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bean.adapters.AnimAdapterUtil;
import com.bean.adapters.DoctorsAdapter;
import com.bean.adapters.GirdDropDownAdapter;
import com.bean.yihuanton.R;
import com.bean.database.EDatabase;
import com.bean.database.MapDataHelper;
import com.bean.yihuanton.DoctorDetailActivity;
import com.bean.utils.Contant;
import com.bean.utils.HttpPostUtil;
import com.bean.view.DialogLoading;
import com.bean.view.DropDownMenu1;
import com.bean.view.XListView;
import net.tsz.afinal.FinalBitmap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
public class Fragment01 extends Fragment {
    private DoctorsAdapter mAdapter;
    private Handler mHandler = new Handler();

    List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

    private boolean tag = false;

    FinalBitmap fb;

    View view;
    private List<View> popupViews = new ArrayList<View>();
    private DropDownMenu1 mDropDownMenu;
    private XListView xList;

    private GirdDropDownAdapter adapterDQ;
    private GirdDropDownAdapter adapterYIYUAN;
    private GirdDropDownAdapter adapterKESHI;

    private String strings[] = {"城市", "医院", "科室", "名医"};

    boolean mingYiOnClick = false;

    List<Map<String, String>> dqList = new ArrayList<Map<String, String>>();
    List<Map<String, String>> yyList = new ArrayList<Map<String, String>>();
    List<Map<String, String>> ksList = new ArrayList<Map<String, String>>();
    List<Map<String, String>> myList = new ArrayList<Map<String, String>>();

    Context context;

    String dq = "";
    String yy = "";
    String ks = "";
    String my = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_main2, null);

        view.setBackgroundColor(Color.WHITE);

        initView(view);

        return view;
    }

    private void initView(View view) {
        fb = FinalBitmap.create(getActivity());

        mDropDownMenu = (DropDownMenu1) view.findViewById(R.id.dropDownMenu);

        dqList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_DQ_NAME);
        yyList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_YY_NAME);
        ksList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_KS_NAME);
        myList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_MY_NAME);

        //init city menu
        final ListView viewDiqu = new ListView(view.getContext());
        adapterDQ = new GirdDropDownAdapter(view.getContext(), dqList);
        viewDiqu.setDividerHeight(0);
        viewDiqu.setAdapter(adapterDQ);

        //init age menu
        final ListView viewYiyuan = new ListView(view.getContext());
        viewYiyuan.setDividerHeight(0);
        adapterYIYUAN = new GirdDropDownAdapter(view.getContext(), yyList);
        viewYiyuan.setAdapter(adapterYIYUAN);

        //init sex menu
        final ListView viewKeshi = new ListView(view.getContext());
        viewKeshi.setDividerHeight(0);
        adapterKESHI = new GirdDropDownAdapter(view.getContext(), ksList);
        viewKeshi.setAdapter(adapterKESHI);

        TextView tv = new TextView(view.getContext());

        //init popupViews
        popupViews.add(viewDiqu);
        popupViews.add(viewYiyuan);
        popupViews.add(viewKeshi);
        popupViews.add(tv);

        //add item click event
        viewDiqu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterDQ.setCheckItem(position);
                dq = (position == 0 ? "" : dqList.get(position).get("Name"));
                mDropDownMenu.setTabText(position == 0 ? strings[0] : dqList.get(position).get("Name"));
                mDropDownMenu.closeMenu();
                setFilter(true);
            }
        });

        viewYiyuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterYIYUAN.setCheckItem(position);
                yy = (position == 0 ? "" : yyList.get(position).get("Name"));
                mDropDownMenu.setTabText(position == 0 ? strings[1] : yyList.get(position).get("Name"));
                mDropDownMenu.closeMenu();
                setFilter(true);
            }
        });

        viewKeshi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterKESHI.setCheckItem(position);
                ks = (position == 0 ? "" :ksList.get(position).get("Name"));
                mDropDownMenu.setTabText(position == 0 ? strings[2] : ksList.get(position).get("Name"));
                mDropDownMenu.closeMenu();
                setFilter(true);
            }
        });

        mDropDownMenu.setOnDoctorsOnClick(new DropDownMenu1.OnDoctorsOnClick() {
            @Override
            public void docOnClick() {

                adapterDQ.setCheckItem(0);
                adapterYIYUAN.setCheckItem(0);
                adapterKESHI.setCheckItem(0);

                mDropDownMenu.initTabText(Arrays.asList(strings));

                dq = "";
                yy = "";
                ks = "";
                my = "";

                setFilter(true);

            }
        });


        xList = new XListView(view.getContext());
        xList.setAdapter(new DoctorsAdapter(getActivity(), datas, fb));

        //init doctor list
        xList.setBackgroundColor(Color.WHITE);
        xList.setFadingEdgeLength(0);
        xList.setScrollbarFadingEnabled(true);
        xList.setPullLoadEnable(false);
        xList.setDividerHeight(0);
        xList.setCacheColorHint(0);

        mAdapter = new DoctorsAdapter(getActivity(), datas, fb);
        xList.setAdapter(mAdapter);

        //init dropdownview
        RelativeLayout layout = new RelativeLayout(view.getContext());

        RelativeLayout.LayoutParams prarms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        layout.setLayoutParams(prarms);
        xList.setLayoutParams(prarms);
        layout.addView(xList);
        mDropDownMenu.setDropDownMenu(Arrays.asList(strings), popupViews, layout);

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

                new TaskLoadMoreDoc(datas == null ? 1 : datas.size() + 1, false).execute();

            }
        });

        xList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putString("id", datas.get(position - 1).get("id").toString());
//                bundle.putString("zjmz", datas.get(position - 1).get("zjmz").toString());
//                bundle.putString("sc", datas.get(position - 1).get("sc").toString());
//                bundle.putString("jj", datas.get(position - 1).get("jj").toString());

                Intent intent = new Intent();
                intent.putExtra("infos", bundle);
                intent.setClass(getActivity(), DoctorDetailActivity.class);
                startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());
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

        new TaskLoadMoreDoc(0, true).execute();

    }

    private void setFilter(boolean showDialog) {
        datas.clear();
        new TaskLoadMoreDoc(0, showDialog).execute();
    }

    class TaskLoadMoreDoc extends AsyncTask<Void, Void, String> {

        long start = 0;

        Map<String, String> map_more;

        boolean showDialog = false;

        DialogLoading loading;

        public TaskLoadMoreDoc(int index, boolean showDialog) {
            map_more = new HashMap<String, String>();
            map_more.put("item", index + "");
            map_more.put("area", dq);
            map_more.put("hospital", yy);
            map_more.put("dept", ks);

            this.showDialog = showDialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            start = System.currentTimeMillis();

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
            return HttpPostUtil.getPostJsonResult(Contant.URL_DOCTOR, map_more, "");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            System.out.println("首页列表数据：" + s);

            try {
                Thread.sleep(System.currentTimeMillis() - start > 2000 ? 0 : System.currentTimeMillis() - start);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (s != null) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optString("status").equals("100")) {

                        jsonObject =  new JSONObject(jsonObject.getString("result"));

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

}
