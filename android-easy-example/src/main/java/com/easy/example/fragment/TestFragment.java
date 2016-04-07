package com.easy.example.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.easy.example.R;
import com.easy.fragment.EasyFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Random;

/**
 * Created by Joke on 2016/3/1.
 */

public class TestFragment extends EasyFragment {

	@ViewInject(R.id.tv_title)
	protected TextView tv_title;

	public View onInitView(LayoutInflater inflater, ViewGroup container, Bundle
			savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_test, null);
		x.view().inject(self, v);
		tv_title.setText(this.getClass().getSimpleName());
		return v;
	}


	@Event(value = R.id.tb_log_life, type = CompoundButton.OnCheckedChangeListener.class)
	private void onCheckedChangeLogLife(CompoundButton cb, boolean checked) {
		setLogLife(checked);
	}

	@Event(value = R.id.btn_color, type = View.OnClickListener.class)
	private void onClickChangeColor(View btn) {
		Random rd = new Random();
		btn.setBackgroundColor(Color.rgb(rd.nextInt(255), rd.nextInt(255), rd.nextInt(255)));
	}

	public static class NormalFragment extends TestFragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
				savedInstanceState) {
			View v = NormalFragment.this.onInitView(inflater, container, savedInstanceState);
			return v;
		}
	}

	public static class ReuseViewFragment extends TestFragment {

		@Override
		public View onCreateViewForReuse(LayoutInflater inflater, ViewGroup container, Bundle
				savedInstanceState) {
			View v = ReuseViewFragment.this.onInitView(inflater, container, savedInstanceState);
			return v;
		}
	}
}
