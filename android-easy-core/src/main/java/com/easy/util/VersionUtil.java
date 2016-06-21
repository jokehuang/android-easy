package com.easy.util;

import android.os.Build;
import android.util.SparseArray;

/**
 * @author Joke
 * @version 1.0.0
 * @description
 * @email 113979462@qq.com
 * @create 2015年4月16日
 */

public class VersionUtil {
    public static final SparseArray<String> ANDROID_VERSIONS = new SparseArray<String>();

    static {
        ANDROID_VERSIONS.append(Build.VERSION_CODES.CUR_DEVELOPMENT, "dev");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.BASE, "1.0");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.BASE_1_1, "1.1");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.CUPCAKE, "1.5");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.DONUT, "1.6");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.ECLAIR, "2.0");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.ECLAIR_0_1, "2.0.1");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.ECLAIR_MR1, "2.1");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.FROYO, "2.2");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.GINGERBREAD, "2.3");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.GINGERBREAD_MR1, "2.3.3");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.HONEYCOMB, "3.0");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.HONEYCOMB_MR1, "3.1");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.HONEYCOMB_MR2, "3.2");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.ICE_CREAM_SANDWICH, "4.0");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1, "4.0.3");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.JELLY_BEAN, "4.1");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.JELLY_BEAN_MR1, "4.2");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.JELLY_BEAN_MR2, "4.3");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.KITKAT, "4.4");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.KITKAT_WATCH, "4.4W");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.LOLLIPOP, "5.0");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.LOLLIPOP_MR1, "5.1");
        ANDROID_VERSIONS.append(Build.VERSION_CODES.M, "6.0");
    }

    private VersionUtil() {
    }

    /**
     * 根据sdk版本获取android版本号
     *
     * @param sdkVersion
     * @return
     */
    public static String toPlatformVersion(int sdkVersion) {
        return ANDROID_VERSIONS.get(sdkVersion, "unknown");
    }

    public static String currentPlatformVersion() {
        return toPlatformVersion(SystemUtil.getSDK());
    }

    /**
     * 对比两个版本号，版本号格式类似1.2.0
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compare(String version1, String version2) throws NumberFormatException {
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
