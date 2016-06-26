package com.bean.yihuanton;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bean.adapters.AnimAdapterUtil;
import com.bean.utils.Contant;
import com.bean.utils.HttpPostUtil;
import com.bean.utils.Util_SharedPreferences;
import com.bean.view.DialogWaiting;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chendi on 2016/6/7.
 */
public class PersonalInfos extends Activity implements View.OnClickListener {

    RelativeLayout personal_real_name_item;
    RelativeLayout personal_sex_item;
    RelativeLayout personal_age_item;
    RelativeLayout personal_height_item;
    RelativeLayout personal_weight_item;
    RelativeLayout personal_phone_item;
    RelativeLayout personal_address_item;

    TextView personal_real_name;
    TextView personal_sex;
    TextView personal_age;
    TextView personal_height;
    TextView personal_weight;
    TextView personal_phone;
    TextView personal_address;

    Context context;
    Intent intent;

    Button save;

    DialogWaiting dialogWating;

    Button go_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_infos_layout);
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

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);

        SharedPreferences sp = Util_SharedPreferences.getInstance().getSharedPreferences(context, Contant.SP_USER);

        personal_real_name_item = (RelativeLayout) findViewById(R.id.personal_real_name_item);
        personal_real_name_item.setOnClickListener(this);
        personal_sex_item = (RelativeLayout) findViewById(R.id.personal_sex_item);
        personal_sex_item.setOnClickListener(this);
        personal_age_item = (RelativeLayout) findViewById(R.id.personal_age_item);
        personal_age_item.setOnClickListener(this);
        personal_height_item = (RelativeLayout) findViewById(R.id.personal_height_item);
        personal_height_item.setOnClickListener(this);
        personal_weight_item = (RelativeLayout) findViewById(R.id.personal_weight_item);
        personal_weight_item.setOnClickListener(this);
        personal_phone_item = (RelativeLayout) findViewById(R.id.personal_phone_item);
        personal_phone_item.setOnClickListener(this);
        personal_address_item = (RelativeLayout) findViewById(R.id.personal_address_item);
        personal_address_item.setOnClickListener(this);

        personal_real_name = (TextView) findViewById(R.id.personal_real_name);
        personal_real_name.setText(sp.getString("name", ""));
        personal_sex = (TextView) findViewById(R.id.personal_sex);
        String sex = sp.getString("sex", "");
        if (!sex.equals("") && sex != null) {
            personal_sex.setText(Integer.parseInt(sex) == 1 ? "男" : "女");
        }

        personal_age = (TextView) findViewById(R.id.personal_age);
        personal_age.setText(sp.getString("age", ""));
        personal_height = (TextView) findViewById(R.id.personal_height);
        personal_height.setText(sp.getString("height", ""));
        personal_weight = (TextView) findViewById(R.id.personal_weight);
        personal_weight.setText(sp.getString("weight", ""));
        personal_phone = (TextView) findViewById(R.id.personal_phone);
        personal_phone.setText(sp.getString("username", ""));
        personal_address = (TextView) findViewById(R.id.personal_address);
        personal_address.setText(sp.getString("address", ""));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.personal_real_name_item:

                intent = new Intent();
                intent.putExtra("item", 0);
                intent.putExtra("value", personal_real_name.getText().toString().trim());
                intent.setClass(context, PersonalInfoChangeActivity.class);
                startActivityForResult(intent, 0);
                AnimAdapterUtil.anim_translate_next(context);

                break;

            case R.id.personal_sex_item:

                showSexDialog();

                break;

            case R.id.personal_age_item:

                intent = new Intent();
                intent.putExtra("item", 2);
                intent.putExtra("value", personal_age.getText().toString().trim());
                intent.setClass(context, PersonalInfoChangeActivity.class);
                startActivityForResult(intent, 2);
                AnimAdapterUtil.anim_translate_next(context);

                break;

            case R.id.personal_height_item:

                intent = new Intent();
                intent.putExtra("item", 3);
                intent.putExtra("value", personal_height.getText().toString().trim());
                intent.setClass(context, PersonalInfoChangeActivity.class);
                startActivityForResult(intent, 3);
                AnimAdapterUtil.anim_translate_next(context);

                break;

            case R.id.personal_weight_item:

                intent = new Intent();
                intent.putExtra("item", 4);
                intent.putExtra("value", personal_weight.getText().toString().trim());
                intent.setClass(context, PersonalInfoChangeActivity.class);
                startActivityForResult(intent, 4);
                AnimAdapterUtil.anim_translate_next(context);

                break;

            case R.id.personal_phone_item:

                break;
            case R.id.personal_address_item:

                intent = new Intent();
                intent.putExtra("item", 6);
                intent.putExtra("value", personal_address.getText().toString().trim());
                intent.setClass(context, PersonalInfoChangeActivity.class);
                startActivityForResult(intent, 6);
                AnimAdapterUtil.anim_translate_next(context);

                break;

            case R.id.save:

                new UploadPersonalInfos().execute();

                break;
        }
    }

    class UploadPersonalInfos extends AsyncTask<Void, Void, String> {

        Map<String, String> map;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (dialogWating == null) {
                dialogWating = new DialogWaiting(context, R.style.dialogwaitingstyle);
            }

            if (!dialogWating.isShowing()) {
                dialogWating.show();
                dialogWating.setContent("提交中...");
            }


            map = new HashMap<String, String>();
            map.put("phone", personal_phone.getText().toString());
            map.put("name", personal_real_name.getText().toString());

            String sex = personal_sex.getText().toString();
            if (!sex.equals("") && sex != null) {
                map.put("sex", sex.equals("男") ? "1" : "0");
            }

            map.put("age", personal_age.getText().toString());
            map.put("height", personal_height.getText().toString());
            map.put("weight", personal_weight.getText().toString());
            map.put("address", personal_address.getText().toString());

        }

        @Override
        protected String doInBackground(Void... params) {
            return HttpPostUtil.getPostJsonResult(Contant.URL_UPDATE_INFO, map, "");
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
                    if (resultStr.optString("status").equals("100")) {

                        Util_SharedPreferences.getInstance().setItemsDataByMap(context, Contant.SP_USER, map);

                        finish();
                        AnimAdapterUtil.anim_translate_back(context);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case 0:

                personal_real_name.setText(data.getStringExtra("key"));

                break;

            case 2:

                personal_age.setText(data.getStringExtra("key"));

                break;

            case 3:

                personal_height.setText(data.getStringExtra("key"));

                break;

            case 4:

                personal_weight.setText(data.getStringExtra("key"));

                break;

            case 6:

                personal_address.setText(data.getStringExtra("key"));

                break;

            default:
                break;
        }
    }


    private void showSexDialog() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertdialog);
        LinearLayout ll_title = (LinearLayout) window
                .findViewById(R.id.ll_title);
        ll_title.setVisibility(View.VISIBLE);
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText("性别");
        // 为确认按钮添加事件,执行退出应用操作
        TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);
        tv_paizhao.setText("男");
        tv_paizhao.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                personal_sex.setText("男");
                dlg.cancel();
            }
        });
        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
        tv_xiangce.setText("女");
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                personal_sex.setText("女");
                dlg.cancel();

            }
        });

    }
}
