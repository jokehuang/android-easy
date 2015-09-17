package com.easy.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * BitmapUtil
 * 
 * @Description
 * @author Joke Huang
 * @createDate 2014年7月15日
 * @version 1.0.0
 */

public class BitmapUtil {
	public static final String EXT_PNG = ".png";
	public static final String EXT_JPG = ".jpg";

	private BitmapUtil() {
	}

	/** 新建基本配置 */
	@SuppressWarnings("deprecation")
	public static BitmapFactory.Options createOptions() {
		// 设置编码、可回收、深拷贝、非增强色
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inDither = false;
		return options;
	}

	/** 新建指定颜色的配置 */
	@SuppressWarnings("deprecation")
	public static BitmapFactory.Options createOptions(Bitmap.Config colorConfig) {
		// 设置编码、可回收、深拷贝、非增强色
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = colorConfig;
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inDither = false;
		return options;
	}

	/** 计算合适的缩放大小 */
	public static float measureScale(Bitmap src, int maxSize) {
		return measureScale(src.getWidth(), src.getHeight(), maxSize);
	}

	public static float measureScale(String path, int maxSize) {
		BitmapFactory.Options op = decodeBounds(path);
		return measureScale(op.outWidth, op.outHeight, maxSize);
	}

	public static float measureScale(int width, int height, int maxSize) {
		int longSize = width > height ? width : height;
		if (longSize <= maxSize) {
			return 1;
		}
		return (float) maxSize / longSize;
	}

	/** 计算合适的样本大小 */
	public static float measureSample(Bitmap src, int maxSize) {
		return measureSample(src.getWidth(), src.getHeight(), maxSize);
	}

	public static float measureSample(String path, int maxSize) {
		BitmapFactory.Options op = decodeBounds(path);
		return measureSample(op.outWidth, op.outHeight, maxSize);
	}

	public static int measureSample(int width, int height, int maxSize) {
		float scale = measureScale(width, height, maxSize);
		if (scale % 1f > 0) {
			scale++;
		}
		return (int) scale;
	}

	/** 获取图片尺寸信息 */
	public static BitmapFactory.Options decodeBounds(String path) {
		BitmapFactory.Options opts = createOptions();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		return opts;
	}

	/** 获取绝对路径的图片 */
	public static Bitmap get(String absolutePath, Options options) {
		try {
			return BitmapFactory.decodeFile(absolutePath, options);
		} catch (Exception e) {
			return null;
		}
	}

	public static Bitmap get(String absolutePath) {
		return get(absolutePath, createOptions());
	}

	/** 获取应用目录下的图片 */
	public static Bitmap get(Context context, String fileName, Options options) {
		try {
			return BitmapFactory.decodeFile(context.getFileStreamPath(fileName)
					.getAbsolutePath(), options);
		} catch (Exception e) {
			return null;
		}
	}

	public static Bitmap get(Context context, String fileName) {
		return get(context, fileName, createOptions());
	}

	/** 获取资源文件中的图片 */
	public static Bitmap get(Context context, int resId, Options options) {
		try {
			return BitmapFactory.decodeResource(context.getResources(), resId,
					options);
		} catch (Exception e) {
			return null;
		}
	}

	public static Bitmap get(Context context, int resId) {
		return get(context, resId, createOptions());
	}

	/** 构建图片 */
	public static Bitmap create(byte[] data, Options options) {
		try {
			return BitmapFactory.decodeByteArray(data, 0, data.length, options);
		} catch (Exception e) {
			return null;
		}
	}

	public static Bitmap create(byte[] data) {
		return create(data, createOptions());
	}

	/** 保存图片 */
	public static String save(String absolutePath, Bitmap bm,
			CompressFormat format, int quality) {
		FileOutputStream fos = null;
		try {
			File f = new File(absolutePath);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(f);
			bm.compress(format, quality, fos);
			return absolutePath;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/** 保存图片到应用文件夹 */
	public static String save(Context context, String fileName, Bitmap bm,
			CompressFormat format, int quality) {
		FileOutputStream fos = null;
		try {
			File bmFile = context.getFileStreamPath(fileName);
			fos = new FileOutputStream(bmFile);
			bm.compress(format, quality, fos);
			return bmFile.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/** 删除绝对路径文件夹下的所有图片 */
	public static boolean clear(String dir) {
		return FileUtil.clear(dir, new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.endsWith(EXT_JPG) || filename.endsWith(EXT_PNG);
			}
		});
	}

	/** 删除应用文件夹下的所有图片 */
	public static boolean clear(Context context) {
		return FileUtil.clear(context.getFilesDir().getAbsolutePath(),
				new FilenameFilter() {
					@Override
					public boolean accept(File dir, String filename) {
						return filename.endsWith(EXT_JPG)
								|| filename.endsWith(EXT_PNG);
					}
				});
	}

	/** 缩放比例 */
	public static Bitmap scale(Bitmap src, float scale) {
		if (src == null) {
			return null;
		}

		Matrix m = new Matrix();
		m.setScale(scale, scale);
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(),
				m, true);
	}

	/** 限制大小 */
	public static Bitmap limitSize(String path, int maxSize) {
		if (path == null || path.length() == 0)
			return null;

		BitmapFactory.Options opts = decodeBounds(path);

		// 找出最接近目标尺寸的sampleSize，但不能小于目标尺寸
		int sample = 0;
		int nextSample = 1;
		do {
			sample = nextSample;
			nextSample++;
		} while (opts.outWidth / nextSample >= maxSize
				&& opts.outHeight / nextSample >= maxSize);
		opts.inSampleSize = sample;
		opts.inJustDecodeBounds = false;
		Bitmap tempBmp = BitmapFactory.decodeFile(path, opts);
		Bitmap bmp = limitSize(tempBmp, maxSize);
		if (tempBmp != bmp) {
			tempBmp.recycle();
		}
		return bmp;
	}

	public static Bitmap limitSize(Bitmap src, int maxSize) {
		if (src == null) {
			return null;
		}

		float scale = measureScale(src, maxSize);
		if (scale == 1) {
			return src;
		}
		return scale(src, scale);
	}

	/**
	 * 裁剪并缩放成固定尺寸大小的图片
	 */
	public static Bitmap cropSize(String path, int dstWidth, int dstHeight) {
		if (path == null || path.length() == 0)
			return null;

		BitmapFactory.Options opts = decodeBounds(path);

		// 找出最接近目标尺寸的sampleSize，但不能小于目标尺寸
		int sample = 0;
		int nextSample = 1;
		do {
			sample = nextSample;
			nextSample++;
		} while (opts.outWidth / nextSample >= dstWidth
				&& opts.outHeight / nextSample >= dstHeight);
		opts.inSampleSize = sample;
		opts.inJustDecodeBounds = false;
		Bitmap tempBmp = BitmapFactory.decodeFile(path, opts);
		Bitmap bmp = cropSize(tempBmp, dstWidth, dstHeight);
		if (tempBmp != bmp) {
			tempBmp.recycle();
		}
		return bmp;
	}

	/**
	 * 裁剪并缩放成固定尺寸大小的图片
	 */
	public static Bitmap cropSize(Bitmap bmp, int dstWidth, int dstHeight) {
		// 根据目标尺寸的长宽比，将原图进行截取，取最中间那一块
		int cutHeight = 0;
		int cutWidth = 0;
		float dstHeightWidthScale = (float) dstHeight / dstWidth;
		float srcHeightWidthScale = (float) bmp.getHeight() / bmp.getWidth();
		if (srcHeightWidthScale > dstHeightWidthScale) {
			cutHeight = (int) (bmp.getHeight() - dstHeightWidthScale
					* bmp.getWidth());
		} else if (srcHeightWidthScale < dstHeightWidthScale) {
			cutWidth = (int) (bmp.getWidth() - bmp.getHeight()
					/ dstHeightWidthScale);
		}
		Bitmap tempBmp = Bitmap.createBitmap(bmp, cutWidth / 2, cutHeight / 2,
				bmp.getWidth() - cutWidth, bmp.getHeight() - cutHeight);
		// 根据目标尺寸大小进行缩放
		bmp = Bitmap.createScaledBitmap(tempBmp, dstWidth, dstHeight, true);
		tempBmp.recycle();

		return bmp;
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
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();

				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
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
		if (iv == null)
			return;
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
