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
	@ViewInject(R.id.tv_exitable_tips)
	private TextView tv_exitable_tips;
	@ViewInject(R.id.tv_log_life_tips)
	private TextView tv_log_life_tips;
	private MyFragment f1;
	private MyFragment f2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		tv_exitable_tips.setVisibility(View.GONE);
		tv_log_life_tips.setVisibility(View.GONE);
	}

	/**
	 * 设置为双击后退键退出应用
	 *
	 * @param cb
	 * @param checked
	 */
	@OnCompoundButtonCheckedChange(R.id.tb_exitable)
	public void onCheckedChangeExitable(CompoundButton cb, boolean checked) {
		setExitable(checked);
		tv_exitable_tips.setVisibility(checked ? View.VISIBLE : View.GONE);
	}

	/**
	 * 设置为打印生命周期到LogCat
	 *
	 * @param cb
	 * @param checked
	 */
	@OnCompoundButtonCheckedChange(R.id.tb_log_life)
	public void onCheckedChangeLogLife(CompoundButton cb, boolean checked) {
		setLogLife(checked);
		tv_log_life_tips.setVisibility(checked ? View.VISIBLE : View.GONE);
	}

	/**
	 * 使用父类已经定义好的Tag，Tag的内容是当前Activity的类名
	 *
	 * @param v
	 */
	@OnClick(R.id.btn_tag)
	public void onClickTag(View v) {
		ToastUtil.show(self, "当前页面默认Tag: " + tag);
	}

	/**
	 * 封装了repalceFragment的事务，使得代码更简洁
	 *
	 * @param v
	 */
	@OnClick(R.id.btn_fragment1)
	public void onClickFragment1(View v) {
		if (f1 == null) {
			Bundle data = new Bundle();
			data.putString("name", "Fragment1");
			data.putInt("color", 0x66660000);
			f1 = (MyFragment) Fragment.instantiate(self, MyFragment.class.getName(), data);
		}
		repalceFragment(R.id.rl_fragment, f1);
	}

	@OnClick(R.id.btn_fragment2)
	public void onClickFragment2(View v) {
		if (f2 == null) {
			Bundle data = new Bundle();
			data.putString("name", "Fragment2");
			data.putInt("color", 0x66000066);
			f2 = (MyFragment) Fragment.instantiate(self, MyFragment.class.getName(), data);
		}
		repalceFragment(R.id.rl_fragment, f2);
	}

	public static class MyFragment extends EasyFragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
				savedInstanceState) {
			TextView tv = new TextView(getActivity());
			tv.setText(getArguments().getString("name"));
			tv.setBackgroundColor(getArguments().getInt("color"));
			tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
					.MATCH_PARENT));
			tv.setGravity(Gravity.CENTER);
			return tv;
		}
	}
}
