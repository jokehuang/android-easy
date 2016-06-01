package com.easy.example.activity;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.easy.example.R;
import com.easy.util.KeyboardUtil;
import com.easy.view.EasyEdit;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_edit_text)
public class EditTextActivity extends BaseActivity {

	@ViewInject(R.id.et_clear)
	private EasyEdit et_clear;

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
