package com.easy.example.activity.util;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.EmptyUtil;
import com.easy.util.IntentUtil;
import com.easy.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_intentutil)
public class IntentUtilActivity extends BaseActivity {

	private static final int REQ_FILE = 1;
	private static final int REQ_PHOTO = 3;
	private static final int REQ_ALBUM = 4;

	@ViewInject(R.id.tv_file_path)
	private TextView tv_file_path;
	@ViewInject(R.id.et_is_installed)
	private EditText et_is_installed;
	@ViewInject(R.id.et_uninstall)
	private EditText et_uninstall;
	@ViewInject(R.id.tv_photo_path)
	private TextView tv_photo_path;
	@ViewInject(R.id.tv_album_path)
	private TextView tv_album_path;
	@ViewInject(R.id.et_tel_num)
	private EditText et_tel_num;
	@ViewInject(R.id.et_share)
	private EditText et_share;
	@ViewInject(R.id.et_web)
	private EditText et_web;
	@ViewInject(R.id.et_search)
	private EditText et_search;
	@ViewInject(R.id.et_market)
	private EditText et_market;

	@Event(value = R.id.btn_file_manager, type = View.OnClickListener.class)
	private void onClickFileManager(View view) {
		IntentUtil.startFileManager(this, REQ_FILE);
	}

	@Event(value = R.id.btn_install, type = View.OnClickListener.class)
	private void onClickInstall(View view) {
		String path = tv_file_path.getText().toString();
		if (EmptyUtil.isEmpty(path) || !path.endsWith(".apk")) {
			ToastUtil.show(this, "请选择一个apk文件");
			return;
		}
		IntentUtil.install(this, path);
	}

	@Event(value = R.id.btn_is_installed, type = View.OnClickListener.class)
	private void onClickIsInstalled(View view) {
		String packageName = et_is_installed.getText().toString();
		if (EmptyUtil.isEmpty(packageName)) {
			ToastUtil.show(this, "请输入包名");
			return;
		}
		ToastUtil.show(this, IntentUtil.isInstalled(this, packageName) + "");
	}

	@Event(value = R.id.btn_uninstall, type = View.OnClickListener.class)
	private void onClickUninstall(View view) {
		String packageName = et_uninstall.getText().toString();
		if (EmptyUtil.isEmpty(packageName)) {
			ToastUtil.show(this, "请输入包名");
			return;
		}
		IntentUtil.uninstall(this, packageName);
	}

	@Event(value = R.id.btn_photo, type = View.OnClickListener.class)
	private void onClickPhoto(View view) {
		IntentUtil.startPhoto(this, REQ_PHOTO);
	}

	@Event(value = R.id.btn_album, type = View.OnClickListener.class)
	private void onClickAlbum(View view) {
		IntentUtil.startAlbum(this, REQ_ALBUM);
	}

	@Event(value = R.id.btn_share_image, type = View.OnClickListener.class)
	private void onClickShareImage(View view) {
		String shareContent = tv_album_path.getText().toString();
		if (EmptyUtil.isEmpty(shareContent)) {
			ToastUtil.show(this, "请选择图片");
			return;
		}
		IntentUtil.startShareImage(this, shareContent);
	}

	@Event(value = R.id.btn_share_text, type = View.OnClickListener.class)
	private void onClickShareText(View view) {
		String shareContent = et_share.getText().toString();
		if (EmptyUtil.isEmpty(shareContent)) {
			ToastUtil.show(this, "请输入分享内容");
			return;
		}
		IntentUtil.startShareText(this, shareContent);
	}

	@Event(value = R.id.btn_telephone, type = View.OnClickListener.class)
	private void onClickTelephone(View view) {
		String telNum = et_tel_num.getText().toString();
		if (EmptyUtil.isEmpty(telNum)) {
			ToastUtil.show(this, "请输入电话号码");
			return;
		}
		IntentUtil.startTelephone(this, telNum);
	}

	@Event(value = R.id.btn_map, type = View.OnClickListener.class)
	private void onClickMap(View view) {
		IntentUtil.startMap(this, 23.1090780992, 113.3190351164, "广州塔");
	}

	@Event(value = R.id.btn_web, type = View.OnClickListener.class)
	private void onClickWeb(View view) {
		String url = et_web.getText().toString();
		if (EmptyUtil.isEmpty(url)) {
			ToastUtil.show(this, "请输入网址");
			return;
		}
		IntentUtil.startWeb(this, url);
	}

	@Event(value = R.id.btn_search, type = View.OnClickListener.class)
	private void onClickSearch(View view) {
		String keyword = et_search.getText().toString();
		if (EmptyUtil.isEmpty(keyword)) {
			ToastUtil.show(this, "请输入关键字");
			return;
		}
		IntentUtil.startSearch(this, keyword);
	}

	@Event(value = R.id.btn_market, type = View.OnClickListener.class)
	private void onClickMarket(View view) {
		String packageName = et_market.getText().toString();
		if (EmptyUtil.isEmpty(packageName)) {
			IntentUtil.startMarket(this);
		} else {
			IntentUtil.startMarket(this, packageName);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) return;

		String path = IntentUtil.getInfo(this, data.getData()).get("path");
		switch (requestCode) {
			case REQ_FILE:
				tv_file_path.setText(path);
				break;
			case REQ_PHOTO:
				tv_photo_path.setText(path);
				break;
			case REQ_ALBUM:
				tv_album_path.setText(path);
				break;
		}
	}
}
