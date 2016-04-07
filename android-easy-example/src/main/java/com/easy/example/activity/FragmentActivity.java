package com.easy.example.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.easy.activity.EasyActivity;
import com.easy.example.R;
import com.easy.example.fragment.TestFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by Joke on 2015/10/12.
 */
@ContentView(R.layout.activity_fragment)
public class FragmentActivity extends BaseActivity {

	private TestFragment.NormalFragment normalFragment;
	private TestFragment.ReuseViewFragment reuseViewFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		normalFragment = (TestFragment.NormalFragment) Fragment.instantiate(self, TestFragment
				.NormalFragment.class.getName());
		reuseViewFragment = (TestFragment.ReuseViewFragment) Fragment.instantiate(self,
				TestFragment.ReuseViewFragment.class.getName());
	}

	@Event(value = {R.id.btn_f1, R.id.btn_f2}, type = View.OnClickListener.class)
	private void onReplaceFragment(View btn) {
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
