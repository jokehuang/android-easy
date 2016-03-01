package com.easy.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.easy.activity.EasyActivity;
import com.easy.fragment.EasyFragment;
import com.easy.util.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;

/**
 * @author Joke
 * @version 1.0.0
 * @description
 * @email 113979462@qq.com
 * @create 2015年6月8日
 */

@ContentView(R.layout.activity_activity)
public class ActivityActivity extends EasyActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
	}

	/**
	 * 双击后退键退出应用
	 *
	 * @param cb
	 * @param checked
	 */
	@OnCompoundButtonCheckedChange(R.id.tb_exitable)
	public void onCheckedChangeExitable(CompoundButton cb, boolean checked) {
		setExitable(checked, R.string.exit_tips);
	}

	/**
	 * 打印生命周期
	 *
	 * @param cb
	 * @param checked
	 */
	@OnCompoundButtonCheckedChange(R.id.tb_log_life)
	public void onCheckedChangeLogLife(CompoundButton cb, boolean checked) {
		setLogLife(checked);
	}
}
