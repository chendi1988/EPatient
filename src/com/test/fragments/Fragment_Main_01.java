//package com.test.fragments;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.AdapterView;
//import com.test.adapters.AnimAdapterUtil;
//import com.test.adapters.DoctorsAdapter;
//import com.test.adapters.OnMenuSelectedListener;
//import com.test.database.EDatabase;
//import com.test.database.MapDataHelper;
//import com.test.demo.DoctorDetailActivity;
//import com.test.demo.R;
//import com.test.utils.Contant;
//import com.test.utils.HttpPostUtil;
//import com.test.view.DialogLoading;
//import com.test.view.DropDownMenu;
//import com.test.view.XListView;
//import net.tsz.afinal.FinalBitmap;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.test.utils.Contant.URL_DOCTOR;
//
//public class Fragment_Main_01 extends Fragment {
//
//    private DoctorsAdapter mAdapter;
//    private Handler mHandler = new Handler();
//
//    List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
//
//    private boolean tag = false;
//
//    FinalBitmap fb;
//
//    View view;
//
//    private DropDownMenu mMenu;
//    private XListView xList;
//
//    private String strings[] = {"城市", "医院", "科室", "名医"};
//
//    List<Map<String, String>> dqList = new ArrayList<Map<String, String>>();
//    List<Map<String, String>> yyList = new ArrayList<Map<String, String>>();
//    List<Map<String, String>> ksList = new ArrayList<Map<String, String>>();
//    List<Map<String, String>> myList = new ArrayList<Map<String, String>>();
//
//    Context context;
//
//    String dq = "";
//    String yy = "";
//    String ks = "";
//    String my = "";
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_main_01, null);
//        view.setBackgroundColor(Color.WHITE);
//        context = getActivity();
//
//        initView(view);
//
//        return view;
//    }
//
//    private void initView(View view) {
//        fb = FinalBitmap.create(getActivity());
//
//        mMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);
//
//        mMenu.setmMenuCount(4);
//        mMenu.setmShowCount(6);
//        mMenu.setShowCheck(true);
//        mMenu.setmMenuTitleTextSize(16);
//        mMenu.setmMenuTitleTextColor(Color.parseColor("#777777"));
//        mMenu.setmMenuListTextSize(16);
//        mMenu.setmMenuListTextColor(Color.BLACK);
//        mMenu.setmMenuBackColor(Color.parseColor("#eeeeee"));
//        mMenu.setmMenuPressedBackColor(Color.WHITE);
//        mMenu.setmMenuPressedTitleTextColor(Color.BLACK);
//
//        mMenu.setmCheckIcon(R.drawable.ico_make);
//
//        mMenu.setmUpArrow(R.drawable.arrow_up);
//        mMenu.setmDownArrow(R.drawable.arrow_down);
//
//        mMenu.setDefaultMenuTitle(strings);
//
//        mMenu.setShowDivider(false);
//        mMenu.setmMenuListBackColor(getResources().getColor(R.color.white));
//        mMenu.setmMenuListSelectorRes(R.color.white);
//        mMenu.setmArrowMarginTitle(20);
//
//        mMenu.setMenuSelectedListener(new OnMenuSelectedListener() {
//            @Override
//            public void onSelected(View listview, int RowIndex, int ColumnIndex) {
//                Log.i("MainActivity", "select " + ColumnIndex + " column and " + RowIndex + " row");
//                if (ColumnIndex == 0) {
//                    dq = (RowIndex == 0 ? "" : RowIndex + "");
//                } else if (ColumnIndex == 1) {
//                    yy = (RowIndex == 0 ? "" : RowIndex + "");
//                } else if (ColumnIndex == 2) {
//                    ks = (RowIndex == 0 ? "" : RowIndex + "");
//                } else {
//                    my = (RowIndex == 0 ? "" : RowIndex + "");
//                }
//                //过滤筛选
//                setFilter(true);
//            }
//        });
//        List<List<Map<String, String>>> items = new ArrayList<List<Map<String, String>>>();
//        dqList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_DQ_NAME);
//        yyList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_YY_NAME);
//        ksList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_KS_NAME);
//        myList = MapDataHelper.getInstance(context).getAllList(EDatabase.TABLE_MY_NAME);
//        items.add(dqList);
//        items.add(yyList);
//        items.add(ksList);
//        items.add(myList);
//        mMenu.setmMenuItems(items);
//
//        mMenu.setIsDebug(false);
//
//        xList = (XListView) view.findViewById(R.id.xListView);
//        xList.setAdapter(new DoctorsAdapter(getActivity(), datas, fb));
//
//        //init doctor list
//        xList.setBackgroundColor(Color.WHITE);
//        xList.setFadingEdgeLength(0);
//        xList.setCacheColorHint(Color.alpha(R.color.touming));
//        xList.setScrollbarFadingEnabled(true);
//        xList.setPullLoadEnable(false);
//        xList.setDividerHeight(0);
//
//        xList.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                switch (scrollState) {
//                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://空闲状态
//                        mAdapter.onSetScrollState(false);
//                        break;
//                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING://滚动状态
//                        mAdapter.onSetScrollState(true);
//                        break;
//                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸后滚动
//
//                        break;
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            }
//        });
//
//        xList.setXListViewListener(new XListView.IXListViewListener() {
//            @Override
//            public void onRefresh() {
//
//                setFilter(false);
//
//            }
//
//            @Override
//            public void onFirshResh() {
//            }
//
//            @Override
//            public void onLoadMore() {
//
//                new TaskLoadMore(datas == null ? 1 : datas.size() + 1, false).execute();
//
//            }
//        });
//
//        mAdapter = new DoctorsAdapter(getActivity(), datas, fb);
//        xList.setAdapter(mAdapter);
//
//        xList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Bundle bundle = new Bundle();
//                bundle.putString("xm", datas.get(position - 1).get("xm").toString());
//                bundle.putString("zjmz", datas.get(position - 1).get("zjmz").toString());
//                bundle.putString("sc", datas.get(position - 1).get("sc").toString());
//                bundle.putString("jj", datas.get(position - 1).get("jj").toString());
//
//                Intent intent = new Intent();
//                intent.putExtra("infos", bundle);
//                intent.setClass(getActivity(), DoctorDetailActivity.class);
//                startActivity(intent);
//                AnimAdapterUtil.anim_translate_next(getActivity());
//            }
//        });
//
//        setFilter(true);
//
//    }
//
//    private void setFilter(boolean showDialog) {
//        datas.clear();
//        new TaskLoadMore(1, showDialog).execute();
//    }
//
//
//    class TaskLoadMore extends AsyncTask<Void, Void, String> {
//
//        long start = 0;
//
//        Map<String, String> map_more;
//
//        boolean showDialog = false;
//
//        DialogLoading loading;
//
//        public TaskLoadMore(int index, boolean showDialog) {
//            map_more = new HashMap<String, String>();
//            map_more.put("index", index + "");
//            map_more.put("dq", dq);
//            map_more.put("yy", yy);
//            map_more.put("ks", ks);
//            map_more.put("my", my);
//
//            this.showDialog = showDialog;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            start = System.currentTimeMillis();
//
//            if (showDialog) {
//
//                if (loading == null) {
//                    loading = new DialogLoading(getActivity(), R.style.dialogwaitingstyle);
//                }
//
//                if (!loading.isShowing()) {
//                    loading.show();
//                }
//
//            }
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            return HttpPostUtil.getPostJsonResult(URL_DOCTOR, map_more, "");
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//                Thread.sleep(System.currentTimeMillis() - start > 2000 ? 0 : System.currentTimeMillis() - start);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (s != null) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    if (jsonObject.optString("Status").equals("100")) {
//
//                        if (jsonObject.getString("result").equals("") && datas.size() == 0) {
//                            xList.setPullLoadEnable(false);
//                        } else if (jsonObject.getString("result").equals("")) {
//                            tag = true;
//                        }
//
//                        JSONArray jsonArray = jsonObject.getJSONArray("result");
//
//                        Map<String, Object> map_item;
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            jsonObject = jsonArray.getJSONObject(i);
//                            map_item = new HashMap<String, Object>();
//                            map_item.put("id", jsonObject.optString("id"));
//                            map_item.put("name", jsonObject.optString("name"));
//                            map_item.put("sex", jsonObject.optString("sex"));
//                            map_item.put("phone", jsonObject.optString("phone"));
//                            map_item.put("updatetime", jsonObject.optString("updatetime"));
//                            map_item.put("ks", jsonObject.optString("ks"));
//                            map_item.put("yy", jsonObject.optString("yy"));
//                            map_item.put("xm", jsonObject.optString("xm"));
//                            map_item.put("zjmz", jsonObject.optString("zjmz"));
//                            map_item.put("sc", jsonObject.optString("sc"));
//                            map_item.put("jj", jsonObject.optString("jj"));
//                            map_item.put("dq", jsonObject.optString("dq"));
//                            map_item.put("my", jsonObject.optString("my"));
//                            datas.add(map_item);
//
//                            if (jsonArray.length() < Contant.PAGESIZE && datas.size() < Contant.PAGESIZE) {
//                                xList.setPullLoadEnable(false);
//                            } else if (datas.size() >= Contant.PAGESIZE && jsonArray.length() < Contant.PAGESIZE) {
//                                tag = true;
//                            } else {
//                                tag = false;
//                                xList.setPullLoadEnable(true);
//                            }
//                        }
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            mAdapter.notifyDataSetChanged();
//
//            if (showDialog) {
//                if (loading != null && loading.isShowing()) {
//                    loading.dismiss();
//                }
//            }
//
//            onLoad();
//
//        }
//    }
//
//    private void onLoad() {
//
//        xList.stopRefresh();
//        xList.stopLoadMore();
//        xList.setRefreshTime("刚刚");
//
//        if (tag) {
//            xList.setFooterText(false, "到底了~");
//        }
//    }
//
//
//}
