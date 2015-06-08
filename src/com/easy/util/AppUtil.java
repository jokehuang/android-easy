package com.easy.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年6月8日
 * @version 1.0.0
 */

public class AppUtil {
	private AppUtil() {
	}

	/**
	 * 判断app是否在前台
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isForeground(Context context) {
		ActivityManager activityManager = SystemServiceUtil
				.getActivityManager(context);
		String packageName = context.getPackageName();
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null)
			return false;
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断app是否在后台
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isBackground(Context context) {
		return !isForeground(context);
	}
}
