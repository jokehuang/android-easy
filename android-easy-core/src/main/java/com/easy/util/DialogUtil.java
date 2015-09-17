package com.easy.util;

import android.app.Activity;
import android.app.Dialog;

/**
 * DialogUtil
 * 
 * @author Joke Huang
 * @createDate 2014年2月27日
 * @version 1.0.0
 */

public class DialogUtil {

	private DialogUtil() {
	}

	/**
	 * 在UI线程显示Dialog
	 * 
	 * @param activity
	 * @param dialog
	 */
	public static void showOnUiThread(Activity activity, final Dialog dialog) {
		if (activity == null || activity.isFinishing()) {
			return;
		}

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dialog.show();
			}
		});
	}

	/**
	 * 在UI线程隐藏Dialog
	 * 
	 * @param activity
	 * @param dialog
	 */
	public static void dismissOnUiThread(Activity activity, final Dialog dialog) {
		if (activity == null || activity.isFinishing()) {
			return;
		}

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
			}
		});
	}
}
