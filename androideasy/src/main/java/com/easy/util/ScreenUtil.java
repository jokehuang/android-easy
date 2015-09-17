package com.easy.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * 
 * @author Joke Huang
 * @createDate 2014年6月18日
 * @version 1.0.0
 */

public class ScreenUtil {
	public static final int TYPE_PHONE = 1;
	public static final int TYPE_PAD = 2;

	public static class ScreenInfo {
		public int type;
		public int rotation;
		public int height;
		public int width;
		public int longSide;
		public int shortSide;
	}

	public static ScreenInfo getInfo(Context context) {
		ScreenInfo info = new ScreenInfo();

		WindowManager wm = SystemServiceUtil.getWindowManager(context);
		Display d = wm.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		d.getMetrics(outMetrics);

		info.height = outMetrics.heightPixels;
		info.width = outMetrics.widthPixels;
		info.rotation = d.getRotation();
		info.longSide = info.height > info.width ? info.height : info.width;
		info.shortSide = info.height < info.width ? info.height : info.width;

		boolean isDefaultRotation = info.rotation % 2 == 0;
		boolean isHeightLonger = info.height > info.width;

		if (isDefaultRotation ^ isHeightLonger) {
			info.type = TYPE_PAD;
		} else {
			info.type = TYPE_PHONE;
		}

		return info;

		// Log.v("MeasureUtil", "Rotation: " + d.getRotation());
		// Log.v("MeasureUtil", "screenHeight: " + screenHeight);
		// Log.v("MeasureUtil", "screenWidth: " + screenWidth);
		// Log.v("MeasureUtil", "screenRelativeHeight: " +
		// screenRelativeHeight);
		// Log.v("MeasureUtil", "screenRelativeWidth: " + screenRelativeWidth);
		// Log.v("MeasureUtil", "scale: " + scale);
		// Log.v("MeasureUtil", "type: " + (type == TYPE_PHONE ? "PHONE" :
		// "PAD"));

	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getType(Context context) {
		return getInfo(context).type;
	}

	public static int getHeight(Context context) {
		return getInfo(context).height;
	}

	public static int getWidth(Context context) {
		return getInfo(context).width;
	}

	public static int getLongSide(Context context) {
		return getInfo(context).longSide;
	}

	public static int getShortSide(Context context) {
		return getInfo(context).shortSide;
	}
}
