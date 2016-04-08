package com.easy.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @author Joke
 * @version 1.0.0
 * @description
 * @email 113979462@qq.com
 * @create 2015年6月8日
 */

public class ImmersionUtil {
	private ImmersionUtil() {
	}

	public static void set(Activity act, int color) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
		View contentView = ((ViewGroup) act.findViewById(android.R.id.content)).getChildAt(0);
		if (!(contentView instanceof ViewGroup)) return;
		ViewGroup contentViewGroup = (ViewGroup) contentView;

		act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		TextView tv = new TextView(act);
		tv.setFitsSystemWindows(true);
		tv.setBackgroundColor(color);
		ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
				.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		contentViewGroup.addView(tv, 0, lp);
	}
}
