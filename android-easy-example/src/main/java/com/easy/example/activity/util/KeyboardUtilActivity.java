package com.easy.example.activity.util;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.KeyboardUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_keyboardutil)
public class KeyboardUtilActivity extends BaseActivity {

	@ViewInject(R.id.et)
	private EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		KeyboardUtil.showDelayed(this, et, 300);
	}

	@Event(value = R.id.btn_toggle, type = View.OnClickListener.class)
	private void onClickToggle(View view) {
		KeyboardUtil.toggle(this);
	}

	@Event(value = R.id.btn_show, type = View.OnClickListener.class)
	private void onClickShow(View view) {
		KeyboardUtil.show(this, et);
	}

	@Event(value = R.id.btn_hide, type = View.OnClickListener.class)
	private void onClickHide(View view) {
		KeyboardUtil.hide(this, et);
	}
}
