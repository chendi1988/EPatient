package com.bean.yihuanton;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.bean.utils.Contant;
import com.bean.utils.HttpPostUtil;
import com.bean.utils.ToastUtil;
import com.bean.utils.Util_SharedPreferences;
import com.bean.view.DialogWaiting;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chendi on 2016/5/30.
 */
public class ChangePwdFragmet extends Fragment {

    Button go_back;

    EditText oldPwd;
    Button oldPwdeye;

    EditText newPwd;
    Button newPwdeye;

    Button submit;

    String url = Contant.URL_UPDATE_PWD;

    Context context;

    View view;

    Bundle vBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.activity_change_pwd_layout,null);

        vBundle = getArguments();

        go_back = (Button) view.findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        initView();

        return view;
    }

    public void initView() {
        oldPwd = (EditText) view.findViewById(R.id.old_pwd);
        oldPwd.addTextChangedListener(new EditChangedListener());
        oldPwdeye = (Button) view.findViewById(R.id.pwd_old_eye);
        oldPwdeye.setOnClickListener(onClick);

        newPwd = (EditText) view.findViewById(R.id.new_pwd);
        newPwd.addTextChangedListener(new EditChangedListener());
        newPwdeye = (Button) view.findViewById(R.id.pwd_new_eye);
        newPwdeye.setOnClickListener(onClick);

        submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(onClick);
    }

    boolean showOldPWD = false;
    boolean showNewPWD = false;

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.changepwd:
                    break;

                case R.id.pwd_old_eye:

                    showOldPWD = !showOldPWD;
                    if (showOldPWD) {
                        oldPwdeye.setBackgroundResource(R.drawable.agu);
                        //显示
                        oldPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        oldPwdeye.setBackgroundResource(R.drawable.agt);
                        //隐藏
                        oldPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }

                    oldPwd.postInvalidate();
                    //切换后将EditText光标置于末尾
                    CharSequence charSequence = oldPwd.getText();
                    if (charSequence instanceof Spannable) {
                        Spannable spanText = (Spannable) charSequence;
                        Selection.setSelection(spanText, charSequence.length());
                    }

                    break;

                case R.id.pwd_new_eye:

                    showNewPWD = !showNewPWD;
                    if (showNewPWD) {
                        newPwdeye.setBackgroundResource(R.drawable.agu);
                        //显示
                        newPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        newPwdeye.setBackgroundResource(R.drawable.agt);
                        //隐藏
                        newPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }

                    newPwd.postInvalidate();
                    //切换后将EditText光标置于末尾
                    CharSequence charSequence1 = newPwd.getText();
                    if (charSequence1 instanceof Spannable) {
                        Spannable spanText = (Spannable) charSequence1;
                        Selection.setSelection(spanText, charSequence1.length());
                    }

                    break;

                case R.id.submit:

                    new UpdatePWD().execute();

                    break;

            }
        }
    };

    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (oldPwd.getText().length() > 0) {
                oldPwdeye.setVisibility(View.VISIBLE);
            } else {
                oldPwdeye.setVisibility(View.INVISIBLE);
            }

            if (newPwd.getText().length() > 0) {

                newPwdeye.setVisibility(View.VISIBLE);

            } else {
                newPwdeye.setVisibility(View.INVISIBLE);
            }

            if (oldPwd.getText().length() > 5 && newPwd.getText().length() > 5) {
                submit.setEnabled(true);
            } else {
                submit.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    DialogWaiting dialogWaiting;

    class UpdatePWD extends AsyncTask<Void, Void, String> {

        Map<String, String> map;

        public UpdatePWD() {

            map = new HashMap<String, String>();
            map.put("type", "2");
            map.put("phone", vBundle.getString("phone"));
            map.put("verifycode", vBundle.getString("verify"));
            map.put("oldpassword", oldPwd.getText().toString().trim());
            map.put("newpassword", newPwd.getText().toString().trim());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (dialogWaiting == null) {
                dialogWaiting = new DialogWaiting(getActivity(), R.style.dialogwaitingstyle);
            }

            if (!dialogWaiting.isShowing()) {
                dialogWaiting.show();
                dialogWaiting.setContent("处理中...");
            }


        }

        @Override
        protected String doInBackground(Void... params) {
            return HttpPostUtil.getPostJsonResult(url, map, "");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (dialogWaiting != null && dialogWaiting.isShowing()) {
                dialogWaiting.dismiss();
            }

            if (s != null) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optString("status").equals("100")) {
                        Util_SharedPreferences.getInstance().setItemDataByKey(context,Contant.SP_USER,"password",newPwd.getText().toString().trim());
                        getActivity().setResult(0);
                        getActivity().finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtil.showToast(context, "数据解析出错");
                }

            } else {
                ToastUtil.showToast(context, "网络请求错误");
            }
        }
    }
}
