package com.easy.util;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年3月30日
 * @version 1.0.0
 */

public class SystemInfo {

	private SystemInfo() {
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public static String getModel() {
		return android.os.Build.MODEL;
	}

	/**
	 * 获取系统版本
	 * 
	 * @return
	 */
	public static String getOS() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取SDK版本
	 * 
	 * @return
	 */
	public static int getSDK() {
		return android.os.Build.VERSION.SDK_INT;
	}
}
