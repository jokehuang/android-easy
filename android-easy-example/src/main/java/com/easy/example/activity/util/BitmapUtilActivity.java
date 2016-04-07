package com.easy.example.activity.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easy.example.MyApplication;
import com.easy.example.R;
import com.easy.example.activity.BaseActivity;
import com.easy.util.BitmapUtil;
import com.easy.util.EmptyUtil;
import com.easy.util.FileUtil;
import com.easy.util.IntentUtil;
import com.easy.util.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.Map;

@ContentView(R.layout.activity_bitmaputil)
public class BitmapUtilActivity extends BaseActivity {

	private static final String BITMAP_DIR_PATH = MyApplication.APP_DIR_PATH + File.separator +
			"bitmap";

	@ViewInject(R.id.iv_src)
	private ImageView iv_src;
	@ViewInject(R.id.tv_file_size)
	private TextView tv_file_size;
	@ViewInject(R.id.tv_current_size)
	private TextView tv_current_size;
	private Bitmap rawBmp;
	private Bitmap curBmp;

	@Event(value = R.id.iv_src, type = View.OnClickListener.class)
	private void onClickSelectPicture(View v) {
		IntentUtil.startAlbum(this, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) return;

		Bitmap tempBmp = BitmapUtil.decodeByUri(this, data.getData(), iv_src.getWidth(), iv_src
				.getHeight(), Bitmap.Config.RGB_565);
		if (tempBmp == null) {
			ToastUtil.show(this, "读取图片失败！");
			return;
		}
		recycleAll();
		rawBmp = tempBmp;
		setBitmap(rawBmp);

		Map<String, String> info = IntentUtil.getInfo(this, data.getData());
		log("info: " + info);
		//获取旋转角度
		String orientation = info.get("orientation");
		int degress = EmptyUtil.isEmpty(orientation) ? 0 : Integer.parseInt(orientation);
		//获得正向的宽高
		int outWidth = Integer.parseInt(info.get("width"));
		int outHeight = Integer.parseInt(info.get("height"));
		if (degress % 180 != 0) {
			int temp = outWidth;
			outWidth = outHeight;
			outHeight = temp;
		}
		long fileSize = Integer.parseInt(info.get("_size"));
		tv_file_size.setText(outWidth + " x " + outHeight + "（" + FileUtil.formatSize(fileSize) +
				"）");
	}

	@Event(value = R.id.btn_raw_size, type = View.OnClickListener.class)
	private void onClickRawSize(View v) {
		if (!isBmpExsit()) return;
		setBitmap(rawBmp);
	}

	@Event(value = R.id.btn_limit_size, type = View.OnClickListener.class)
	private void onClickLimitSize(View v) {
		if (!isBmpExsit()) return;
		Bitmap temp = BitmapUtil.limitSize(rawBmp, iv_src.getWidth() / 3);
		setBitmap(temp);
	}

	@Event(value = R.id.btn_crop_center, type = View.OnClickListener.class)
	private void onClickCropCenter(View v) {
		if (!isBmpExsit()) return;
		Bitmap temp = BitmapUtil.cropCenter(rawBmp, iv_src.getWidth(), iv_src.getHeight());
		setBitmap(temp);
	}

	@Event(value = R.id.btn_save, type = View.OnClickListener.class)
	private void onClickSave(View v) {
		if (!isBmpExsit()) return;
		final String path = BITMAP_DIR_PATH + System.currentTimeMillis() +
				BitmapUtil.EXT_JPG;
		new AsyncTask<Bitmap, Void, Boolean>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				ToastUtil.show(self, "保存中...", tag);
			}

			@Override
			protected void onPostExecute(Boolean aBoolean) {
				super.onPostExecute(aBoolean);
				if (aBoolean) ToastUtil.show(self, "已保存到：" + path, tag);
				else ToastUtil.show(self, "保存失败！", tag);
			}

			@Override
			protected void onProgressUpdate(Void... values) {
				super.onProgressUpdate(values);
			}

			@Override
			protected void onCancelled(Boolean aBoolean) {
				super.onCancelled(aBoolean);
			}

			@Override
			protected void onCancelled() {
				super.onCancelled();
			}

			@Override
			protected Boolean doInBackground(Bitmap... params) {
				return BitmapUtil.save(path, params[0], Bitmap.CompressFormat.JPEG, 80);
			}
		}.execute(curBmp);
	}

	@Event(value = R.id.btn_clear, type = View.OnClickListener.class)
	private void onClickClear(View v) {
		BitmapUtil.clear(BITMAP_DIR_PATH);
		ToastUtil.show(this, "已保清除文件夹：" + BITMAP_DIR_PATH);
	}

	private boolean isBmpExsit() {
		if (rawBmp == null) {
			ToastUtil.show(this, "请先选择图片");
			return false;
		} else {
			return true;
		}
	}

	private void setBitmap(Bitmap bmp) {
		if (curBmp == bmp) return;
		//如果当前图片不为空并且不是原图，则可回收
		if (curBmp != null && curBmp != rawBmp) {
			BitmapUtil.recycle(iv_src);
			curBmp = null;
		}
		curBmp = bmp;
		iv_src.setImageBitmap(bmp);
		tv_current_size.setText(bmp.getWidth() + " x " + bmp.getHeight() + "（" + FileUtil
				.formatSize(bmp.getByteCount()) + "）");
	}

	private void recycleAll() {
		//不是第一次加载图片
		if (rawBmp != null) {
			//回收当前显示的图片
			BitmapUtil.recycle(iv_src);
			curBmp = null;
			//回收原图
			if (!rawBmp.isRecycled()) {
				BitmapUtil.recycle(rawBmp);
				rawBmp = null;
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		recycleAll();
	}
}
