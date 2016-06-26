/**
 * 南京熙健 ecg 开发支持库
 * Copyright (C) 2015 mhealth365.com All rights reserved.
 * create by lc  2015年6月16日 上午9:56:01
 */


package com.mhealth365.ecgsdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.bean.yihuanton.R;
import com.mhealth365.osdk.EcgOpenApiCallback.LOGIN_FAIL_MSG;
import com.mhealth365.osdk.EcgOpenApiCallback.LoginCallback;
import com.mhealth365.osdk.EcgOpenApiHelper;

/**登陆*/
public class LoginActivity extends Activity{

	Button btnLogin;
	EditText etOpenId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_ecg_sdk_demo_login);
		
		etOpenId = (EditText) findViewById(R.id.editOpenId);
		
		btnLogin = (Button) findViewById(R.id.login_button);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = etOpenId.getText().toString().trim();
				
//				if(id!=null&&!id.equals("")){
					EcgOpenApiHelper mHelper = EcgOpenApiHelper.getInstance();
					mHelper.login(id, mLoginCallback);
//				}
				
			}
		});
		
	}
	
	
	LoginCallback mLoginCallback = new LoginCallback() {
		
		@Override
		public void loginOk() {
			ToastText("登陆成功");
		}
		

		@Override
		public void loginFailed(LOGIN_FAIL_MSG msg) {
			String text = "";
			if(msg==null){
				text = "未知异常";
			}else{
				switch (msg) {
				case LOGIN_FAIL_NO_NET:
					text = "无网络";
					break;
				case LOGIN_FAIL_NO_OPENID:
					text = "OpenId为空值";
					break;
				case LOGIN_FAIL_NO_RESPOND:
					text = "服务器未响应";
					break;
				case LOGIN_FAIL_NO_USER:
					text = "无此用户";
					break;
				case LOGIN_FAIL_OSDK_INIT_ERROR:
					text = "sdk初始化异常";
					break;
				//20150716----------------------------
				case SYS_0:
					text ="系统错误";//系统错误
					break;
				case SYS_USER_EXIST_E:
					text ="注册用户已回收";//Openid存在，但是账号已回收
					break;
				case SYS_THIRD_PARTY_ID_CHECKING:
					text ="公司id审核中";//thiredpartyId存在，正在审核未生效
					break;
				case SYS_THIRD_PARTY_ID_NOT_EXIST:
					text ="公司id不存在";//thiredpartyId不存在
					break;
				case SYS_APP_ID_CHECKING:
					text ="appid审核中";//appid存在，正在审核未生效
					break;
				case SYS_APP_ID_ERROR:
//					text ="appid不存在，或者appSecret有错误";//appid不存在，或者appSecret有错误
					text ="包名 appId 公司id 不匹配";//TODO 包名 appId 公司id 不匹配
					break;
				case SYS_APP_PACKAGE_ID_NOT_EXIST:
					text ="包名不存在";//包名不正确
					break;
				case SYS_LOW_VERSION:
					text ="sdk版本低需要升级";
					break;
				default:
					break;
				}
				Log.v("Login", "loginFailed:"+msg.name());
			}
			
			
			ToastText("登陆失败 "+text);
			
		}
	};
	
	final static int TOAST_TEXT = 10002;

	/**显示刷新*/
	Handler displayMessage = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			
			int what = msg.what;
			switch (what) {
			case TOAST_TEXT:{
				String text = (String) msg.obj;
				if(text!=null)Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
			}break;
			default:
				break;
			}
			
		}

		
	};
	
	
	
	public void ToastText(String text){
		displayMessage.obtainMessage(TOAST_TEXT, text).sendToTarget();
	}
	

}
