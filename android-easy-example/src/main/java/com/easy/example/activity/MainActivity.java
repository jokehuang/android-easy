package com.easy.example.activity;

import android.view.View;

import com.easy.example.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * @author Joke
 * @version 1.0.0
 * @description
 * @email 113979462@qq.com
 * @create 2015年6月8日
 */

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

	@Event(value = R.id.btn_activity, type = View.OnClickListener.class)
	private void onClickActivity(View v) {
		startActivity(ActivityActivity.class);
	}

	@Event(value = R.id.btn_adapter, type = View.OnClickListener.class)
	private void onClickAdapter(View v) {
		startActivity(AdapterActivity.class);
	}

	@Event(value = R.id.btn_animate, type = View.OnClickListener.class)
	private void onClickAnimate(View v) {
		startActivity(AnimateActivity.class);
	}

	@Event(value = R.id.btn_database, type = View.OnClickListener.class)
	private void onClickDatabase(View v) {
		startActivity(DatabaseActivity.class);
	}

	@Event(value = R.id.btn_fragment, type = View.OnClickListener.class)
	private void onClickFragment(View v) {
		startActivity(FragmentActivity.class);
	}

	@Event(value = R.id.btn_manager, type = View.OnClickListener.class)
	private void onClickManager(View v) {
		startActivity(ManagerActivity.class);
	}

	@Event(value = R.id.btn_popup, type = View.OnClickListener.class)
	private void onClickPopup(View v) {
		startActivity(PopupActivity.class);
	}

	@Event(value = R.id.btn_util, type = View.OnClickListener.class)
	private void onClickUtil(View v) {
		startActivity(UtilActivity.class);
	}

	@Event(value = R.id.btn_view, type = View.OnClickListener.class)
	private void onClickView(View v) {
		startActivity(ViewActivity.class);
	}

	@Override
	public void onBackPressed() {
		exit(R.string.exit_tips);
	}
}
