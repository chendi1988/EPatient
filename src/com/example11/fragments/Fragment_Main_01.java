package com.example11.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example11.adapters.DoctorsAdapter;
import com.example11.adapters.GirdDropDownAdapter;
import com.example11.adapters.ListDropDownAdapter;
import com.example11.myapp.R;
import com.example11.utils.HttpPostUtil;
import com.example11.utils.ToastUtil;
import com.example11.view.DropDownMenu;
import com.example11.view.XListView;
import net.tsz.afinal.FinalBitmap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static com.example11.utils.Contant.URL_GOODS;

public class Fragment_Main_01 extends Fragment{
    private XListView mListView;
    private DoctorsAdapter mAdapter;
    private Handler mHandler;
    private int start = 1;
    private int diedline = 0;
    private int show_num = 0;

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
        mListView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mListView.setPullLoadEnable(true);

        mAdapter = new DoctorsAdapter(getActivity(), datas, fb);
        mListView.setAdapter(mAdapter);

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mListView);
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
                        Map<String, String> map_item;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            map_item = new HashMap<String, String>();
                            map_item.put("id", jsonObject.optString("id"));
                            map_item.put("photo", jsonObject.optString("photo"));
                            map_item.put("goodname", jsonObject.optString("goodname"));
                            map_item.put("money", jsonObject.optString("money"));
                            map_item.put("dec", jsonObject.optString("dec"));

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
