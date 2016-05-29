package com.example11.myapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example11.fragments.Fragment_Main_01;
import com.example11.fragments.Fragment_Main_02;
import com.example11.fragments.Fragment_Main_03;
import com.example11.fragments.Fragment_Main_04;

public class MainActivity extends FragmentActivity {
	// 未读消息textview
	private TextView unreadLabel;
	// 未读通讯录textview
	TextView unreadAddressLable;
	protected static final String TAG = "MainActivity";

	private Fragment[] fragments;
	public Fragment_Main_01 homefragment;
	private Fragment_Main_02 contactlistfragment;
	private Fragment_Main_03 findfragment;
	private Fragment_Main_04 profilefragment;
	private ImageView[] imagebuttons;
	private TextView[] textviews;
	private int index;
	// 当前fragment的index
	private int currentTabIndex;

	private TextView titlemsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mian_temp);
		initView();

	}

	private void initView() {
		titlemsg = (TextView) findViewById(R.id.titlemsg);

		unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
		unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);

		homefragment = new Fragment_Main_01();
		contactlistfragment = new Fragment_Main_02();
		findfragment = new Fragment_Main_03();
		profilefragment = new Fragment_Main_04();
		fragments = new Fragment[] { homefragment, contactlistfragment,
				findfragment, profilefragment };
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
		textviews[0].setTextColor(0xFF45C01A);
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, homefragment)
				.add(R.id.fragment_container, contactlistfragment)
				.add(R.id.fragment_container, profilefragment)
				.add(R.id.fragment_container, findfragment)
				.hide(contactlistfragment).hide(profilefragment)
				.hide(findfragment).show(homefragment).commit();

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
		textviews[index].setTextColor(0xFF45C01A);
		currentTabIndex = index;
	}

}
