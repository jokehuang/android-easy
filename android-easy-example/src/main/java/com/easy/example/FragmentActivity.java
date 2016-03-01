package com.easy.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.easy.activity.EasyActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by Joke on 2015/10/12.
 */
@ContentView(R.layout.activity_fragment)
public class FragmentActivity extends EasyActivity {

	private FragmentFragment.NormalFragment normalFragment;
	private FragmentFragment.ReuseViewFragment reuseViewFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		normalFragment = (FragmentFragment.NormalFragment) Fragment.instantiate(self, FragmentFragment.NormalFragment.class.getName
				());
		reuseViewFragment = (FragmentFragment.ReuseViewFragment) Fragment.instantiate(self, FragmentFragment.ReuseViewFragment.class
				.getName());
	}

	@OnClick({R.id.btn_f1, R.id.btn_f2})
	public void onReplaceFragment(View btn) {
		switch (btn.getId()) {
			case R.id.btn_f1:
				replaceFragment(R.id.rl_fragment, normalFragment);
				break;
			case R.id.btn_f2:
				replaceFragment(R.id.rl_fragment, reuseViewFragment);
				break;
		}
	}
}
