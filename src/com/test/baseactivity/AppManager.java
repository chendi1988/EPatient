package com.test.baseactivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import java.util.Stack;

/**
 * app'manager class ,manages app and exits app
 * 
 */
public class AppManager {

	private static Stack<FragmentActivity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * instance
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * add activity to stack
	 */
	public static void addActivity(FragmentActivity activity) {
		if (activityStack == null) {
			activityStack = new Stack<FragmentActivity>();
		}
		activityStack.add(activity);
	}

	/**
	 * get activity from stack (the last one)
	 */
	public Activity currentActivity() {
		FragmentActivity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * finish the last activity
	 */
	public void finishActivity() {
		FragmentActivity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * finish the Activity
	 */
	public void finishActivity(FragmentActivity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * finish Activity of class name
	 */
	public void finishActivity(Class<?> cls) {
		for (FragmentActivity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * finish all Activity
	 */
	public static void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * exit app
	 */
	public static void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}
}