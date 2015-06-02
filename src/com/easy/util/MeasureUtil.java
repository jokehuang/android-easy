package com.easy.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * MeasureUtil
 * 
 * @author Joke Huang
 * @createDate 2014年6月18日
 * @version 1.0.0
 */

public class MeasureUtil {
	// public static final int[] SCREEN_RELATIVE_SIZE = new int[] { 1920, 1080
	// };
	public static final int TYPE_PHONE = 1;
	public static final int TYPE_PAD = 2;

	public static int type;

	// public static int screenRelativeHeight;
	// public static int screenRelativeWidth;

	public static int screenHeight;
	public static int screenWidth;

	// public static float scale;

	private static void measure(Context context) {
		if (type != 0) {
			return;
		}

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display d = wm.getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		d.getMetrics(outMetrics);

		screenHeight = outMetrics.heightPixels;
		screenWidth = outMetrics.widthPixels;

		boolean isDefaultRotation = d.getRotation() % 2 == 0;
		boolean isHeightLonger = screenHeight > screenWidth;

		if (isDefaultRotation ^ isHeightLonger) {
			type = TYPE_PAD;
		} else {
			type = TYPE_PHONE;
		}

		// if (heightIsLonger) {
		// screenRelativeHeight = SCREEN_RELATIVE_SIZE[0];
		// screenRelativeWidth = SCREEN_RELATIVE_SIZE[1];
		// } else {
		// screenRelativeHeight = SCREEN_RELATIVE_SIZE[1];
		// screenRelativeWidth = SCREEN_RELATIVE_SIZE[0];
		// }
		//
		// scale = 1.0f * screenHeight / screenRelativeHeight;

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
		measure(context);
		return type;
	}

	// public static void setType(int type) {
	// MeasureUtil.type = type;
	// }
	//
	// public static int getScreenRelativeHeight(Context context) {
	// measure(context);
	// return screenRelativeHeight;
	// }
	//
	// public static void setScreenRelativeHeight(int screenRelativeHeight) {
	// MeasureUtil.screenRelativeHeight = screenRelativeHeight;
	// }
	//
	// public static int getScreenRelativeWidth(Context context) {
	// measure(context);
	// return screenRelativeWidth;
	// }
	//
	// public static void setScreenRelativeWidth(int screenRelativeWidth) {
	// MeasureUtil.screenRelativeWidth = screenRelativeWidth;
	// }

	public static int getScreenHeight(Context context) {
		measure(context);
		return screenHeight;
	}

	//
	// public static void setScreenHeight(int screenHeight) {
	// MeasureUtil.screenHeight = screenHeight;
	// }

	public static int getScreenWidth(Context context) {
		measure(context);
		return screenWidth;
	}

	//
	// public static void setScreenWidth(int screenWidth) {
	// MeasureUtil.screenWidth = screenWidth;
	// }

	// public static float getScale(Context context) {
	// measure(context);
	// return scale;
	// }
	//
	// public static void setScale(float scale) {
	// MeasureUtil.scale = scale;
	// }

	public static int getLongSide(Context context) {
		measure(context);
		return screenHeight > screenWidth ? screenHeight : screenWidth;
	}

	public static int getShortSide(Context context) {
		measure(context);
		return screenHeight > screenWidth ? screenWidth : screenHeight;
	}
}
