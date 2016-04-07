package com.easy.example.activity.util;

import android.os.Bundle;
import android.widget.TextView;

import com.easy.activity.EasyActivity;
import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.AppInfo;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_appinfo)
public class AppInfoActivity extends BaseActivity {

	@ViewInject(R.id.tv_versionname)
	private TextView tv_versionname;
	@ViewInject(R.id.tv_versioncode)
	private TextView tv_versioncode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tv_versionname.setText(AppInfo.getVersionName(this));
		tv_versioncode.setText(AppInfo.getVersionCode(this) + "");
	}
}
