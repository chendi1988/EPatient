package com.example11.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import com.example11.myapp.R;
import com.example11.utils.Contant;
import com.example11.utils.HttpPostUtil;
import com.example11.utils.ToastUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chendi on 2016/6/8.
 */
public class Fragment_Verify_Verify extends Fragment {

    TextView send_verify;

    TextView phoneNoti;

    Button next;

    EditText verify;

    Handler msgHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    send_verify.setText("" + num + "s后重新发送");
                    if (num < 0) {
                        stopTimer();
                        num = 60;
                        send_verify.setEnabled(true);
                        send_verify.setText("发送验证码");
                    }
                    break;

                default:
                    break;
            }
        }
    };

    Bundle bundle;

    boolean isFirst = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_verify_verify, null);
        bundle = getArguments();
        phoneNoti = (TextView) view.findViewById(R.id.phone_noti);
        phoneNoti.setText("我们已为你的手机 " + bundle.getString("phone") + " 发送了验证短信");
        send_verify = (TextView) view.findViewById(R.id.send_verify);
        send_verify.setOnClickListener(onClick);
        verify = (EditText) view.findViewById(R.id.verify);
        verify.addTextChangedListener(new EditChangedListener());

        next = (Button) view.findViewById(R.id.next);
        next.setOnClickListener(onClick);

        if(isFirst){
            isFirst =!isFirst;
            startTimer();
        }

        return view;
    }


    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.send_verify:
                    new RequestVerify(bundle.getString("phone")).execute();
                    startTimer();
                    break;
                case R.id.next:

//                    Bundle vBundle = new Bundle();
//                    bundle.putString("phone",bundle.getString("phone"));
                    bundle.putString("verify",verify.getText().toString().trim());

                    Fragment_Verify_pwd fragmentVerifyPwd = new Fragment_Verify_pwd();
                    fragmentVerifyPwd.setArguments(bundle);

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();

                    ft.replace(R.id.register, fragmentVerifyPwd);
                    ft.addToBackStack(null);
                    ft.commit();

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

            if (verify.getText().length() > 0) {
                next.setEnabled(true);
            } else {
                next.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    Timer timer = null;
    TimerTask tTask = null;

    public void startTimer() {

        if (timer == null) {
            timer = new Timer();
        }

        if (tTask == null) {

            tTask = new TimerTask() {

                @Override
                public void run() {
                    num--;
                    Message message = new Message();
                    message.what = 1;
                    msgHandler.sendMessage(message);
                }
            };
        }

        timer.schedule(tTask, 0, 1000);

    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (tTask != null) {
            tTask.cancel();
            tTask = null;
        }

//        num = 60;
//        send_verify.setEnabled(true);
//        send_verify.setText("发送验证码");
    }

    public int num = 60;


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
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optString("Status").equals("100")) {

                        ToastUtil.showToast(getActivity(), "验证码已发送");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                ToastUtil.showToast(getActivity(), "网络请求失败，请稍后尝试");

            }

        }
    }

}
