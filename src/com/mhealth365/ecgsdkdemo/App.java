
/**
 * 南京熙健 ecg 开发支持库
 * Copyright (C) 2015 mhealth365.com All rights reserved.
 * create by lc  2015年6月16日 上午9:56:01
 */
package com.mhealth365.ecgsdkdemo;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import com.mhealth365.osdk.EcgOpenApiCallback.OsdkCallback;
import com.mhealth365.osdk.EcgOpenApiHelper;

import java.io.IOException;

public class App extends Application{
	
//    appPack  : com.bean.yihuanton
	
	//com.app.mhealth
    private final String thirdPartyId = "e361102fa8a6faed46ee311375f12ed4";
	private final String appId = "620a7037c7b26af2df9751e449fdb54d";
	private final String appSecret = "";
//	private final String thirdPartyId = "a691dd5a92484bafa7e65215d2e87b9f";
//	private final String appId = "39bc3b65e12641e49612bba4a9b38a04";
//	private final String appSecret = "d5855a24a8244ddd8efd04d170f41f35";
	private final String UserOrgName = "南京熙健";//20150923修改定义 机构（厂商）名称，（自定义）长度不超过25字节,不为空
	//20150831,增加保存数据和读取保存数据
	
	
	private static Context mAppContext;
	private static App app;
	private static int versionCode = 0;
	private static String versionName = "";
	static String APPNAME = "";
	private CrushWriter crushWriter ;

	private static final String TAG = "JPush";


	@Override
	public void onCreate() {
		super.onCreate();

		Log.d(TAG, "[ExampleApplication] onCreate");
		super.onCreate();

		JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
		JPushInterface.init(this);     		// 初始化 JPush
		
		crushWriter = new CrushWriter();
		app = this;
		mAppContext = getApplicationContext();
		hasSystemFeatureCheck();
		String pn = getPackageName();
		APPNAME = "sdk-demo";
//		initDir();
		try {
			versionCode = mAppContext.getPackageManager().getPackageInfo(
					pn, 0).versionCode;
			versionName = mAppContext.getPackageManager().getPackageInfo(
					pn, 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		
		EcgOpenApiHelper mHelper = EcgOpenApiHelper.getInstance();
		Log.i("App", "--- thirdPartyId:"+thirdPartyId);
		Log.i("App", "--- appId:"+appId);
		Log.i("App", "--- appSecret:"+appSecret);
		Log.i("App", EcgOpenApiHelper.getLibsVersion());
		try {
			mHelper.initOsdk(mAppContext, thirdPartyId, appId, appSecret,UserOrgName, mOsdkCallback);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void finishSdk() throws IOException{
		EcgOpenApiHelper mHelper = EcgOpenApiHelper.getInstance();
		mHelper.finishSdk();
	}
	
	public static App getApp(){
		return app;
	}
	
	OsdkCallback displayMessage;
	public void setOsdkCallback(OsdkCallback osdkCallback){
		displayMessage = osdkCallback;
	}
	
	OsdkCallback mOsdkCallback = new OsdkCallback() {
		
		@Override
		public void usbSocketLost() {
			if(displayMessage!=null)displayMessage.usbSocketLost();
		}
		
		@Override
		public void usbSocketConnect() {
			if(displayMessage!=null)displayMessage.usbSocketConnect();
		}
		
		@Override
		public void usbPlugOut() {
			if(displayMessage!=null)displayMessage.usbPlugOut();
		}
		
		@Override
		public void usbPlugIn() {
			if(displayMessage!=null)displayMessage.usbPlugIn();
		}
		
		@Override
		public void deviceReady(int sample) {
			if(displayMessage!=null)displayMessage.deviceReady(sample);
		}
		@Override
		public void deviceNotReady(int msg) {
			if(displayMessage!=null)displayMessage.deviceNotReady(msg);
		}
	};

	
	public void onTerminate() {
//		if(mLogcatHelper!=null){
//			mLogcatHelper.stop();
//		}
		try {
			App.finishSdk();//释放sdk所有资源【不可恢复】
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	
	
	
	void hasSystemFeatureCheck(){
		StringBuffer sb = new StringBuffer();

		fileString("usb 从", hasSystemFeature_USB_ACCESSORY(), sb);
		fileString("usb 主", hasSystemFeature_USB_HOST(), sb);
		
		Log.i("App", sb.toString());
	}
	
	void fileString(String name,boolean yes,StringBuffer sb){
		sb.append("\n");
		sb.append(name);
		sb.append("：");
		if(yes){
			sb.append("YES");
		}else{
			sb.append("NO");
		}
		sb.append("\n");
		
	}
	
	
	public static Context getEcgContext() {
		return mAppContext;
	}
	
	public static String getVersionName() {
		return versionName;
	}
	
	public static String getVersion() {
		return versionName + "(" + versionCode + ")";
	}
	
	public static String AppName(){
		return APPNAME;
	}
	

	public static boolean hasSystemFeature_USB_HOST(){
		return mAppContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_HOST);
	}
	
	public static boolean hasSystemFeature_USB_ACCESSORY(){
		return mAppContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_ACCESSORY);
	}
	
    
	public static void killProcess() {
		Log.v("activity", "kill process------------1");
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	
}
