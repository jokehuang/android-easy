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
	private static final String EXT = ".png";

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

	/** 新建指定颜色方案的配置 */
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

	/** 计算合适的样本大小 */
	public static int measureSample(Bitmap src, int maxSize) {
		float sample = 1;
		int w = src.getWidth();
		int h = src.getHeight();
		if (w > maxSize || h > maxSize) {
			if (w > h) {
				sample = 1f * w / maxSize;
			} else {
				sample = 1f * h / maxSize;
			}
		}
		if (sample - (int) sample > 0) {
			sample += 1;
		}

		return (int) sample * (int) sample;
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
			return BitmapFactory
					.decodeFile(context.getFileStreamPath(fileName + EXT)
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
	public static Bitmap limit(Bitmap src, int maxSize) {
		if (src == null) {
			return null;
		}

		Bitmap result = null;
		int w = src.getWidth();
		int h = src.getHeight();
		if (w > maxSize || h > maxSize) {
			if (w > h) {
				result = scale(src, maxSize * 1f / w);
			} else {
				result = scale(src, maxSize * 1f / h);
			}
		}
		return result;
	}

	/** 保存图片 */
	public static String save(String absolutePath, Bitmap bm, int quality) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(absolutePath);
			bm.compress(CompressFormat.PNG, quality, fos);
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
			int quality) {
		FileOutputStream fos = null;
		try {
			File bmFile = context.getFileStreamPath(fileName + EXT);
			fos = new FileOutputStream(bmFile);
			bm.compress(CompressFormat.PNG, quality, fos);
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

	/** 删除图片从应用文件夹 */
	public static String deleteBitmap(Context context, String fileName) {
		try {
			File bmFile = context.getFileStreamPath(fileName + EXT);
			bmFile.delete();
			return bmFile.getAbsolutePath();
		} catch (Exception e) {
			return null;
		}
	}

	/** 删除应用文件夹下的所有图片 */
	public static void clearBitmap(Context context) {
		try {
			File dir = context.getFilesDir();
			FilenameFilter ff = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String filename) {
					return filename.endsWith(EXT);
				}
			};
			for (File subFile : dir.listFiles(ff)) {
				subFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 裁剪并缩放成固定尺寸大小的图片
	 */
	public static Bitmap fixSize(String path, int dstWidth, int dstHeight) {
		if (path == null || path.length() < 1)
			return null;
		BitmapFactory.Options opts = createOptions();

		// 找出最接近目标尺寸的sampleSize，但不能小于目标尺寸
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		int sampleSize = 0;
		int nextSample = 1;
		do {
			sampleSize = nextSample;
			nextSample++;
		} while (opts.outWidth / nextSample >= dstWidth
				&& opts.outHeight / nextSample >= dstHeight);
		opts.inSampleSize = sampleSize;
		opts.inJustDecodeBounds = false;
		Bitmap bmp = BitmapFactory.decodeFile(path, opts);
		return fixSize(bmp, dstWidth, dstHeight);
	}

	/**
	 * 裁剪并缩放成固定尺寸大小的图片
	 */
	public static Bitmap fixSize(Bitmap bmp, int dstWidth, int dstHeight) {
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
		bmp = Bitmap.createBitmap(bmp, cutWidth / 2, cutHeight / 2,
				bmp.getWidth() - cutWidth, bmp.getHeight() - cutHeight);

		// 根据目标尺寸大小进行缩放
		return Bitmap.createScaledBitmap(bmp, dstWidth, dstHeight, true);
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
