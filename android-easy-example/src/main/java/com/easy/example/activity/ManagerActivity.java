package com.easy.example.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.easy.activity.EasyActivity;
import com.easy.example.R;
import com.easy.manager.EasyActivityManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_manager)
public class ManagerActivity extends BaseActivity {

	@ViewInject(R.id.lv)
	private ListView lv;
	private ArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new ArrayAdapter<Activity>(this, android.R.layout.simple_list_item_1, android.R
				.id.text1);
		adapter.addAll(EasyActivityManager.getInstance().getAllActivitys());
		lv.setAdapter(adapter);
	}

	@Event(value = R.id.btn_start_activity, type = View.OnClickListener.class)
	private void onClickStartActivity(View v) {
		startActivity(ManagerActivity.class);
	}

	@Event(value = R.id.btn_finish_manager, type = View.OnClickListener.class)
	private void onClickFinishManagerActivty(View v) {
		EasyActivityManager.getInstance().finish(ManagerActivity.class);
	}

	@Event(value = R.id.btn_finish_all, type = View.OnClickListener.class)
	private void onClickFinishAll(View v) {
		EasyActivityManager.getInstance().finishAll();
	}
}
