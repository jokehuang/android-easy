package com.easy.example.activity;

import android.widget.CompoundButton;

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

@ContentView(R.layout.activity_activity)
public class ActivityActivity extends BaseActivity {
	/**
	 * 双击后退键退出应用
	 *
	 * @param cb
	 * @param checked
	 */
	@Event(value = R.id.tb_exitable, type = CompoundButton.OnCheckedChangeListener.class)
	private void onCheckedChangeExitable(CompoundButton cb, boolean checked) {
		setExitable(checked, R.string.exit_tips);
	}

	/**
	 * 打印生命周期
	 *
	 * @param cb
	 * @param checked
	 */
	@Event(value = R.id.tb_log_life, type = CompoundButton.OnCheckedChangeListener.class)
	private void onCheckedChangeLogLife(CompoundButton cb, boolean checked) {
		setLogLife(checked);
	}
}
