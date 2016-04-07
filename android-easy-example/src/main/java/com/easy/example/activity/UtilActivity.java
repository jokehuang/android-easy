package com.easy.example.activity;

import android.view.View;

import com.easy.example.R;
import com.easy.example.activity.util.AppInfoActivity;
import com.easy.example.activity.util.AppUtilActivity;
import com.easy.example.activity.util.BadgeUtilActivity;
import com.easy.example.activity.util.BitmapUtilActivity;
import com.easy.example.activity.util.DataUtilActivity;
import com.easy.example.activity.util.FileUtilActivity;
import com.easy.example.activity.util.IntentUtilActivity;
import com.easy.example.activity.util.KeyboardUtilActivity;
import com.easy.example.activity.util.LogUtilActivity;
import com.easy.util.KeyboardUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_util)
public class UtilActivity extends BaseActivity {

	@Event(value = {R.id.btn_appinfo, R.id.btn_apputil, R.id.btn_badgeutil, R.id.btn_bitmaputil, R
			.id.btn_datautil, R.id.btn_dialogutil, R.id.btn_emptyutil, R.id.btn_fileutil, R.id
			.btn_intentutil, R.id.btn_keyboardutil, R.id.btn_logutil, R.id.btn_networkutil, R.id
			.btn_reflectutil, R.id.btn_runduration, R.id.btn_screenutil, R.id
			.btn_scrollcontroller, R.id.btn_spfile, R.id.btn_systeminfo, R.id
			.btn_systemserviceutil, R.id.btn_timeutil, R.id.btn_toastutil, R.id.btn_versionutil},
			type = View.OnClickListener.class)
	private void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_appinfo:
				startActivity(AppInfoActivity.class);
				break;
			case R.id.btn_apputil:
				startActivity(AppUtilActivity.class);
				break;
			case R.id.btn_badgeutil:
				startActivity(BadgeUtilActivity.class);
				break;
			case R.id.btn_bitmaputil:
				startActivity(BitmapUtilActivity.class);
				break;
			case R.id.btn_datautil:
				startActivity(DataUtilActivity.class);
				break;
			case R.id.btn_dialogutil:
				break;
			case R.id.btn_emptyutil:
				break;
			case R.id.btn_fileutil:
				startActivity(FileUtilActivity.class);
				break;
			case R.id.btn_intentutil:
				startActivity(IntentUtilActivity.class);
				break;
			case R.id.btn_keyboardutil:
				startActivity(KeyboardUtilActivity.class);
				break;
			case R.id.btn_logutil:
				startActivity(LogUtilActivity.class);
				break;
			case R.id.btn_networkutil:
				break;
			case R.id.btn_reflectutil:
				break;
			case R.id.btn_runduration:
				break;
			case R.id.btn_screenutil:
				break;
			case R.id.btn_scrollcontroller:
				break;
			case R.id.btn_spfile:
				break;
			case R.id.btn_systeminfo:
				break;
			case R.id.btn_systemserviceutil:
				break;
			case R.id.btn_timeutil:
				break;
			case R.id.btn_toastutil:
				break;
			case R.id.btn_versionutil:
				break;
		}
	}
}
