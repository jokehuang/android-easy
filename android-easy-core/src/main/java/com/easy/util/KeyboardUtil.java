package com.easy.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author Joke
 * @version 1.0.0
 * @description
 * @email 113979462@qq.com
 * @create 2015年4月16日
 */

public class KeyboardUtil {
	private KeyboardUtil() {
	}

	public static void toggle(Context context) {
		InputMethodManager imm = SystemServiceUtil.getInputMethodManager(context);
		imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void show(Context context, View view) {
		InputMethodManager imm = SystemServiceUtil.getInputMethodManager(context);
		imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}

	public static void hide(Context context, View view) {
		InputMethodManager imm = SystemServiceUtil.getInputMethodManager(context);
		imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void showDelayed(final Context context, final View view, long delayMillis) {
		view.postDelayed(new Runnable() {
			@Override
			public void run() {
				show(context, view);
			}
		}, delayMillis);
	}
}
