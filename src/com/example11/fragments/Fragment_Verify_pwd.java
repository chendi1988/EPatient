package com.example11.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example11.myapp.R;
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
public class Fragment_Verify_pwd extends Fragment {

    Bundle vBundle;

    EditText pwd;
    Button pwd_eye;
    Button register;

    boolean showpwd = false;

    String url = "";

    TextView title;

    int type = 0;

    Button go_back;

    public interface GetType {
        public int getType();
    }

    GetType getType;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getType = (GetType) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vBundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_verify_pwd, null);

        go_back = (Button) view.findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        pwd = (EditText) view.findViewById(R.id.pwd);
        pwd_eye = (Button) view.findViewById(R.id.pwd_eye);
        register = (Button) view.findViewById(R.id.register);

        switch (getType.getType()) {
            case 0:

                url = Contant.URL_REGISTER;
                title = (TextView) view.findViewById(R.id.title_msg);
                title.setText("密码");
                pwd.setHint("输入长度6~10位密码");
                register.setText("注册");
                type = 0;

                break;

            case 1:

                url = Contant.URL_UPDATE_PWD;
                title = (TextView) view.findViewById(R.id.title_msg);
                title.setText("重置密码");
                pwd.setHint("输入长度6~10位新密码");
                register.setText("提交");
                type = 2;

                break;

            default:
                break;
        }
        pwd_eye.setOnClickListener(onClick);
        pwd.addTextChangedListener(new EditChangedListener());
        register.setOnClickListener(onClick);


        return view;
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.pwd_eye:
                    showpwd = !showpwd;
                    if (showpwd) {
                        pwd_eye.setBackgroundResource(R.drawable.agu);
                        pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        pwd_eye.setBackgroundResource(R.drawable.agt);
                        pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }

                    pwd.postInvalidate();
                    //切换后将EditText光标置于末尾
                    CharSequence charSequence = pwd.getText();
                    if (charSequence instanceof Spannable) {
                        Spannable spanText = (Spannable) charSequence;
                        Selection.setSelection(spanText, charSequence.length());
                    }
                    break;

                case R.id.register:

                    String pwdStr = pwd.getText().toString().trim();
                    if (pwdStr.length() > 5) {
                        new RegisterTask(pwdStr).execute();
                    } else {
                        ToastUtil.showToast(getActivity(), "输入长度6~10位密码");
                    }

                    break;

                default:
                    break;
            }

        }
    };


    class RegisterTask extends AsyncTask<Void, Void, String> {

        Map<String, String> map;

        public RegisterTask(String pwd) {

            map = new HashMap<String, String>();
            map.put("phone", vBundle.getString("phone"));
            map.put("password", pwd);
            map.put("verifycode", vBundle.getString("verify"));

            if (type == 2) {
                map.put("type", type + "");
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            return HttpPostUtil.getPostJsonResult(url, map, "");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optString("Status").equals("100")) {

                        Intent intent = new Intent();
                        intent.putExtra("phone", map.get("phone"));
                        intent.putExtra("password", map.get("password"));
                        getActivity().setResult(1);
                        getActivity().finish();
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

            if (pwd.getText().length() > 0) {
                pwd_eye.setVisibility(View.VISIBLE);
                register.setEnabled(true);
            } else {
                pwd_eye.setVisibility(View.INVISIBLE);
                register.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
