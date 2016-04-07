package com.easy.example.activity.util;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.easy.example.MyApplication;
import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.EmptyUtil;
import com.easy.util.FileUtil;
import com.easy.util.IntentUtil;
import com.easy.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@ContentView(R.layout.activity_fileutil)
public class FileUtilActivity extends BaseActivity {

	private static final String FILE_DIR_PATH = MyApplication.APP_DIR_PATH + File.separator +
			"file";
	private static final String FILE_PATH = FILE_DIR_PATH + File.separator + "test.txt";

	@ViewInject(R.id.tv_md5)
	private TextView tv_md5;
	@ViewInject(R.id.tv_size)
	private TextView tv_size;
	@ViewInject(R.id.et_output)
	private EditText et_output;
	@ViewInject(R.id.et_input)
	private EditText et_input;

	@Event(value = R.id.btn_select_file, type = View.OnClickListener.class)
	private void onClickSelectFile(View v) {
		IntentUtil.startFileManager(this, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) return;

		String path = IntentUtil.getInfo(this, data.getData()).get("path");
		if (EmptyUtil.isEmpty(path)) {
			ToastUtil.show(this, "读取文件失败！");
			return;
		}

		File file = new File(path);
		tv_md5.setText(FileUtil.encodeMD5(file));
		tv_size.setText(FileUtil.formatSize(file.length()));
	}

	@Event(value = R.id.btn_output, type = View.OnClickListener.class)
	private void onClickOutput(View v) {
		FileUtil.agentOutput(FILE_PATH, new FileUtil.Outputer() {
			@Override
			public void doOutput(FileOutputStream fos) throws IOException {
				DataOutputStream dos = new DataOutputStream(fos);
				dos.writeUTF(et_output.getText().toString());
			}
		}, false);
		ToastUtil.show(this, "已写入：" + FILE_PATH);
	}

	@Event(value = R.id.btn_input, type = View.OnClickListener.class)
	private void onClickInput(View v) {
		String content = FileUtil.agentInput(FILE_PATH, new FileUtil.Inputer<String>() {
			@Override
			public String doInput(FileInputStream fis) throws IOException {
				DataInputStream dis = new DataInputStream(fis);
				return dis.readUTF();
			}
		});
		et_input.setText(content);
		ToastUtil.show(this, "已读取：" + FILE_PATH);
	}

	@Event(value = R.id.btn_clear, type = View.OnClickListener.class)
	private void onClickClear(View v) {
		FileUtil.clear(FILE_DIR_PATH, true);
		ToastUtil.show(this, "已清除：" + FILE_DIR_PATH);
	}

	@Event(value = R.id.btn_calculate_size, type = View.OnClickListener.class)
	private void onClickCalculateSize(View v) {
		long size = FileUtil.calculateSize(FILE_DIR_PATH);
		ToastUtil.show(this, "文件夹大小：" + FileUtil.formatSize(size));
	}

}
