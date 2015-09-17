package com.easy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.easy.manager.EasyActivityManager;
import com.easy.util.LogUtil;
import com.easy.util.ToastUtil;

/**
 * @description 1、连续双击退出应用 2、懒人专用 3、打印生命周期log
 * @author Joke Huang
 * @createDate 2014年6月30日
 * @version 1.1.0
 */

public class EasyActivity extends FragmentActivity {
	// 双击退出应用的有效间隔时间
	private static final int BACK_PRESSED_TIME = 2000;
	// 退出程序提示
	private static String exitTips = "再按一次退出";
	// log使用的tag
	protected final String tag = this.getClass().getSimpleName();
	// 指向activity自己，当内部类调用activity时，不用写“类名.this”，供懒人使用
	protected EasyActivity self;
	// 最后一次按下后退键的时间
	private long lastBackPressedTime;
	// 当前activity是否为整个应用的出口，也就是允许双击退出应用
	private boolean exitable;
	// 是否打印生命周期相关的log
	private boolean isLogLife;

	/****************************************** 初始化 ****************************************/
	@Deprecated
	protected void initData() {
	}

	@Deprecated
	protected void initUI() {
	}

	@Deprecated
	protected void initEvent() {
	}

	/****************************************** 初始化 ****************************************/

	/****************************************** 生命周期 ****************************************/
	@Override
	protected void onNewIntent(Intent intent) {
		if (isLogLife)
			log("onNewIntent");
		setIntent(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isLogLife)
			log("onCreate");
		self = this;
		EasyActivityManager.getInstance().add(this);
		initData();
		initUI();
		initEvent();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (isLogLife)
			log("onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (isLogLife)
			log("onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isLogLife)
			log("onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isLogLife)
			log("onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (isLogLife)
			log("onStop");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (isLogLife)
			log("onActivityResult: " + requestCode + "/" + resultCode);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (isLogLife)
			log("onDestroy");
		EasyActivityManager.getInstance().remove(this);
	}

	protected void onExit() {
		if (isLogLife)
			log("onExit");
	}

	/****************************************** 生命周期 ****************************************/

	/***************************************** 公用方法 ****************************************/
	@Override
	public void onBackPressed() {
		if (exitable) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastBackPressedTime > BACK_PRESSED_TIME) {
				lastBackPressedTime = currentTime;
				ToastUtil.show(this, exitTips);
				return;
			}
			EasyActivityManager.getInstance().finishAll();
			onExit();
		}
		super.onBackPressed();
	}

	public void back(View v) {
		onBackPressed();
	}

	public void log(Object obj) {
		LogUtil.v(tag, obj);
	}

	public void startActivity(Class<? extends Activity> activityClass) {
		startActivity(new Intent(self, activityClass));
	}

	public void repalceFragment(int contentId, Fragment fragment) {
		repalceFragment(contentId, fragment, false);
	}

	public void repalceFragment(int contentId, Fragment fragment,
			boolean addToBackStack) {
		if (addToBackStack) {
			getSupportFragmentManager().beginTransaction()
					.replace(contentId, fragment).addToBackStack(null).commit();
		} else {
			getSupportFragmentManager().beginTransaction()
					.replace(contentId, fragment).commit();
		}
	}

	public static String getExitTips() {
		return exitTips;
	}

	public static void setExitTips(Context context, int exitTipsId) {
		EasyActivity.exitTips = context.getString(exitTipsId);
	}

	public static void setExitTips(String exitTips) {
		EasyActivity.exitTips = exitTips;
	}

	public boolean isExitable() {
		return exitable;
	}

	public void setExitable(boolean exitable) {
		this.exitable = exitable;
	}

	public boolean isLogLife() {
		return isLogLife;
	}

	public void setLogLife(boolean isLogLife) {
		this.isLogLife = isLogLife;
	}
}
