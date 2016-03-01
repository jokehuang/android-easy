package com.easy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
	public static BitmapFactory.Options createOptions(Context context, boolean isTransparent) {
		// 设置编码、可自动回收、可拷贝、非增强色、密度
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = isTransparent ? Bitmap.Config.ARGB_4444 : Bitmap.Config
				.RGB_565;
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

		float widthScale = maxWidth / srcWidth;
		float heightScale = maxHeight / srcHeight;

		return widthScale < heightScale ? widthScale : heightScale;
	}

	/**
	 * 计算合适的样本大小
	 */
	public static int measureSample(int srcWidth, int srcHeight, int closeSize, boolean
			isQualityPriority) {
		return measureSample(srcWidth, srcHeight, closeSize, closeSize, isQualityPriority);

	}

	public static int measureSample(int srcWidth, int srcHeight, int closeWidth, int closeHeight,
	                                boolean isQualityPriority) {
		if (srcWidth <= closeWidth && srcHeight <= closeHeight) return 1;

		float widthSample = srcWidth / closeWidth;
		float heightSample = srcHeight / closeHeight;

		if (isQualityPriority) {
			return (int) (widthSample < heightSample ? widthSample : heightSample);
		} else {
			float targetSample = widthSample > heightSample ? widthSample : heightSample;
			return (int) (targetSample % 1f == 0f ? targetSample : targetSample + 1);
		}
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
		});
	}

	/**
	 * 删除绝对路径文件夹下的所有图片
	 */
	public static boolean clear(File dir) {
		return FileUtil.delete(dir, true, new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.getAbsolutePath().endsWith(EXT_JPG) || file.getAbsolutePath().endsWith
						(EXT_PNG);
			}
		});
	}

	/**
	 * 缩放比例
	 */
	public static Bitmap scale(Bitmap src, float scale) {
		if (scale == 1f) return src;
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
		float widthScale = dstWidth / src.getWidth();
		float heightScale = dstHeight / src.getHeight();
		float targetScale = widthScale > heightScale ? widthScale : heightScale;
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
