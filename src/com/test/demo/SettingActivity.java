package com.test.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.test.adapters.AnimAdapterUtil;
import com.test.baseactivity.BaseFragmentActivity;
import com.test.utils.CheckFormatUtil;
import com.test.utils.Contant;
import com.test.utils.ToastUtil;
import com.test.utils.Util_SharedPreferences;

/**
 * Created by chendi on 2016/5/30.
 */
public class SettingActivity extends BaseFragmentActivity {

    Button go_back;

    RelativeLayout changepwd;
    RelativeLayout aboutus;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        context = this;

        go_back = (Button) findViewById(R.id.go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initView();
    }

    public void initView() {
        changepwd = (RelativeLayout) findViewById(R.id.changepwd);
        changepwd.setOnClickListener(onClick);

        aboutus = (RelativeLayout) findViewById(R.id.aboutus);
        aboutus.setOnClickListener(onClick);

    }


    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.changepwd:

                    Intent intent = new Intent();
                    String phone = Util_SharedPreferences.getInstance().getItemDataByKey(context, Contant.SP_USER, "phone");
                    if (CheckFormatUtil.isPhoneNum(phone)) {
                        intent.putExtra("phone", phone);
                        intent.putExtra("type", 2);
                        intent.setClass(context, RegisteActivity.class);
                        startActivityForResult(intent, 0);
                        AnimAdapterUtil.anim_translate_next(context);
                    } else {
                        ToastUtil.showToast(context, "缓存手机号码有误，请退出后重新登陆操作");
                    }


                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                ToastUtil.showToast(context, "修改成功");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AnimAdapterUtil.anim_translate_back(this);
    }
}
