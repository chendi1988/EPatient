package com.example11.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example11.adapters.DoctorsAdapter;
import com.example11.myapp.R;
import com.example11.view.XListView;
import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Fragment_Main_01 extends Fragment implements XListView.IXListViewListener {
	private XListView mListView;
	private DoctorsAdapter mAdapter;
	private Handler mHandler;
	private int start = 1;
	private int diedline = 0;
	private int show_num = 0;

	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

	private boolean tag = false;

	FinalBitmap fb;

	private boolean load_more = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View frag = inflater.inflate(R.layout.fragment_main_01, null);

		fb = FinalBitmap.create(getActivity());

		geneRefreshItems();
		mListView = (XListView) frag.findViewById(R.id.xListView);
		mListView.setPullLoadEnable(true);
		mAdapter = new DoctorsAdapter(getActivity(), list, fb);
		mListView.setAdapter(mAdapter);

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
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {

//				geneUploadItems();
//				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
}
