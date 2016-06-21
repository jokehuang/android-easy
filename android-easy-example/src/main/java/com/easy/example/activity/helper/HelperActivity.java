package com.easy.example.activity.helper;

import android.view.View;

import com.easy.example.R;
import com.easy.example.activity.ActivityActivity;
import com.easy.example.activity.AdapterActivity;
import com.easy.example.activity.AnimateActivity;
import com.easy.example.activity.BaseActivity;
import com.easy.example.activity.DatabaseActivity;
import com.easy.example.activity.FragmentActivity;
import com.easy.example.activity.ManagerActivity;
import com.easy.example.activity.PopupActivity;
import com.easy.example.activity.util.UtilActivity;
import com.easy.example.activity.view.ViewActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * @author Joke
 * @version 1.0.0
 * @description
 * @email 113979462@qq.com
 * @create 2015年6月8日
 */

@ContentView(R.layout.activity_helper)
public class HelperActivity extends BaseActivity {

	@Event(value = R.id.btn_scroll_controller, type = View.OnClickListener.class)
	private void onClickScrollController(View v) {
		startActivity(ScrollControllerActivity.class);
	}

	@Event(value = R.id.btn_spfile, type = View.OnClickListener.class)
	private void onClickSPFile(View v) {
		startActivity(SPFileActivity.class);
	}
}
