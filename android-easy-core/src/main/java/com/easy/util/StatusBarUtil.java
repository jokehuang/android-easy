package com.easy.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * @author Joke
 * @version 1.0.0
 * @description
 * @email 113979462@qq.com
 * @create 2015年6月8日
 */

public class StatusBarUtil {
	private StatusBarUtil() {
	}

	/**
	 * 设置状态栏颜色
	 *
	 * @param act
	 * @param color
	 */
	public static void setColor(Activity act, int color) {
		//版本不足
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;

		//未初始化view
		ViewGroup rootView = (ViewGroup) act.findViewById(android.R.id.content);
		if (rootView == null) return;

		//未设置contentView
		View contentView = rootView.getChildAt(0);
		if (contentView == null) return;

		//状态栏高度小于0
		int height = getHeight(act);
		if (height <= 0) return;

		//设置沉浸式
		act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		//避免状态栏覆盖内容，将内容位置下移
		ViewGroup.MarginLayoutParams mpl = (ViewGroup.MarginLayoutParams) contentView
				.getLayoutParams();
		mpl.topMargin += height;
		contentView.setLayoutParams(mpl);

		//添加新的背景色
		View v = new View(act);
		v.setBackgroundColor(color);
		rootView.addView(v, ViewGroup.LayoutParams.MATCH_PARENT, height);
	}

	/**
	 * 获取状态栏高度
	 *
	 * @param context
	 * @return
	 */
	public static int getHeight(Context context) {
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
				"android");
		if (resourceId > 0) {
			return context.getResources().getDimensionPixelSize(resourceId);
		}
		return 0;
	}
}
