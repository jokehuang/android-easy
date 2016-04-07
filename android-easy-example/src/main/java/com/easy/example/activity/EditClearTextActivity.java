package com.easy.example.activity;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.easy.example.R;
import com.easy.util.KeyboardUtil;
import com.easy.view.EditClearText;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_editcleartext)
public class EditClearTextActivity extends BaseActivity {

	@ViewInject(R.id.et_clear)
	private EditClearText et_clear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		et_clear.setSelection(et_clear.length());
		KeyboardUtil.showDelayed(this, et_clear, 300);
	}

	@Event(value = R.id.tb_clearable, type = CompoundButton.OnCheckedChangeListener.class)
	private void onCheckedChangedClearable(CompoundButton buttonView, boolean isChecked) {
		et_clear.setClearable(isChecked);
	}
}
