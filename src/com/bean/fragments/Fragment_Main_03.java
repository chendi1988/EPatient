package com.bean.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bean.adapters.AnimAdapterUtil;
import com.bean.utils.CheckFormatUtil;
import com.bean.utils.Contant;
import com.bean.utils.HttpPostUtil;
import com.bean.utils.Util_SharedPreferences;
import com.bean.view.DialogLoading;
import com.bean.view.MyCircleImageView;
import com.bean.yihuanton.*;
import com.zbar.lib.CaptureActivity;
import net.tsz.afinal.FinalBitmap;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Fragment_Main_03 extends Fragment implements View.OnClickListener {

    MyCircleImageView header;
    TextView load;

    LinearLayout msg_layout;
    TextView doc_des;
    TextView yy;

    RelativeLayout personalInfo;
    RelativeLayout setting;
    RelativeLayout payHistory;
    RelativeLayout bindHardware;
    RelativeLayout healthReport;
    RelativeLayout managerDoctor;

    Button login_out;

    FinalBitmap fb;

    boolean isLogin = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_03, null);
        initView(view);

        fb = FinalBitmap.create(view.getContext());

        initViewData();

        return view;
    }

    public void initView(View view) {

        header = (MyCircleImageView) view.findViewById(R.id.header);

        load = (TextView) view.findViewById(R.id.load);

        msg_layout = (LinearLayout) view.findViewById(R.id.msg_layout);
        doc_des = (TextView) view.findViewById(R.id.doc_des);
        yy = (TextView) view.findViewById(R.id.yy);

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


        if (!isLogin) {
            loginInterface.needLogin();
            return;
        }

        Intent intent;
        switch (v.getId()) {
            case R.id.re_personal_info:
                intent = new Intent();
                intent.setClass(getActivity(), PersonalInfos.class);
                getActivity().startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());
                break;
            case R.id.re_setting:
                intent = new Intent();
                intent.setClass(getActivity(), SettingActivity.class);
                getActivity().startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());
                break;
            case R.id.re_pay_history:
                intent = new Intent();
                intent.putExtra("phone", Util_SharedPreferences.getInstance().getItemDataByKey(getActivity(), Contant.SP_USER, "username"));
                intent.setClass(getActivity(), PayHistoryList.class);
                getActivity().startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());

                break;
            case R.id.re_bind_hard:
                intent = new Intent();
                intent.setClass(getActivity(), CaptureActivity.class);
                getActivity().startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());

                break;
            case R.id.re_health_report:
                intent = new Intent();
                intent.setClass(getActivity(), HealthListActivity.class);
                getActivity().startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());

                break;
            case R.id.re_manger_doctor:

                intent = new Intent();
                intent.setClass(getActivity(), MyDoctorsList.class);
                getActivity().startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());

                break;

            case R.id.login_out:

//                String token = Util_SharedPreferences.getInstance().getItemDataByKey(getActivity(),Contant.SP_USER,"token");
//                if(token.equals("")){
                    loginInterface.loginOut();
//                }else{
//                    new Loginout(token).execute();
//                }
                break;
        }

    }

    class Loginout extends AsyncTask<Void,Void,String>{

        Map<String,String> map;
        DialogLoading loading;

        public Loginout(String token){
            map = new HashMap<String, String>();
            map.put("token",token);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (loading == null) {
                loading = new DialogLoading(getActivity(), R.style.dialogwaitingstyle);
            }

            if (!loading.isShowing()) {
                loading.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            return HttpPostUtil.getPostJsonResult(Contant.URL_LOGIN_OUT,map,"");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null){
                try {
                    JSONObject json = new JSONObject(s);
                    if(json.optString("status").equals("100")){
                        loginInterface.loginOut();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (loading != null && loading.isShowing()) {
                loading.dismiss();
            }

        }
    }

    View.OnClickListener titleOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (isLogin) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PersonalInfos.class);
                getActivity().startActivity(intent);
                AnimAdapterUtil.anim_translate_next(getActivity());
            } else {
                loginInterface.startLogin();
            }

        }
    };

    public void initViewData() {

        SharedPreferences sp = Util_SharedPreferences.getInstance().getSharedPreferences(getActivity(), Contant.SP_USER);

        if (CheckFormatUtil.isPhoneNum(sp.getString("username", "")) && sp.getString("logintype", "0").equals("1")) {
            isLogin = true;
            load.setVisibility(View.GONE);
            msg_layout.setVisibility(View.VISIBLE);
            login_out.setEnabled(true);

            header.setOnClickListener(titleOnClick);
            doc_des.setOnClickListener(titleOnClick);
            yy.setOnClickListener(titleOnClick);
            fb.display(header, "http://img0.imgtn.bdimg.com/it/u=2627898444,3954990436&fm=21&gp=0.jpg");
            doc_des.setText(sp.getString("name", ""));
//            yy.setText(sp.getString("yy", ""));
        } else {
            isLogin = false;
            msg_layout.setVisibility(View.GONE);
            load.setVisibility(View.VISIBLE);
            login_out.setEnabled(false);

            header.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.my_face_default));
            header.setOnClickListener(titleOnClick);
            load.setOnClickListener(titleOnClick);

        }


    }

    ;

    public interface LoginInterface {
        public void needLogin();

        public void startLogin();

        public void loginOut();
    }

    LoginInterface loginInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        loginInterface = (LoginInterface) activity;
    }
}
