package com.easy.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * ToastUtil
 * 
 * @author Joke Huang
 * @createDate 2014年3月30日
 * @version 1.0.0
 */

public class ToastUtil {
	private static int duration = Toast.LENGTH_SHORT;
	private static Integer gravity;
	private static Integer xOffset;
	private static Integer yOffset;
	private static Float horizontalMargin;
	private static Float verticalMargin;
	private static boolean isDebug = false;

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
	 * @param gravity
	 */
	public static void setMargin(float horizontalMargin, float verticalMargin) {
		ToastUtil.horizontalMargin = horizontalMargin;
		ToastUtil.verticalMargin = verticalMargin;
	}

	/**
	 * 显示Debug消息
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showDebug(Context context, String msg) {
		if (isDebug) {
			show(context, msg);
		}
	}

	/**
	 * 显示Debug消息
	 * 
	 * @param context
	 * @param resId
	 */
	public static void showDebug(Context context, int resId) {
		if (isDebug) {
			show(context, resId);
		}
	}

	/**
	 * 显示消息
	 * 
	 * @param context
	 * @param msg
	 */
	public static void show(Context context, String msg) {
		Toast t = Toast.makeText(context, msg, duration);
		show(t);
	}

	/**
	 * 显示消息
	 * 
	 * @param context
	 * @param resId
	 */
	public static void show(Context context, int resId) {
		Toast t = Toast.makeText(context, resId, duration);
		show(t);
	}

	/**
	 * 显示消息
	 * 
	 * @param context
	 * @param resId
	 */
	public static void show(Toast t) {
		if (gravity != null)
			t.setGravity(gravity, xOffset, yOffset);
		if (horizontalMargin != null)
			t.setMargin(horizontalMargin, verticalMargin);
		t.show();
	}

	/**
	 * 在ui线程中显示Debug消息
	 * 
	 * @param activity
	 * @param msg
	 */
	public static void showDebugOnUiThread(final Activity activity,
			final String msg) {
		if (isDebug) {
			showOnUiThread(activity, msg);
		}
	}

	/**
	 * 在ui线程中显示Debug消息
	 * 
	 * @param activity
	 * @param resId
	 */
	public static void showDebugOnUiThread(final Activity activity,
			final int resId) {
		if (isDebug) {
			showOnUiThread(activity, resId);
		}
	}

	/**
	 * 在ui线程中显示消息
	 * 
	 * @param activity
	 * @param msg
	 */
	public static void showOnUiThread(final Activity activity, final String msg) {
		if (activity == null || activity.isFinishing())
			return;

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				show(activity, msg);
			}
		});
	}

	/**
	 * 在ui线程中显示消息
	 * 
	 * @param activity
	 * @param resId
	 */
	public static void showOnUiThread(final Activity activity, final int resId) {
		if (activity == null || activity.isFinishing())
			return;

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				show(activity, resId);
			}
		});
	}

	public static boolean isDebug() {
		return isDebug;
	}

	public static void setDebug(boolean isDebug) {
		ToastUtil.isDebug = isDebug;
	}
}
