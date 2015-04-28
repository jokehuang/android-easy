package com.easy.fragment;

import java.util.Timer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.easy.util.LogUtil;

/**
 * @description 1、保持Fragment的内容 2、懒人专用 3、打印生命周期log
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年4月18日
 * @version 1.0.0
 */

public class EasyFragment extends Fragment implements OnClickListener {
	// log使用的tag
	protected final String tag = this.getClass().getSimpleName();
	// 指向Fragment自己，当内部类调用Fragment时，不用写“类名.this”，供懒人使用
	protected EasyFragment self;
	// 常用的加载提示框，仅声明，当activity销毁时会自动dismiss
	protected Dialog loadingDialog;
	// 常用的超时定时器，仅声明，当activity销毁时会自动cancel
	protected Timer timeoutTimer;
	// 是否打印生命周期相关的log
	private boolean isLogLife;
	// 是否保持View，使得页面不受生命周期影响，但会占用内存空间
	private boolean isHoldView;
	// 被保持的View
	private View rootView;

	/****************************************** 初始化 ****************************************/

	protected void initData() {
		if (isLogLife)
			log("initData");
	}

	protected View initUI(LayoutInflater inflater) {
		if (isLogLife)
			log("initUI");
		return null;
	}

	protected void initEvent() {
		if (isLogLife)
			log("initEvent");
	}

	/****************************************** 初始化 ****************************************/

	/****************************************** 生命周期 ****************************************/

	@Override
	public void onHiddenChanged(boolean flag) {
		super.onHiddenChanged(flag);
		if (isLogLife)
			log("onHiddenChanged: " + flag);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isLogLife)
			log("setUserVisibleHint: " + isVisibleToUser);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (isLogLife)
			log("onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isLogLife)
			log("onCreate");
		self = this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (isLogLife)
			log("onCreateView");
		if (rootView != null)
			return rootView;
		initData();
		View v = initUI(inflater);
		initEvent();
		if (isHoldView)
			rootView = v;
		return v;
	}

	@Override
	public void onViewCreated(View view1, Bundle bundle) {
		super.onViewCreated(view1, bundle);
		if (isLogLife)
			log("onViewCreated");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (isLogLife)
			log("onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		if (isLogLife)
			log("onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isLogLife)
			log("onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		if (isLogLife)
			log("onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		if (isLogLife)
			log("onStop");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (isLogLife)
			log("onActivityResult: " + requestCode + "/" + resultCode);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (isLogLife)
			log("onDestroyView");
		if (isHoldView) {
			ViewParent vp = rootView.getParent();
			if (vp != null && vp instanceof ViewGroup) {
				((ViewGroup) vp).removeAllViews();
			}
		}
	}

	@Override
	public void onDestroy() {
		if (loadingDialog != null) {
			loadingDialog.dismiss();
		}
		if (timeoutTimer != null) {
			timeoutTimer.cancel();
		}
		super.onDestroy();
		if (isLogLife)
			log("onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if (isLogLife)
			log("onDetach");
	}

	/****************************************** 生命周期 ****************************************/

	/***************************************** 公用方法 ****************************************/

	@Override
	public void onClick(View arg0) {
	}

	public void log(Object obj) {
		LogUtil.v(tag, obj);
	}

	public boolean isLogLife() {
		return isLogLife;
	}

	public void setLogLife(boolean isLogLife) {
		this.isLogLife = isLogLife;
	}

	public boolean isHoldView() {
		return isHoldView;
	}

	public void setHoldView(boolean isHoldView) {
		this.isHoldView = isHoldView;
	}
}
