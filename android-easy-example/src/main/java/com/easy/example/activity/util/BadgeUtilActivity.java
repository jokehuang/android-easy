package com.easy.example.activity.util;

import android.view.View;
import android.widget.EditText;

import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.BadgeUtil;
import com.easy.util.EmptyUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_badge_util)
public class BadgeUtilActivity extends BaseActivity {

	@ViewInject(R.id.et)
	private EditText et;

	@Event(value = R.id.btn_set_badge, type = View.OnClickListener.class)
	private void onClickSetBadge(View v) {
		if (EmptyUtil.isEmpty(et.getText().toString())) {
			et.setText("0");
		}
		BadgeUtil.setBadgeCount(this, Integer.parseInt(et.getText().toString()));
	}

	@Event(value = R.id.btn_reset_badge, type = View.OnClickListener.class)
	private void onClickResetBadge(View v) {
		et.setText("0");
		BadgeUtil.resetBadgeCount(this);
	}
}
