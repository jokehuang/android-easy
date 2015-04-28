package com.easy.util;

import android.util.SparseArray;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年4月16日
 * @version 1.0.0
 */

public class VersionUtil {
	public static final SparseArray<String> ANDROID_VERSIONS = new SparseArray<String>();

	static {
		ANDROID_VERSIONS.append(21, "5.0.1");
		ANDROID_VERSIONS.append(20, "4.4W.2");
		ANDROID_VERSIONS.append(19, "4.4.2");
		ANDROID_VERSIONS.append(18, "4.3.1");
		ANDROID_VERSIONS.append(17, "4.2.2");
		ANDROID_VERSIONS.append(16, "4.1.2");
		ANDROID_VERSIONS.append(15, "4.0.3");
		ANDROID_VERSIONS.append(14, "4.0");
		ANDROID_VERSIONS.append(13, "3.2");
		ANDROID_VERSIONS.append(12, "3.1");
		ANDROID_VERSIONS.append(11, "3.0");
		ANDROID_VERSIONS.append(10, "2.3.3");
		ANDROID_VERSIONS.append(8, "2.2");
		ANDROID_VERSIONS.append(7, "2.1");
		ANDROID_VERSIONS.append(4, "1.6");
		ANDROID_VERSIONS.append(3, "1.5");
	}

	private VersionUtil() {
	}

	/**
	 * 根据sdk版本获取android版本号
	 * 
	 * @param sdkVersion
	 * @return
	 */
	public static String getAndroidVersionBySDK(int sdkVersion) {
		return ANDROID_VERSIONS.get(sdkVersion, "unknown");
	}

	/**
	 * 对比两个版本号，版本号格式类似1.2.0
	 * 
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compare(String version1, String version2)
			throws NumberFormatException {
		// 去掉非数字和非“.”的字符，避免传入非法字符
		version1 = version1.replaceAll("[^0-9.]", "");
		version2 = version2.replaceAll("[^0-9.]", "");
		// 用“.”将版本号分开多段
		String[] codes1 = version1.split("\\.");
		String[] codes2 = version2.split("\\.");
		// 对比每个“.”之间的数字大小
		int minLength = Math.min(codes1.length, codes2.length);
		for (int i = 0; i < minLength; i++) {
			int code1 = Integer.parseInt(codes1[i]);
			int code2 = Integer.parseInt(codes2[i]);
			// 如果两个版本的数字一样，则继续对比下一个数字
			if (code1 == code2) {
				continue;
			} else {
				// 如果两个版本数字大小不一样，前者小则返回-1，表示前者版本比较旧，否则返回1
				return DataUtil.compare(code1, code2);
			}
		}
		// 如果两个版本号前段数字全部相同，则“.”比较多的版本号作为较新的版本
		return DataUtil.compare(codes1.length, codes2.length);
	}

}
