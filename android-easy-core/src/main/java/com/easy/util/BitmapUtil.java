package com.easy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * BitmapUtil
 *
 * @author Joke Huang
 * @version 1.0.0
 * @Description
 * @createDate 2014年7月15日
 */

public class BitmapUtil {
	public static final String EXT_PNG = ".png";
	public static final String EXT_JPG = ".jpg";
	private static final float DENSITY_SCALE = 0.75f;

	private BitmapUtil() {
	}

	/**
	 * 新建基本配置
	 */
	@SuppressWarnings("deprecation")
	public static BitmapFactory.Options createOptions(Context context, Bitmap.Config colorMode) {
		// 设置编码、可自动回收、可拷贝、非增强色、密度
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = colorMode == null ? Bitmap.Config.ARGB_8888 : colorMode;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inDither = false;
		if (context != null) {
			options.inDensity = (int) (context.getResources().getDisplayMetrics().densityDpi *
					DENSITY_SCALE);
			options.inTargetDensity = options.inDensity;
		}

		return options;
	}

	/**
	 * 计算合适的缩放大小
	 */
	public static float measureScale(int srcWidth, int srcHeight, int maxSize) {
		return measureScale(srcWidth, srcHeight, maxSize, maxSize);
	}

	public static float measureScale(int srcWidth, int srcHeight, int maxWidth, int maxHeight) {
		if (srcWidth <= maxWidth && srcHeight <= maxHeight) return 1f;

		float widthScale = maxWidth <= 0 ? 1f : (float) maxWidth / srcWidth;
		float heightScale = maxHeight <= 0 ? 1f : (float) maxHeight / srcHeight;

		return Math.min(widthScale, heightScale);
	}

	/**
	 * 计算合适的样本大小
	 */
	public static int measureSample(int srcWidth, int srcHeight, int maxSize) {
		return measureSample(srcWidth, srcHeight, maxSize, maxSize);

	}

	public static int measureSample(int srcWidth, int srcHeight, int maxWidth, int maxHeight) {
		if (srcWidth <= maxWidth && srcHeight <= maxHeight) return 1;

		float widthSample = maxWidth <= 0 ? 1f : (float) srcWidth / maxWidth;
		float heightSample = maxHeight <= 0 ? 1f : (float) srcHeight / maxHeight;
		float maxSample = Math.max(widthSample, heightSample);
		double power = Math.log(maxSample) / Math.log(2.0);
		return (int) Math.pow(2, Math.ceil(power));
	}

	/**
	 * 获取图片尺寸信息
	 */
	public static BitmapFactory.Options decodeBounds(String absolutePath) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(absolutePath, opts);
		return opts;
	}

	public static BitmapFactory.Options decodeBounds(byte[] data, int offset, int length) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, offset, length, opts);
		return opts;
	}

	public static BitmapFactory.Options decodeBounds(FileDescriptor fd) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFileDescriptor(fd, null, opts);
		return opts;
	}

	public static BitmapFactory.Options decodeBounds(Context context, int id) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), id, opts);
		return opts;
	}

	public static BitmapFactory.Options decodeBounds(InputStream is) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, opts);
		return opts;
	}

	/**
	 * 保存图片
	 */
	public static boolean save(String absolutePath, final Bitmap bm, final CompressFormat format,
	                           final int quality) {
		return FileUtil.agentOutput(absolutePath, new FileUtil.Outputer() {
			@Override
			public void doOutput(FileOutputStream fos) throws IOException {
				bm.compress(format, quality, fos);
			}
		}, false);
	}

	/**
	 * 删除绝对路径文件夹下的所有图片
	 */
	public static boolean clear(String dirPath) {
		return clear(new File(dirPath));
	}

	public static boolean clear(File dir) {
		if (!dir.exists()) return false;
		File[] list = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.getAbsolutePath().endsWith(EXT_JPG) || file.getAbsolutePath().endsWith
						(EXT_PNG);
			}
		});
		if (list != null) {// 子文件正在被写入, 文件属性异常返回null.
			for (File subFile : list) {
				FileUtil.delete(subFile);
			}
		}
		return true;
	}

	/**
	 * 旋转
	 */
	public static Bitmap rotate(Bitmap src, float degress) {
		Matrix m = new Matrix();
		m.setRotate(degress);
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, true);
	}

	/**
	 * 缩放比例
	 */
	public static Bitmap scale(Bitmap src, float scale) {
		Matrix m = new Matrix();
		m.setScale(scale, scale);
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, true);
	}

	/**
	 * 限制大小
	 */
	public static Bitmap limitSize(Bitmap src, int maxSize) {
		return limitSize(src, maxSize, maxSize);
	}

	public static Bitmap limitSize(Bitmap src, int maxWidth, int maxHeight) {
		float scale = measureScale(src.getWidth(), src.getHeight(), maxWidth, maxHeight);
		return scale == 1f ? src : scale(src, scale);
	}

	/**
	 * 缩放并将中心部分裁剪成固定尺寸大小的图片
	 */
	public static Bitmap cropCenter(Bitmap src, int dstWidth, int dstHeight) {
		Bitmap target = src;

		//缩放到合适大小
		float widthScale = (float) dstWidth / src.getWidth();
		float heightScale = (float) dstHeight / src.getHeight();
		float targetScale = Math.max(widthScale, heightScale);
		if (targetScale != 1f) {
			target = scale(src, targetScale);
		}

		// 将中心部分裁剪成固定尺寸
		int cutWidth = target.getWidth() - dstWidth;
		int cutHeight = target.getHeight() - dstHeight;
		if (cutWidth != 0 || cutHeight != 0) {
			Bitmap tempTarget = Bitmap.createBitmap(target, cutWidth / 2, cutHeight / 2, target
					.getWidth() - cutWidth, target.getHeight() - cutHeight);
			target.recycle();
			target = tempTarget;
		}

		return target;
	}

	public static Bitmap decodeByUri(Context context, Uri uri, int maxWidth, int maxHeight, Bitmap
			.Config colorMode) {
		Map<String, String> info = IntentUtil.getInfo(context, uri);
		//获取不到路径
		if (EmptyUtil.isEmpty(info) || !info.containsKey("path")) {
			return null;
		}
		String path = info.get("path");
		File file = new File(path);
		//文件不存在
		if (!file.exists()) {
			return null;
		}

		//获取旋转角度
		String orientation = info.get("orientation");
		int degress = EmptyUtil.isEmpty(orientation) ? 0 : Integer.parseInt(orientation);
		//获得正向的宽高
		BitmapFactory.Options opts = BitmapUtil.decodeBounds(path);
		int outWidth = degress % 180 != 0 ? opts.outHeight : opts.outWidth;
		int outHeight = degress % 180 != 0 ? opts.outWidth : opts.outHeight;
		//计算合适的sample值
		int sample = BitmapUtil.measureSample(outWidth, outHeight, maxWidth, maxHeight);
		opts = BitmapUtil.createOptions(context, colorMode);
		opts.inSampleSize = sample;
		try {
			Bitmap tempBmp = BitmapFactory.decodeFile(path, opts);
			if (degress == 0) {
				return tempBmp;
			} else {
				//将图片转正
				Bitmap bmp = BitmapUtil.rotate(tempBmp, degress);
				tempBmp.recycle();
				return bmp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * bitmap转为base64
	 *
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToBase64(Bitmap bitmap) {
		String result = null;
		ByteArrayOutputStream baos = null;

		try {
			baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			baos.flush();
			baos.close();
			result = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * base64转为bitmap
	 *
	 * @param base64Data
	 * @return
	 */
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	/**
	 * 回收Layout中所有ImageView
	 */
	public static void recycle(ViewGroup parent) {
		for (int i = 0; i < parent.getChildCount(); i++) {
			View child = parent.getChildAt(i);
			if (child instanceof ViewGroup) {
				recycle((ViewGroup) child);
			} else if (child instanceof ImageView) {
				recycle((ImageView) child);
			}
		}
	}

	/**
	 * 回收ImageView
	 */
	public static void recycle(ImageView iv) {
		Drawable drawable = iv.getDrawable();
		if (drawable != null) {
			iv.setImageBitmap(null);
			if (drawable instanceof BitmapDrawable) {
				BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
				Bitmap bmp = bitmapDrawable.getBitmap();
				recycle(bmp);
			} else if (drawable instanceof AnimationDrawable) {
				AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
				recycle(animationDrawable);
			}
		}
	}

	/**
	 * 回收Animation
	 */
	public static void recycle(AnimationDrawable ad) {
		for (int i = 0; i < ad.getNumberOfFrames(); i++) {
			Drawable frame = ad.getFrame(i);
			if (frame instanceof BitmapDrawable) {
				Bitmap tempBmp = ((BitmapDrawable) frame).getBitmap();
				recycle(tempBmp);
			}
		}
	}

	/**
	 * 回收Bitmap
	 */
	public static void recycle(Bitmap bmp) {
		if (bmp != null && !bmp.isRecycled()) {
			bmp.recycle();
		}
	}
}
