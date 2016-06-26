/**
 * 南京熙健 ecg 开发支持库
 * Copyright (C) 2015 mhealth365.com All rights reserved.
 * create by lc  2015年6月16日 上午9:56:01
 */
package com.mhealth365.ecgsdkdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bean.yihuanton.R;
import com.mhealth365.ecgsdkdemo.file.EcgDataSource;
import com.mhealth365.ecgsdkdemo.file.EcgFile;
import com.mhealth365.osdk.EcgOpenApiCallback;
import com.mhealth365.osdk.EcgOpenApiCallback.OsdkCallback;
import com.mhealth365.osdk.EcgOpenApiCallback.RECORD_FAIL_MSG;
import com.mhealth365.osdk.EcgOpenApiCallback.RecordCallback;
import com.mhealth365.osdk.EcgOpenApiHelper;
import com.mhealth365.osdk.EcgOpenApiHelper.RECORD_MODE;
import com.mhealth365.osdk.ecgbrowser.EcgBrowserInteractive;
import com.mhealth365.osdk.ecgbrowser.RealTimeEcgBrowser;
import com.mhealth365.osdk.ecgbrowser.Scale;

import java.io.File;
import java.io.IOException;
/**
 * usb 设备特别的处理：
 * 启动项：在AndroidManifest.xml 里添加参数
 * 		res 中添加匹配usb的参数文件
 *
 * activity里处理收到插入设备消息的调用，在onResume中
 *
 * */
public class EcgSdkDemo extends Activity{
	
	EcgDataSource demoData = null;
	
	int ecgSample = 0;
	int countEcg = 0;
	EcgOpenApiHelper mHelper = null;

	TextView hr,rr,speed,gain,result,counter,ver;
	private static final int DIALOG_OPEN_DATA = 1;
	
	void initSdk(){
		mHelper = EcgOpenApiHelper.getInstance();
		App.getApp().setOsdkCallback(mOsdkCallback);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ecg_sdk_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.smallBrowser){
        	initEcgSmall();
        }else if(id==R.id.datasource){
        	Intent datasource = new Intent(this, EcgDataSourceReviewActivity.class);
			startActivity(datasource);
        }else if(id==R.id.printrecord){
        	
        	
        }else{
        	 float screenInch = 5;
			//手动设置屏幕分辨率
             String value = (String) item.getTitle();
             screenInch = Float.parseFloat(value.trim());
             DisplayMetrics dm = getResources().getDisplayMetrics();
             mEcgBrowser.setScreenDPI(screenInch, dm.widthPixels, dm.heightPixels);
             mEcgBrowser.clearEcg();
        }
        
        return super.onOptionsItemSelected(item);
    }

	final static String tag = EcgSdkDemo.class.getSimpleName();

	Button mBtnRegister,mBtnLogin;
	Button mButtonRecordStart;
	Button mButtonRecordStop;
	RealTimeEcgBrowser mEcgBrowser;
//	RealTimeEcgBrowser mEcgBrowserSmall;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		requestWindowFeature(Window.FEATURE_NO_TITLE); 
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); 

		setContentView(R.layout.activity_ecg_demo);
		mEcgBrowser = (RealTimeEcgBrowser) findViewById(R.id.ecgBrowser);
		mEcgBrowser.setEcgBrowserInteractive(mEcgBrowserInteractive);
//		mEcgBrowserSmall = (RealTimeEcgBrowser) findViewById(R.id.ecgBrowserSmall);
		

		mButtonRecordStart = (Button) findViewById(R.id.button_record_start);
		mButtonRecordStop = (Button) findViewById(R.id.button_record_stop);
		mBtnRegister = (Button) findViewById(R.id.button_register);
		mBtnLogin = (Button) findViewById(R.id.button_login);
		mBtnLogin.setOnClickListener(buttonOnClickListener);
		mBtnRegister.setOnClickListener(buttonOnClickListener);

		mButtonRecordStart.setOnClickListener(buttonOnClickListener);
		mButtonRecordStop.setOnClickListener(buttonOnClickListener);

		if(!App.hasSystemFeature_USB_HOST()){
			Toast.makeText(this, "系统不支持usb host，无法连接设备", Toast.LENGTH_LONG).show();
		}
		initLable();
		initSdk();
		initEcg();
//		initEcgSmall();
	}
	
	
	void initLable(){
		hr = (TextView) findViewById(R.id.label_heartrate_realtime);
		rr = (TextView) findViewById(R.id.label_rr_value);
		speed =  (TextView) findViewById(R.id.label_speed);
		gain =  (TextView) findViewById(R.id.label_gain);
		result = (TextView) findViewById(R.id.label_result);
		counter = (TextView) findViewById(R.id.label_counter);
		ver = (TextView) findViewById(R.id.label_ver_value);
		ver.setText(EcgOpenApiHelper.ver+"("+EcgOpenApiHelper.doc+")");
		clearValue();
	}
	
	void clearValue(){
		hr.setText("---");
		rr.setText("----");
		speed.setText("");
		gain.setText("");
		String msg = "平均心率：--- (bpm),心率正常范围： -- %"+",节律正常范围：-- %";
		result.setText(msg);
		counter.setText("--");
		mEcgBrowser.clearEcg();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.v(getClass().getSimpleName(), "onRestart");
	}

	public void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v(getClass().getSimpleName(), "onResume");
		init();
	}
	private void init(){
		mHelper.notifyUSBDeviceAttach();
		if(mHelper.isDeviceReady()){
			setEcgSample(mHelper.getEcgSample());
		}
	}
	public void setEcgSample(int sample){
		this.ecgSample = sample;
		mEcgBrowser.setSample(sample);
	}
	
	
	public void initEcg() {
		
		mEcgBrowser.setSpeedAndGain( Scale.SPEED_25MM_S,Scale.GAIN_10MM_MV);// 设置增益和走速
		mEcgBrowser.setSample(500);
		mEcgBrowser.showFps(true);
		mEcgBrowser.setScreenDPI(getDPI());
		mEcgBrowser.clearEcg();

	}
	private boolean isSmall = false;
	public void initEcgSmall() {
		
		if(!isSmall){
			mEcgBrowser.setBackgroundColor(Color.LTGRAY);
			mEcgBrowser.setEcgColor(Color.WHITE);
			mEcgBrowser.setGridVisible(false);
			mEcgBrowser.setStandRectVisible(false);
			mEcgBrowser.setSpeedGainVisible(false);
			mEcgBrowser.setOpenTouch(false);
			mEcgBrowser.setScreenDPI(300);
			mEcgBrowser.clearEcg();
		}else{
			mEcgBrowser.setBackgroundColor(Color.WHITE);
			mEcgBrowser.setEcgColor(Color.BLACK);
			mEcgBrowser.setGridVisible(true);
			mEcgBrowser.setStandRectVisible(true);
			mEcgBrowser.setSpeedGainVisible(true);
			mEcgBrowser.setOpenTouch(true);
			DisplayMetrics dm = getResources().getDisplayMetrics();
			mEcgBrowser.setScreenDPI(dm.xdpi);
			mEcgBrowser.clearEcg();
		}
		
		isSmall = !isSmall;
		

	}
	
	OsdkCallback mOsdkCallback = new OsdkCallback() {
		
		@Override
		public void usbSocketLost() {
			Log.i("Demo", "OsdkCallback --- usbSocketLost");
			ToastText("usb设备连接断开！");
		}
		
		@Override
		public void usbSocketConnect() {
			Log.i("Demo", "OsdkCallback --- usbSocketConnect");
			ToastText("usb设备已连接！");
		}
		
		@Override
		public void usbPlugOut() {
			// TODO Auto-generated method stub
			Log.i("Demo", "OsdkCallback --- usbPlugOut");
			ToastText("usb设备拔出！");
		}
		
		@Override
		public void usbPlugIn() {
			// TODO Auto-generated method stub
			Log.i("Demo", "OsdkCallback --- usbPlugIn");
			ToastText("usb设备插入！");
		}
		
		@Override
		public void deviceReady(int sample) {
			if(sample<=0){
				EcgSdkDemo.this.ecgSample = 0;
			}else{
				// TODO Auto-generated method stub
				ToastText("心电设备已准备好！");//可以开始记录心电图
				Log.i("App", "OsdkCallback --- deviceReady sample:"+sample);
//				mEcgBrowser.setSample(sample);//设备采样率参数
//				EcgSdkDemo.this.ecgSample = sample;
				setEcgSample(sample);
			}
			
			
		}
		
		@Override
		public void deviceNotReady(int msg) {
			switch (msg) {
			case EcgOpenApiCallback.DEVICE_NOT_READY_NOT_SUPPORT_DEVICE://不支持设备
//				sdk不支持型号
				ToastText("褰撳墠sdk璁惧鏃犳硶浣跨敤姝ゅ瀷鍙疯澶�");
				break;
			case EcgOpenApiCallback.DEVICE_NOT_READY_UNKNOWN_DEVICE:////未知设备
				ToastText("设备无法使用");//设备故障或者非熙健产品
				break;
			default:
				break;
			}
//			ToastText("设备无法使用");//设备故障或者是sdk不支持型号
		}
	};
	
	
	
	public float getDPI(){
		return  mEcgBrowser.getDisplayDPI();
	}

	EcgBrowserInteractive mEcgBrowserInteractive = new EcgBrowserInteractive() {
		
		@Override
		public void onChangeGainAndSpeed(int gain, int speed) {
			displayMessage.obtainMessage(ECG_GAIN_SPEED,gain,speed).sendToTarget();
		}
	};
	
	
	RecordCallback  mRecordCallback  = new RecordCallback() {
		
		@Override
		public void recordTime(int second) {
			 Log.w(getClass().getSimpleName(), "recordTime--- second="+second);
			 
			 displayMessage.obtainMessage(ECG_COUNTER, second, -1).sendToTarget();
		}
		
		@Override
		public void recordStatistics(String id, int averageHeartRate,
				int normalRange, int suspectedRisk) {
//			Log.w(getClass().getSimpleName(), "recordStatistics--- averageHeartRate="+averageHeartRate);
//	
			if(null!=id){
				//FIXME 节律异常范围，修改为节律正常范围
				String msg = "平均心率："+averageHeartRate+"(bpm),心率正常范围："+normalRange+"%"+",节律正常范围："+suspectedRisk+"%";
				displayMessage.obtainMessage(ECG_STAISTICS_RESULT, msg).sendToTarget();
				ToastText("统计分析完成");
			}else{
				String msg = "【统计数据异常】";//一般是数据文件错误引起
				displayMessage.obtainMessage(ECG_STAISTICS_RESULT, msg).sendToTarget();
				
			}
			
			
		}
		
		@Override
		public void recordStart(String id) {
			 Log.w(getClass().getSimpleName(), "recordStart--- id="+id);
			 
			 Log.w(getClass().getSimpleName(), "recordStart--- countEcg="+countEcg);
			 
			 try {
				demoData = new EcgDataSource(System.currentTimeMillis(), ecgSample);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				ToastText("创建记录失败，ecgSample："+ecgSample);
			}
		}
		
		@Override
		public void recordEnd(String id) {
			if(id==null){
				ToastText("关闭记录，未生成有效数据");
			}else{
				ToastText("记录结束，开始统计分析");
			}
			Log.w(getClass().getSimpleName(), "recordEnd--- id="+id);
			 Log.w(getClass().getSimpleName(), "recordEnd--- countEcg="+countEcg);
			if(demoData!=null){
				String rootDir = Environment.getExternalStorageDirectory()
						.getAbsolutePath()+"/EcgSdkDemo/";
				File file = new File(rootDir);
				if(!file.exists()){
					file.mkdirs();
				}
				String filename = rootDir+System.currentTimeMillis()+".ecg";
				File demoFile = new File(filename);
				if(demoFile.exists()){
					demoFile.delete();
				}
				try {
					Log.w(getClass().getSimpleName(), "recordEnd--- demoData:"+demoData.toString());
					boolean ok = EcgFile.write(demoFile, demoData);
					
					if(ok){
						showData(filename);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				demoData = null;
			}
		}
		
		@Override
		public void heartRate(int hr) {
			Log.w(getClass().getSimpleName(), "heartRate--- hr="+hr);
			
			displayMessage.obtainMessage(ECG_HEART, hr, -1).sendToTarget();
		}
		
		@Override
		public void ecg(int[] value) {
			countEcg++;
//			Log.w(getClass().getSimpleName(), "ecg--- "+value[0]);
			mEcgBrowser.ecgPackage(value);
			
			if(demoData!=null)demoData.addPackage(value);
		}
		
		@Override
		public void RR(int ms) {
			Log.v(getClass().getSimpleName(), "RR--- rr="+ms);
			displayMessage.obtainMessage(ECG_RR, ms, -1).sendToTarget();
		}

		@Override
		public void startFailed(RECORD_FAIL_MSG msg) {
			Log.e(getClass().getSimpleName(), "startFailed--- "+msg.name());
			String text ="";
			switch (msg) {
			case RECORD_FAIL_A_RECORD_RUNNING:
				text ="已经开始记录了";
				break;
			case RECORD_FAIL_DEVICE_NO_RESPOND:
				text ="设备没有响应";//设备没有响应控制指令，可以重试
				break;
			case RECORD_FAIL_DEVICE_NOT_READY:
				text ="设备没有准备好";//设备未插入，或者未被识别
				break;
			case RECORD_FAIL_NOT_LOGIN:
				text ="还没有登陆";
				break;
			case RECORD_FAIL_OSDK_INIT_ERROR:
				text ="osdk没有初始化";
				break;
			case RECORD_FAIL_PARAMETER:
				text ="参数错误";
				break;
			default:
				break;
			}

			ToastText("开始记录失败："+text);
			
		}
	};
	
	String showDataFile = null;
	private void showData(String demoFile){
		displayMessage.obtainMessage(ECG_SHOW_DATA, demoFile).sendToTarget();
		
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
            return new AlertDialog.Builder(EcgSdkDemo.this)
                .setIconAttribute(android.R.attr.alertDialogIcon)
					.setTitle("查看记录")
                .setPositiveButton("打开", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked OK so do some stuff */
                    	
                    	if(showDataFile!=null){
                    		Intent intent = new Intent(EcgSdkDemo.this, EcgDataSourceReviewActivity.class);
                    		intent.putExtra("ecgFile", showDataFile);
                    		startActivity(intent);
                    	}else{
                    		ToastText("当前没有记录！");
                    	}
                    }
                })
                .setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Cancel so do some stuff */
                    }
                })
                .create();
        }

	OnClickListener buttonOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {


			int id = v.getId();
			// Log.v(getClass().getSimpleName(), "onClick--- id="+id);
			switch (id) {
			case R.id.button_record_start:{
				clearValue();
				countEcg = 0;
				mHelper.startRecord(RECORD_MODE.RECORD_MODE_60, mRecordCallback);
			}break;
			
			case R.id.button_record_stop:{
				
				
				if(mHelper.isRunningRecord()){
					try {
						ToastText("【停止记录】");
						mHelper.stopRecord();
						mEcgBrowser.clearEcg();
					} catch (IOException e) {
						e.printStackTrace();
						ToastText("【关闭记录】文件异常,开始记录失败！");
					}
				}else{
					ToastText("没有开始记录！");
				}
				
				
			}break;
			case R.id.button_register:{
				
				Intent intent = new Intent(EcgSdkDemo.this, RegisterActivity.class);
				startActivity(intent);
			}break;
			case R.id.button_login:{
				Intent intent = new Intent(EcgSdkDemo.this, LoginActivity.class);
				startActivity(intent);
			}break;
			
			default:
				break;
			}

		}
	};


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.v("ChooseTimeActivity", "onConfigurationChanged");
		checkOrientation();
	}

	void checkOrientation() {
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 当前为横屏
		}
		WindowManager wmanager = this.getWindowManager();
		DisplayMetrics dm = new DisplayMetrics();
		Display display = wmanager.getDefaultDisplay();
		display.getMetrics(dm);
		
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Log.i("SDK","---  onDestroy ");

	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if(this.mHelper.isRunningRecord()){
				ToastText("还有记录正在运行，请先停止记录！");
				return false;
			}
			break;
		default:
			break;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	
	final static int REQUEST_CODE = 1000;// 蓝牙地址
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(tag, "onActivityResult ---  requestCode=" + requestCode
				+ ",resultCode=" + resultCode);
	}
	
		
	final static int ECG_GAIN_SPEED = 10001;
	final static int TOAST_TEXT = 10002;
	static final int ECG_HEART = 10003;
	static final int ECG_RR = 10004;
	static final int ECG_STAISTICS_RESULT = 10005;
	static final int ECG_COUNTER = 10006;
	static final int ECG_SHOW_DATA = 10007;
	private  Handler displayMessage = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			
			int what = msg.what;
			switch (what) {
			case ECG_COUNTER:{
				counter.setText(msg.arg1+"");
			}break;
			case ECG_STAISTICS_RESULT:{
				String text = (String) msg.obj;
				if(text!=null)result.setText(text);
			}break;
			case ECG_HEART:{
				int hrValue = 0;
				hrValue = msg.arg1;
				if(hrValue>=1&&hrValue<=355){
					hr.setText(""+hrValue);
				}else{
					hr.setText("---");
				}
				
			}
				break;
			case ECG_RR:{
				if(msg.arg1>=10000){
					rr.setText("----");
				}else{
					rr.setText(""+msg.arg1);
				}
				
			}
				break;
			case ECG_GAIN_SPEED:{
				float speedValue = msg.arg2/10.0f;
				float gainValue = msg.arg1/10.0f;
				speed.setText(""+speedValue+" mm/s") ;
				gain.setText(""+gainValue+" mm/mv");
			}
				break;
				
			case TOAST_TEXT:{
				String text = (String) msg.obj;
				if(text!=null)Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
			}break;
			case ECG_SHOW_DATA:{
				String demoFile = (String) msg.obj;
				showDataFile = demoFile;
				if(showDataFile!=null){
					showDialog(0);
				}
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