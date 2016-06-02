package com.example11.myapp;

import android.app.Activity;
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
import android.widget.Toast;
import com.example11.utils.HttpPostUtil;

import java.util.HashMap;
import java.util.Map;

import static com.example11.utils.Contant.url_login;

/**
 * Created by chendi on 2016/5/29.
 */
public class LoginActivity extends Activity {

    EditText et_username;
    Button bt_username_clear;

    EditText et_pwd;
    Button bt_pwd_eye;
    Button bt_pwd_clear;

    Button login;

    Button register;

    boolean showpwd = false;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        context = this;
        initView();
    }

    public void initView() {

        login = (Button) findViewById(R.id.login);

        et_username = (EditText) findViewById(R.id.username);
        et_username.addTextChangedListener(new EditChangedListener());
        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);

        et_pwd = (EditText) findViewById(R.id.password);
        et_pwd.addTextChangedListener(new EditChangedListener());
        bt_pwd_eye = (Button) findViewById(R.id.bt_pwd_eye);
        bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);

        register = (Button) findViewById(R.id.register);

        bindOnClick();
    }

    public void bindOnClick() {

        bt_username_clear.setOnClickListener(onClick);
        bt_pwd_eye.setOnClickListener(onClick);
        bt_pwd_clear.setOnClickListener(onClick);

        login.setOnClickListener(onClick);

        register.setOnClickListener(onClick);
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

                case R.id.bt_pwd_clear:

                    et_pwd.setText("");
                    bt_pwd_eye.setVisibility(View.INVISIBLE);
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                    showpwd = false;

                    break;

                case R.id.login:

                    new LoginTask().execute();

                    break;
                case R.id.register:

                    Intent intent1 = new Intent();
                    intent1.setClass(context,registerActivity.class);
                    context.startActivity(intent1);
                    finish();

                    break;


                default:
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

            if (et_username.getText().length() > 0) {
                bt_username_clear.setVisibility(View.VISIBLE);
            } else {
                bt_username_clear.setVisibility(View.INVISIBLE);
            }

            if (et_pwd.getText().length() > 0) {

                bt_pwd_eye.setVisibility(View.VISIBLE);
                bt_pwd_clear.setVisibility(View.VISIBLE);

            } else {
                bt_pwd_eye.setVisibility(View.INVISIBLE);
                bt_pwd_clear.setVisibility(View.INVISIBLE);
            }

            if (et_username.getText().length() > 10 && et_pwd.getText().length() > 6) {
                login.setEnabled(true);
            } else {
                login.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


    class LoginTask extends AsyncTask<Void,Void,Map<String,Object>>{
        Map<String, Object> map;
        public LoginTask(){

            map = new HashMap<String, Object>();
            map.put("Tel",et_username.getText());
            map.put("Pwd",et_pwd.getText());

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(Void... params) {
            return HttpPostUtil.getPostJsonResult(url_login,map);
        }

        @Override
        protected void onPostExecute(Map<String, Object> result) {
            super.onPostExecute(result);
            if(!result.get("result").equals("null")){
                if(result.get("result").equals("1")){
                    showToast("账号不存在");
                }else  if(result.get("result").equals("2")){
                    showToast("密码错误");
                }else  if(result.get("result").equals("-1")){
                    showToast("系统出错，请重试");
                }else{
                    showToast("登录成功");
                    Intent intent = new Intent();
                    intent.setClass(context,MainActivity.class);
                    context.startActivity(intent);
                    finish();
                }

            }else{
                showToast(result.get("err").toString());
            }
        }
    }

    public void showToast(String msg){
        Toast.makeText(context,msg.toString(),Toast.LENGTH_SHORT).show();
    }

}
