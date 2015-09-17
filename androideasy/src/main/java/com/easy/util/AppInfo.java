package com.easy.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年3月30日
 * @version 1.0.0
 */

public class AppInfo {

	private AppInfo() {
	}

	/**
	 * 获取版本名
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			return "";
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			return -1;
		}
	}
}
