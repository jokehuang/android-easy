package com.easy.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * ToastUtil
 *
 * @author Joke Huang
 * @version 1.0.0
 * @createDate 2014年3月30日
 */

public class ToastUtil {
	private static int duration = Toast.LENGTH_SHORT;
	private static Integer gravity;
	private static Integer xOffset;
	private static Integer yOffset;
	private static Float horizontalMargin;
	private static Float verticalMargin;
	private static boolean isDebugMode = false;
	private static Map<String, Toast> toastMap = new HashMap<>();

	private ToastUtil() {
	}

	/**
	 * 设置默认的显示持续时间
	 *
	 * @param duration
	 */
	public static void setDuration(int duration) {
		ToastUtil.duration = duration;
	}

	/**
	 * 设置默认的显示位置
	 *
	 * @param gravity
	 */
	public static void setGravity(int gravity, int xOffset, int yOffset) {
		ToastUtil.gravity = gravity;
		ToastUtil.xOffset = xOffset;
		ToastUtil.yOffset = yOffset;
	}

	/**
	 * 设置默认的间隔大小
	 *
	 * @param horizontalMargin
	 * @param verticalMargin
	 */
	public static void setMargin(float horizontalMargin, float verticalMargin) {
		ToastUtil.horizontalMargin = horizontalMargin;
		ToastUtil.verticalMargin = verticalMargin;
	}


	public static Toast showDebug(Context context, String msg) {
		return showDebug(context, msg, null);
	}

	public static Toast showDebug(Context context, int resId) {
		return showDebug(context, resId, null);
	}

	public static Toast showDebug(Context context, String msg, String flag) {
		if (isDebugMode) {
			return show(context, msg, flag);
		}
		return null;
	}

	public static Toast showDebug(Context context, int resId, String flag) {
		if (isDebugMode) {
			return show(context, resId, flag);
		}
		return null;
	}

	public static Toast show(Context context, String msg) {
		return show(context, msg, null);
	}

	public static Toast show(Context context, int resId) {
		return show(context, resId, null);
	}

	public static Toast show(Context context, String msg, String flag) {
		Toast t = create(context, msg);
		show(t, flag);
		return t;
	}

	public static Toast show(Context context, int resId, String flag) {
		Toast t = create(context, resId);
		show(t, flag);
		return t;
	}

	public static Toast showDebugOnUiThread(Activity activity, String msg) {
		return showDebugOnUiThread(activity, msg, null);
	}

	public static Toast showDebugOnUiThread(Activity activity, int resId) {
		return showDebugOnUiThread(activity, resId, null);
	}

	public static Toast showDebugOnUiThread(Activity activity, String msg, String flag) {
		if (isDebugMode) {
			return showOnUiThread(activity, msg, flag);
		}
		return null;
	}

	public static Toast showDebugOnUiThread(Activity activity, int resId, String flag) {
		if (isDebugMode) {
			return showOnUiThread(activity, resId, flag);
		}
		return null;
	}

	public static Toast showOnUiThread(Activity activity, String msg) {
		return showOnUiThread(activity, msg, null);
	}

	public static Toast showOnUiThread(Activity activity, int resId) {
		return showOnUiThread(activity, resId, null);
	}

	public static Toast showOnUiThread(Activity activity, String msg, String flag) {
		final Toast t = create(activity, msg);
		showOnUiThread(activity, t, flag);
		return t;
	}

	public static Toast showOnUiThread(Activity activity, int resId, String flag) {
		final Toast t = create(activity, resId);
		showOnUiThread(activity, t, flag);
		return t;
	}

	public static Toast showOnUiThread(Activity activity, final Toast t, final String flag) {
		if (activity == null || activity.isFinishing()) return null;
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				show(t, flag);
			}
		});
		return t;
	}

	public static Toast create(Context context, String msg) {
		if (context == null || msg == null) return null;
		Toast t = Toast.makeText(context, msg, duration);
		if (gravity != null) t.setGravity(gravity, xOffset, yOffset);
		if (horizontalMargin != null) t.setMargin(horizontalMargin, verticalMargin);
		return t;
	}

	public static Toast create(Context context, int resId) {
		if (context == null) return null;
		Toast t = Toast.makeText(context, resId, duration);
		if (gravity != null) t.setGravity(gravity, xOffset, yOffset);
		if (horizontalMargin != null) t.setMargin(horizontalMargin, verticalMargin);
		return t;
	}

	public static Toast cancel(String flag) {
		Toast t = toastMap.remove(flag);
		if (t != null) {
			t.cancel();
		}
		return t;
	}

	public static Toast show(Toast t, String flag) {
		if (flag != null) cancel(flag);
		if (t != null) t.show();
		if (flag != null && t != null) toastMap.put(flag, t);
		return t;
	}

	public static boolean isDebugMode() {
		return isDebugMode;
	}

	public static void setDebugMode(boolean isDebugMode) {
		ToastUtil.isDebugMode = isDebugMode;
	}
}
