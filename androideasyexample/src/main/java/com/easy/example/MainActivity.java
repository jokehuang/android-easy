package com.easy.example;

import android.os.Bundle;
import android.view.View;

import com.easy.activity.EasyActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年6月8日
 * @version 1.0.0
 */

@ContentView(R.layout.activity_main)
public class MainActivity extends EasyActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
	}

	@OnClick(R.id.btn_activity)
	public void onClickActivity(View v) {
		startActivity(ActivityActivity.class);
	}

	@OnClick(R.id.btn_adapter)
	public void onClickAdapter(View v) {
		startActivity(AdapterActivity.class);
	}

	@OnClick(R.id.btn_database)
	public void onClickDatabase(View v) {
		startActivity(DatabaseActivity.class);
	}

	@OnClick(R.id.btn_fragment)
	public void onClickFragment(View v) {
	}

	@OnClick(R.id.btn_manager)
	public void onClickManager(View v) {
	}

	@OnClick(R.id.btn_popup)
	public void onClickPopup(View v) {
	}

	@OnClick(R.id.btn_util)
	public void onClickUtil(View v) {
	}

	@OnClick(R.id.btn_view)
	public void onClickView(View v) {
	}
}
