package com.example11.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example11.myapp.R;
import com.example11.utils.CheckFormatUtil;
import com.example11.utils.Contant;
import com.example11.utils.HttpPostUtil;
import com.example11.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chendi on 2016/6/8.
 */
public class Fragment_Verify_Phone extends Fragment {

    EditText rg_phone;
    Button rg_phone_clear;
    Button next;

    Button go_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_verify_phone, null);

        go_back = (Button) view.findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        rg_phone = (EditText) view.findViewById(R.id.rg_phone);
        rg_phone.addTextChangedListener(new EditChangedListener());
        rg_phone_clear = (Button) view.findViewById(R.id.rg_phone_clear);
        rg_phone_clear.setOnClickListener(onClick);
        next = (Button) view.findViewById(R.id.next);
        next.setOnClickListener(onClick);
        return view;
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.next:

                    String pNum = rg_phone.getText().toString().trim();

                    if (!CheckFormatUtil.isPhoneNum(pNum)) {
                        ToastUtil.showToast(getActivity(), "请输入正确的手机号码");
                    } else {
                        new RequestVerify(pNum).execute();
                    }

                    break;

                case R.id.rg_phone_clear:
                    rg_phone.setText("");
                    rg_phone_clear.setVisibility(View.INVISIBLE);
                    break;

                default:
                    break;
            }
        }
    };

    class RequestVerify extends AsyncTask<Void, Void, String> {

        String phoneNum = "";
        Map<String, String> map;

        public RequestVerify(String pNum) {
            phoneNum = pNum;
            map = new HashMap<String, String>();
            map.put("phone", phoneNum);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            return HttpPostUtil.getPostJsonResult(Contant.URL_SEND_IFYCODE, map, "");
//            return "s";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optString("Status").equals("100")) {

                        ToastUtil.showToast(getActivity(), "验证码已发送");

                        Fragment_Verify_Verify fragmentVerifyVerify = new Fragment_Verify_Verify();
                        Bundle bundle = new Bundle();
                        bundle.putString("phone",phoneNum);
                        fragmentVerifyVerify.setArguments(bundle);

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();

                        ft.replace(R.id.register, fragmentVerifyVerify);
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                ToastUtil.showToast(getActivity(), "网络请求失败，请稍后尝试");

            }

        }
    }


    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (rg_phone.getText().length() > 0) {
                rg_phone_clear.setVisibility(View.VISIBLE);
                next.setEnabled(true);
            } else {
                rg_phone_clear.setVisibility(View.INVISIBLE);
                next.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
