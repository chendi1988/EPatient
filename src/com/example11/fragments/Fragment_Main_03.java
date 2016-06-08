package com.example11.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example11.myapp.PersonalInfos;
import com.example11.myapp.R;
import com.example11.myapp.SettingActivity;

public class Fragment_Main_03 extends Fragment implements View.OnClickListener{

	RelativeLayout personalInfo;
	RelativeLayout setting;
	RelativeLayout payHistory;
	RelativeLayout bindHardware;
	RelativeLayout healthReport;
	RelativeLayout managerDoctor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main_03, null);
		initView(view);

		return view;
	}

	public void initView(View view){

		 personalInfo = (RelativeLayout) view.findViewById(R.id.re_personal_info);
		 setting = (RelativeLayout) view.findViewById(R.id.re_setting);
		 payHistory = (RelativeLayout) view.findViewById(R.id.re_pay_history);
		 bindHardware = (RelativeLayout) view.findViewById(R.id.re_bind_hard);
		 healthReport = (RelativeLayout) view.findViewById(R.id.re_health_report);
		 managerDoctor = (RelativeLayout) view.findViewById(R.id.re_manger_doctor);

		bindOnClick();

	}

	public void bindOnClick(){

		personalInfo.setOnClickListener(this);
		setting.setOnClickListener(this);
		payHistory.setOnClickListener(this);
		bindHardware.setOnClickListener(this);
		healthReport.setOnClickListener(this);
		managerDoctor.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		Intent intent = new Intent();

		switch (v.getId()){
			case R.id.re_personal_info:
				intent.setClass(getActivity(), PersonalInfos.class);
				getActivity().startActivity(intent);
				break;
			case R.id.re_setting:
				intent.setClass(getActivity(), SettingActivity.class);
				getActivity().startActivity(intent);
				break;
			case R.id.re_pay_history:
				break;
			case R.id.re_bind_hard:
				break;
			case R.id.re_health_report:
				break;
			case R.id.re_manger_doctor:
				break;
		}

	}
}
