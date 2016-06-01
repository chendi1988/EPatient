package com.example11.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class registerActivity extends Activity {

    EditText rg_username;
    Button bt_rg_username_clear;

    EditText rg_pwd;
    Button rg_pwd_eye;

    EditText rg_identify;
    static Button rg_identify_bt;

    Button register;

    boolean rgshowpwd = false;

    Context context;

    Handler msgHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    rg_identify_bt.setText(""+ num + "s后重新发送");
                    if(num < 0){
                        stopTimer();
                        num = 60;
                        rg_identify_bt.setEnabled(true);
                        rg_identify_bt.setText("重新发送");

                    }
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_layout);
        context = this;
        initView();
    }

    public void initView() {

        rg_identify_bt = (Button) findViewById(R.id.rg_identify_bt);

        register = (Button) findViewById(R.id.register);

        rg_username = (EditText) findViewById(R.id.rg_username);
        rg_username.addTextChangedListener(new EditChangedListener());
        bt_rg_username_clear = (Button) findViewById(R.id.rg_username_clear);

        rg_pwd = (EditText) findViewById(R.id.rg_password);
        rg_pwd.addTextChangedListener(new EditChangedListener());
        rg_pwd_eye = (Button) findViewById(R.id.rg_pwd_eye);

        rg_identify = (EditText) findViewById(R.id.rg_identify);

        bindOnClick();
    }

    public void bindOnClick() {

        bt_rg_username_clear.setOnClickListener(onClick);
        rg_pwd_eye.setOnClickListener(onClick);
        rg_identify_bt.setOnClickListener(onClick);

        register.setOnClickListener(onClick);
    }

    View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rg_username_clear:

                    rg_username.setText("");
                    bt_rg_username_clear.setVisibility(View.INVISIBLE);

                    break;

                case R.id.rg_pwd_eye:
                    rgshowpwd = !rgshowpwd;
                    if (rgshowpwd) {
                        rg_pwd_eye.setBackgroundResource(R.drawable.agu);
                        rg_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        rg_pwd_eye.setBackgroundResource(R.drawable.agt);
                        rg_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }

                    rg_pwd.postInvalidate();
                    //切换后将EditText光标置于末尾
                    CharSequence charSequence = rg_pwd.getText();
                    if (charSequence instanceof Spannable) {
                        Spannable spanText = (Spannable) charSequence;
                        Selection.setSelection(spanText, charSequence.length());
                    }

                    break;

                case R.id.register:

                    Intent intent = new Intent();
                    intent.setClass(context,MainActivity.class);
                    context.startActivity(intent);
                    finish();

                    break;

                case R.id.rg_identify_bt:
                    rg_identify_bt.setEnabled(false);
                    startTimer();
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

            if (rg_username.getText().length() > 0) {
                bt_rg_username_clear.setVisibility(View.VISIBLE);
            } else {
                bt_rg_username_clear.setVisibility(View.INVISIBLE);
            }

            if (rg_pwd.getText().length() > 0) {

                rg_pwd_eye.setVisibility(View.VISIBLE);

            } else {
                rg_pwd_eye.setVisibility(View.INVISIBLE);
            }

            if (rg_username.getText().length() > 10 && rg_pwd.getText().length() > 6 && rg_identify.getText().length() > 5) {
                register.setEnabled(true);
            } else {
                register.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    Timer timer = null;
    TimerTask tTask = null;

    public void startTimer(){

        if(timer == null){
            timer = new Timer();
        }

        if(tTask == null){

            tTask = new TimerTask() {

                @Override
                public void run() {
                    num --;
                    Message message = new Message();
                    message.what = 1;
                    msgHandler.sendMessage(message);
                }
            };
        }

        timer.schedule(tTask,0,1000);

    }

    public void stopTimer(){
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (tTask != null) {
            tTask.cancel();
            tTask = null;
        }
    }

    public int num = 60;

}
