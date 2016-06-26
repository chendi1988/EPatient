package com.bean.yihuanton;

import android.content.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bean.adapters.AnimAdapterUtil;
import com.bean.fragments.Fragment01;
import com.bean.fragments.Fragment_Main_02;
import com.bean.fragments.Fragment_Main_04;
import com.bean.view.CustomDialog;
import com.bean.fragments.Fragment_Main_03;
import com.bean.utils.Contant;
import com.bean.utils.Util_SharedPreferences;

public class MainActivity extends FragmentActivity implements Fragment_Main_03.LoginInterface {
    // 未读消息textview
    private TextView unreadLabel;
    // 未读通讯录textview
    TextView unreadAddressLable;
    protected static final String TAG = "MainActivity";

    private Fragment[] fragments;

    public Fragment01 fragment01;

    //    public Fragment_Main_01 homefragment;
    private Fragment_Main_02 contactlistfragment;
    private Fragment_Main_03 findfragment;
    private Fragment_Main_04 profilefragment;

    private ImageView[] imagebuttons;
    private TextView[] textviews;
    private int index;
    // 当前fragment的index
    private int currentTabIndex;

    private TextView titlemsg;

    //网络断开提示
    private RelativeLayout netErrItem;

    NetBReciver netBReciver;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian_temp);
        context = this;
        initView();
        netBReciver = new NetBReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netBReciver, intentFilter);

    }

    private void initView() {


        titlemsg = (TextView) findViewById(R.id.titlemsg);

        netErrItem = (RelativeLayout) findViewById(R.id.error_item);
        netErrItem.setOnClickListener(onClick);

        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);

//        homefragment = new Fragment_Main_01();
        contactlistfragment = new Fragment_Main_02();
        findfragment = new Fragment_Main_03();
        profilefragment = new Fragment_Main_04();

        fragment01 = new Fragment01();

        fragments = new Fragment[]{fragment01, contactlistfragment,
                findfragment, profilefragment};

        imagebuttons = new ImageView[4];
        imagebuttons[0] = (ImageView) findViewById(R.id.ib_weixin);
        imagebuttons[1] = (ImageView) findViewById(R.id.ib_contact_list);
        imagebuttons[2] = (ImageView) findViewById(R.id.ib_find);
        imagebuttons[3] = (ImageView) findViewById(R.id.ib_profile);

        imagebuttons[0].setSelected(true);
        textviews = new TextView[4];
        textviews[0] = (TextView) findViewById(R.id.tv_weixin);
        textviews[1] = (TextView) findViewById(R.id.tv_contact_list);
        textviews[2] = (TextView) findViewById(R.id.tv_find);
        textviews[3] = (TextView) findViewById(R.id.tv_profile);
//        textviews[0].setTextColor(0xFF45C01A);
        textviews[0].setTextColor(context.getResources().getColor(R.color.drop_down_selected));
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment01)
                .add(R.id.fragment_container, contactlistfragment)
                .add(R.id.fragment_container, profilefragment)
                .add(R.id.fragment_container, findfragment)
                .hide(contactlistfragment).hide(profilefragment)
                .hide(findfragment).show(fragment01).commit();

    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.re_weixin:
                index = 0;
                titlemsg.setText("E患通");
                break;
            case R.id.re_contact_list:
                index = 1;
                titlemsg.setText("商城");
                break;
            case R.id.re_find:
                index = 2;
                titlemsg.setText("我的");
                break;
            case R.id.re_profile:
                index = 3;
                titlemsg.setText("健康园");
                break;

        }

        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(0xFF999999);
//        textviews[index].setTextColor(0xFF45C01A);
        textviews[index].setTextColor(context.getResources().getColor(R.color.drop_down_selected));
        currentTabIndex = index;

    }


    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.error_item:
                    Toast.makeText(context, "点击网络错误提示", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            //登录成功
            findfragment.initViewData();
        }
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();

//				Util_SharedPreferences.getInstance().clearData(context,Contant.SP_USER);
            } else {
                moveTaskToBack(false);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


//    @Override
//    public void initTab() {
//
//        index = 0;
//
//        if (currentTabIndex != index) {
//            FragmentTransaction trx = getSupportFragmentManager()
//                    .beginTransaction();
//            trx.hide(fragments[currentTabIndex]);
//            if (!fragments[index].isAdded()) {
//                trx.add(R.id.fragment_container, fragments[index]);
//            }
//            trx.show(fragments[index]).commit();
//        }
//        imagebuttons[currentTabIndex].setSelected(false);
//        // 把当前tab设为选中状态
//        imagebuttons[index].setSelected(true);
//        textviews[currentTabIndex].setTextColor(0xFF999999);
//        textviews[index].setTextColor(0xFF45C01A);
//        currentTabIndex = index;
//
//    }

    public void initTab0(int index1) {

        index = index1;
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(0xFF999999);
        textviews[index].setTextColor(0xFF45C01A);
        currentTabIndex = index;

    }

    @Override
    public void needLogin() {

        showAlertDialog(1);

    }

    @Override
    public void loginOut() {

        showAlertDialog(0);

    }

    @Override
    public void startLogin() {

        Intent intent = new Intent();
        intent.putExtra("target", Contant.START_PERSONAL_ACTIVITY);
        intent.setClass(context, LoginActivity.class);
        startActivityForResult(intent, 0);
        AnimAdapterUtil.anim_translate_next(context);


    }

    public void showAlertDialog(int type) {

        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("提示");
        if (type == 1) {
            builder.setMessage("先登录才能查询相关数据");
            builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //设置你的操作事项
                    startLogin();
                }
            });
        } else if (type == 0) {
            builder.setMessage("是否退出？");
            builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //设置你的操作事项
                    Util_SharedPreferences.getInstance().clearData(context, Contant.SP_USER);
                    findfragment.initViewData();
                }
            });

        }

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();

    }

    /**
     * 新消息广播接收者
     */
    private class NetBReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e(TAG, "网络状态改变");

            ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                Log.i(TAG, "unconnect");
                // unconnect network
                netErrItem.setVisibility(View.VISIBLE);
            } else {
                // connect network
                netErrItem.setVisibility(View.GONE);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (netBReciver != null) {
            unregisterReceiver(netBReciver);
        }
    }
}
