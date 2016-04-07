package com.easy.example.activity;

import android.view.View;

import com.easy.example.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_view)
public class ViewActivity extends BaseActivity {

	@Event(value = R.id.btn_view_pager, type = View.OnClickListener.class)
	private void onClickViewPager(View view) {
		startActivity(ViewPagerActivity.class);
	}

	@Event(value = R.id.btn_fragment_pager, type = View.OnClickListener.class)
	private void onClickFragmentPager(View view) {
		startActivity(FragmentPagerActivity.class);
	}

	@Event(value = R.id.btn_editcleartext, type = View.OnClickListener.class)
	private void onClickEditClearText(View view) {
		startActivity(EditClearTextActivity.class);
	}
}
