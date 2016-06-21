package com.easy.example.activity.util;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.DataUtil;
import com.easy.util.EmptyUtil;
import com.easy.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.util.Random;

@ContentView(R.layout.activity_data_util)
public class DataUtilActivity extends BaseActivity {

	@ViewInject(R.id.et_md5)
	private EditText et_md5;
	@ViewInject(R.id.et_reverse)
	private EditText et_reverse;
	@ViewInject(R.id.et_bytes)
	private EditText et_bytes;
	@ViewInject(R.id.et_copy)
	private EditText et_copy;
	@ViewInject(R.id.et_bytes1)
	private EditText et_bytes1;
	@ViewInject(R.id.et_bytes2)
	private EditText et_bytes2;
	@ViewInject(R.id.et_merge)
	private EditText et_merge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//生成随机的十六进制字符串
		Random r = new Random();
		et_reverse.setText(Integer.toHexString(r.nextInt()));
		et_bytes.setText(Integer.toHexString(r.nextInt()));
		et_bytes1.setText(Integer.toHexString(r.nextInt()));
		et_bytes2.setText(Integer.toHexString(r.nextInt()));
	}

	/**
	 * 生成MD5
	 *
	 * @param v
	 */
	@Event(value = R.id.btn_encode_md5, type = View.OnClickListener.class)
	private void onClickEncodeMD5(View v) {
		if (isEmpty(et_md5)) return;
		et_md5.setText(DataUtil.encodeMD5(et_md5.getText().toString()));
	}

	/**
	 * 反转数组
	 *
	 * @param v
	 */
	@Event(value = R.id.btn_reverse, type = View.OnClickListener.class)
	private void onClickReverse(View v) {
		if (isEmpty(et_reverse)) return;
		try {
			byte[] bytes = DataUtil.hexStringToBytes(et_reverse.getText().toString());
			bytes = DataUtil.reverse(bytes);
			et_reverse.setText(DataUtil.bytesToHexString(bytes));
		} catch (ParseException e) {
			e.printStackTrace();
			ToastUtil.show(this, "非十六进制字符串");
		}
	}

	/**
	 * 复制数组
	 *
	 * @param v
	 */
	@Event(value = R.id.btn_copy, type = View.OnClickListener.class)
	private void onClickCopy(View v) {
		if (isEmpty(et_bytes)) return;
		try {
			byte[] bytes = DataUtil.hexStringToBytes(et_bytes.getText().toString());
			bytes = DataUtil.copy(bytes);
			et_copy.setText(DataUtil.bytesToHexString(bytes));
		} catch (ParseException e) {
			e.printStackTrace();
			ToastUtil.show(this, "非十六进制字符串");
		}
	}

	/**
	 * 合并数组
	 *
	 * @param v
	 */
	@Event(value = R.id.btn_merge, type = View.OnClickListener.class)
	private void onClickMerge(View v) {
		if (isEmpty(et_bytes1)) return;
		if (isEmpty(et_bytes2)) return;
		try {
			byte[] bytes1 = DataUtil.hexStringToBytes(et_bytes1.getText().toString());
			byte[] bytes2 = DataUtil.hexStringToBytes(et_bytes2.getText().toString());
			byte[] mergeBytes = DataUtil.merge(bytes1, bytes2);
			et_merge.setText(DataUtil.bytesToHexString(mergeBytes));
		} catch (ParseException e) {
			e.printStackTrace();
			ToastUtil.show(this, "非十六进制字符串");
		}
	}

	private boolean isEmpty(EditText et) {
		if (EmptyUtil.isEmpty(et.getText().toString())) {
			ToastUtil.show(this, "请输入内容", tag);
			return true;
		} else {
			return false;
		}
	}
}
