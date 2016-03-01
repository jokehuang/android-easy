package com.easy.example;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.easy.fragment.EasyFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;

import java.util.Random;

/**
 * Created by Joke on 2016/3/1.
 */

public class FragmentFragment extends EasyFragment {
	public View onInitView(LayoutInflater inflater, ViewGroup container, Bundle
			savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_fragment, null);
		ViewUtils.inject(self, v);
		return v;
	}

	public static class NormalFragment extends FragmentFragment {

		@ViewInject(R.id.tv_title)
		private TextView tv_title;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
				savedInstanceState) {
			View v = NormalFragment.this.onInitView(inflater, container, savedInstanceState);
			tv_title.setText(this.getClass().getSimpleName());
			return v;
		}

		@OnCompoundButtonCheckedChange(R.id.tb_log_life)
		public void onCheckedChangeLogLife(CompoundButton cb, boolean checked) {
			setLogLife(checked);
		}

		@OnClick(R.id.btn_color)
		public void onClickChangeColor(View btn) {
			Random rd = new Random();
			btn.setBackgroundColor(Color.rgb(rd.nextInt(255), rd.nextInt(255), rd.nextInt(255)));
		}
	}

	public static class ReuseViewFragment extends FragmentFragment {

		@ViewInject(R.id.tv_title)
		private TextView tv_title;

		@Override
		public View onCreateViewForReuse(LayoutInflater inflater, ViewGroup container, Bundle
				savedInstanceState) {
			View v = ReuseViewFragment.this.onInitView(inflater, container, savedInstanceState);
			tv_title.setText(this.getClass().getSimpleName());
			return v;
		}

		@OnCompoundButtonCheckedChange(R.id.tb_log_life)
		public void onCheckedChangeLogLife(CompoundButton cb, boolean checked) {
			setLogLife(checked);
		}

		@OnClick(R.id.btn_color)
		public void onClickChangeColor(View btn) {
			Random rd = new Random();
			btn.setBackgroundColor(Color.rgb(rd.nextInt(255), rd.nextInt(255), rd.nextInt(255)));
		}
	}
}
