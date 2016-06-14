package com.test.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.test.adapters.AnimAdapterUtil;
import com.test.demo.MainActivity;
import com.test.demo.PersonalInfos;
import com.test.demo.R;
import com.test.demo.SettingActivity;
import com.test.utils.Contant;
import com.test.utils.Util_SharedPreferences;
import com.zbar.lib.CaptureActivity;

public class Fragment_Main_03 extends Fragment implements View.OnClickListener {

    RelativeLayout personalInfo;
    RelativeLayout setting;
    RelativeLayout payHistory;
    RelativeLayout bindHardware;
    RelativeLayout healthReport;
    RelativeLayout managerDoctor;

    Button login_out;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_03, null);
        initView(view);

        return view;
    }

    public void initView(View view) {

        personalInfo = (RelativeLayout) view.findViewById(R.id.re_personal_info);
        setting = (RelativeLayout) view.findViewById(R.id.re_setting);
        payHistory = (RelativeLayout) view.findViewById(R.id.re_pay_history);
        bindHardware = (RelativeLayout) view.findViewById(R.id.re_bind_hard);
        healthReport = (RelativeLayout) view.findViewById(R.id.re_health_report);
        managerDoctor = (RelativeLayout) view.findViewById(R.id.re_manger_doctor);

        login_out = (Button) view.findViewById(R.id.login_out);

        bindOnClick();

    }

    public void bindOnClick() {

        personalInfo.setOnClickListener(this);
        setting.setOnClickListener(this);
        payHistory.setOnClickListener(this);
        bindHardware.setOnClickListener(this);
        healthReport.setOnClickListener(this);
        managerDoctor.setOnClickListener(this);

        login_out.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.re_personal_info:
                intent.setClass(getActivity(), PersonalInfos.class);
                getActivity().startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());
                break;
            case R.id.re_setting:
                intent.setClass(getActivity(), SettingActivity.class);
                getActivity().startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());
                break;
            case R.id.re_pay_history:
                break;
            case R.id.re_bind_hard:

                intent.setClass(getActivity(), CaptureActivity.class);
                getActivity().startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());

                break;
            case R.id.re_health_report:
                break;
            case R.id.re_manger_doctor:
                break;

            case R.id.login_out:
                Util_SharedPreferences.getInstance().clearData(getActivity(), Contant.SP_USER);
                MainActivity parentActivity = (MainActivity ) getActivity();
                parentActivity.initTab0(0);
                break;
        }

    }

    public interface InitTab{
        public void initTab();
    }
    InitTab initTab;

}
