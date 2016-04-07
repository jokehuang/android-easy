package com.easy.example.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.easy.activity.EasyActivity;
import com.easy.example.R;
import com.easy.popup.EasyPopup;
import com.easy.util.KeyboardUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_pupop)
public class PupopActivity extends BaseActivity {
	@ViewInject(R.id.btn_show)
	private Button btn_show;
	private EasyPopup popup;
	private EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		popup = new EasyPopup(this, R.layout.pupop_test);
		et = (EditText) popup.getContentView().findViewById(R.id.tv);
	}

	@Event(value = R.id.btn_show, type = View.OnClickListener.class)
	private void onClickShowPupop(View v) {
		popup.showAsDropDown(btn_show);
		KeyboardUtil.showDelayed(self, et, 0);
	}
}
