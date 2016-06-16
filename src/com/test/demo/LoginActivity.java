package com.test.demo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.test.adapters.AnimAdapterUtil;
import com.test.baseactivity.BaseActivity;
import com.test.utils.*;
import com.test.view.DialogWaiting;
import com.test.view.LinearLayoutView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.test.utils.Contant.FLAG_REGISTER;
import static com.test.utils.Contant.URL_LOGIN;


/**
 * Created by chendi on 2016/5/29.
 */
public class LoginActivity extends BaseActivity implements LinearLayoutView.KeyBordStateListener {

    EditText et_username;
    Button bt_username_clear;

    EditText et_pwd;
    Button bt_pwd_eye;

    Button login;

    Button register;

    Button forget;

    boolean showpwd = false;

    Context context;

    private LinearLayoutView resizeLayout;
    LinearLayout logoLayout;

    public int typeRegister = 0;
    public int typeForget = 1;

    Button go_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        context = this;
        initView();
    }

    public void initView() {

        go_back = (Button) findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        resizeLayout = (LinearLayoutView) findViewById(R.id.login_root_layout);
        logoLayout = (LinearLayout) findViewById(R.id.login_layout_logo);
        resizeLayout.setKeyBordStateListener(this);
        login = (Button) findViewById(R.id.login);


        et_username = (EditText) findViewById(R.id.username);
        et_username.setText(Util_SharedPreferences.getInstance().getItemDataByKey(context, Contant.SP_USER, "phone"));
        et_username.addTextChangedListener(new EditChangedListener());
        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);

        et_pwd = (EditText) findViewById(R.id.password);
        et_pwd.setText(Util_SharedPreferences.getInstance().getItemDataByKey(context, Contant.SP_USER, "password"));
        et_pwd.addTextChangedListener(new EditChangedListener());
        bt_pwd_eye = (Button) findViewById(R.id.bt_pwd_eye);

        if(et_username.getText().length() > 10 && et_pwd.getText().length() > 5){
            login.setEnabled(true);
        }

        register = (Button) findViewById(R.id.register);
        forget = (Button) findViewById(R.id.foget);

        bindOnClick();
    }

    public void bindOnClick() {

        bt_username_clear.setOnClickListener(onClick);
        bt_pwd_eye.setOnClickListener(onClick);

        login.setOnClickListener(onClick);

        register.setOnClickListener(onClick);
        forget.setOnClickListener(onClick);
    }

    View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_username_clear:

                    et_username.setText("");
                    bt_username_clear.setVisibility(View.INVISIBLE);

                    break;

                case R.id.bt_pwd_eye:
                    showpwd = !showpwd;
                    if (showpwd) {
                        bt_pwd_eye.setBackgroundResource(R.drawable.agu);
                        //显示
                        et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        bt_pwd_eye.setBackgroundResource(R.drawable.agt);
                        //隐藏
                        et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }

                    et_pwd.postInvalidate();
                    //切换后将EditText光标置于末尾
                    CharSequence charSequence = et_pwd.getText();
                    if (charSequence instanceof Spannable) {
                        Spannable spanText = (Spannable) charSequence;
                        Selection.setSelection(spanText, charSequence.length());
                    }

                    break;

                case R.id.login:

                    if (!CheckFormatUtil.isPhoneNum(et_username.getText().toString().trim())) {
                        Toast.makeText(context, "请输入正确格式的手机号码", Toast.LENGTH_SHORT).show();
                    } else {
                        new LoginTask().execute();
                    }

                    break;
                case R.id.register:

                    Intent intent1 = new Intent();
                    intent1.putExtra("type",typeRegister);
                    intent1.setClass(context, RegisteActivity.class);
                    startActivityForResult(intent1, FLAG_REGISTER);
                    AnimAdapterUtil.anim_translate_next(context);

                    break;

                case R.id.foget:

                    Intent intent2 = new Intent();
                    intent2.putExtra("type",typeForget);
                    intent2.setClass(context, RegisteActivity.class);
                    startActivityForResult(intent2, FLAG_REGISTER);
                    AnimAdapterUtil.anim_translate_next(context);

                    break;


                default:
                    break;
            }
        }
    };

    @Override
    public void stateChange(int state) {
        switch (state) {
            case LinearLayoutView.KEYBORAD_HIDE:
                logoLayout.setVisibility(View.VISIBLE);
                break;
            case LinearLayoutView.KEYBORAD_SHOW:
                logoLayout.setVisibility(View.GONE);
                break;
        }
    }

    class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (et_username.getText().length() > 0) {
                bt_username_clear.setVisibility(View.VISIBLE);
            } else {
                bt_username_clear.setVisibility(View.INVISIBLE);
            }

            if (et_pwd.getText().length() > 0) {

                bt_pwd_eye.setVisibility(View.VISIBLE);

            } else {
                bt_pwd_eye.setVisibility(View.INVISIBLE);
            }

            if (et_username.getText().length() > 10 && et_pwd.getText().length() > 5) {
                login.setEnabled(true);
            } else {
                login.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    class LoginTask extends AsyncTask<Void, Void, String> {
        Map<String, String> map;
        DialogWaiting dialogWating;

        public LoginTask() {

            map = new HashMap<String, String>();
            map.put("phone", et_username.getText().toString());
            map.put("password", et_pwd.getText().toString());
            map.put("identify", "");

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (dialogWating == null) {
                dialogWating = new DialogWaiting(context, R.style.dialogwaitingstyle);
            }

            if (!dialogWating.isShowing()) {
                dialogWating.show();
                dialogWating.setContent("登录中...");
            }

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return HttpPostUtil.getPostJsonResult(URL_LOGIN, map, "");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (dialogWating != null && dialogWating.isShowing()) {
                dialogWating.dismiss();
            }

            if (result != null) {
                try {
                    JSONObject resultStr = new JSONObject(result);
                    if (resultStr.optString("Status").equals("100")) {
                        showToast("登录成功" + result.toString());


                        resultStr = new JSONObject(resultStr.get("result").toString());

                        map.put("username", resultStr.optString("username"));
                        map.put("token", resultStr.optString("token"));
                        map.put("sex", resultStr.optString("sex"));
                        map.put("age", resultStr.optString("age"));
                        map.put("phone", resultStr.optString("phone"));
                        map.put("height", resultStr.optString("height"));
                        map.put("weight", resultStr.optString("weight"));
                        map.put("address", resultStr.optString("address"));

                        Util_SharedPreferences.getInstance().setItemsDataByMap(context, Contant.SP_USER, map);

//                        Intent intent = new Intent();
//                        switch (getIntent().getIntExtra("target",0)){
//                            case Contant.START_PERSONAL_ACTIVITY:
//                                intent.setClass(context, PersonalInfos.class);
//                                context.startActivity(intent);
//                                break;
//                        }
                        setResult(1);
                        finish();
                        AnimAdapterUtil.anim_translate_back(context);
                    } else {
                        ToastUtil.showToast(context, "登录失败");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void showToast(String msg) {
        Toast.makeText(context, msg.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == 1) {
            et_username.setText(data.getStringExtra("phone"));
            et_pwd.setText(data.getStringExtra("pwd"));
            new LoginTask().execute();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AnimAdapterUtil.anim_translate_back(context);
    }
}
