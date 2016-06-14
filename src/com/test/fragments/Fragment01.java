package com.test.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.test.demo.R;

public class Fragment01 extends Fragment {
	View view ;

//    DropDownMenu mDropDownMenu;
//    private String headers[] = {"城市", "医院", "科室", "名医"};
//    private List<View> popupViews = new ArrayList<View>();
//
//   private GirdDropDownAdapter cityAdapter;
//   private ListDropDownAdapter hospitalAdapter;
//   private ListDropDownAdapter departmentAdapter;
//   private ListDropDownAdapter famouAdapter;
//
//   private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
//   private String hospitals[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
//   private String departments[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
//   private String famous[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};

   @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       view = inflater.inflate(R.layout.activity_main2, null);
		
//       mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);
//
//	   initView(view);
		return view;
	}

//   private void initView(View view) {
//       //init city menu
//       final ListView cityView = new ListView(view.getContext());
//       cityAdapter = new GirdDropDownAdapter(view.getContext(), Arrays.asList(citys));
//       cityView.setDividerHeight(0);
//       cityView.setAdapter(cityAdapter);
//
//       //init hospital menu
//       final ListView hospitalView = new ListView(view.getContext());
//       hospitalView.setDividerHeight(0);
//       hospitalAdapter = new ListDropDownAdapter(view.getContext(), Arrays.asList(hospitals));
//       hospitalView.setAdapter(hospitalAdapter);
//
//       //init department menu
//       final ListView departmentView = new ListView(view.getContext());
//       departmentView.setDividerHeight(0);
//       departmentAdapter = new ListDropDownAdapter(view.getContext(), Arrays.asList(departments));
//       departmentView.setAdapter(departmentAdapter);
//
//       //init famous menu
//       final ListView famouView = new ListView(view.getContext());
//       famouView.setDividerHeight(0);
//       famouAdapter = new ListDropDownAdapter(view.getContext(), Arrays.asList(famous));
//       famouView.setAdapter(famouAdapter);
//
//       //init popupViews
//       popupViews.add(cityView);
//       popupViews.add(hospitalView);
//       popupViews.add(departmentView);
//       popupViews.add(famouView);
//
//       //add item click event
//       cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//           @Override
//           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               cityAdapter.setCheckItem(position);
//               mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
//               mDropDownMenu.closeMenu();
//           }
//       });
//
//       hospitalView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//           @Override
//           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               hospitalAdapter.setCheckItem(position);
//               mDropDownMenu.setTabText(position == 0 ? headers[1] : hospitals[position]);
//               mDropDownMenu.closeMenu();
//           }
//       });
//
//       departmentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//           @Override
//           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               departmentAdapter.setCheckItem(position);
//               mDropDownMenu.setTabText(position == 0 ? headers[2] : departments[position]);
//               mDropDownMenu.closeMenu();
//           }
//       });
//
//       famouView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//           @Override
//           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               famouAdapter.setCheckItem(position);
//               mDropDownMenu.setTabText(position == 0 ? headers[3] : famous[position]);
//               mDropDownMenu.closeMenu();
//           }
//       });
//
//
//
//       fb = FinalBitmap.create(getActivity());
//
//       //init doctor list
//       mListView = new XListView(view.getContext());
//       mListView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//       mListView.setPullLoadEnable(true);
//
//       mAdapter = new DoctorsAdapter(getActivity(), datas, fb);
//       mListView.setAdapter(mAdapter);
//
//       //init dropdownview
//       mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mListView);
//   }
//
//
//    private XListView mListView;
//    private DoctorsAdapter mAdapter;
//    List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
//    FinalBitmap fb;
	

}
