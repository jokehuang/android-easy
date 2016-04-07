package com.easy.util;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * LogObject 方法名后缀为0的方法将使用不含堆栈信息的tag
 *
 * @author Joke Huang
 * @version 1.0.0
 * @Description
 * @createDate 2014年6月30日
 */

public class LogUtil {
	// default tag
	private static final String tag = LogUtil.class.getSimpleName();
	// output degree
	public static final int DEGREE_VERBOSE = 1;
	public static final int DEGREE_DEBUG = 2;
	public static final int DEGREE_INFO = 3;
	public static final int DEGREE_WARN = 4;
	public static final int DEGREE_ERROR = 5;
	public static final int DEGREE_ASSERT = 6;
	// log到文件中的有效期60天
	private static long datedTime = 60 * 24 * 3600000l;
	// log的输出等级
	private static int degree = DEGREE_ASSERT;

	private LogUtil() {
	}

	/**
	 * 清除已过期的log，一般在应用启动的时候调用
	 *
	 * @param dirPath 目录名
	 */
	public static boolean clearDatedLog(String dirPath) {
		File dir = new File(dirPath);
		if (!dir.exists()) return false;

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		final long now = System.currentTimeMillis();
		File[] datedLogs = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				try {
					Date logTime = sdf.parse(filename.replaceAll(".log", ""));
					if (now - logTime.getTime() > datedTime) return true;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return false;
			}
		});

		for (File datedLog : datedLogs) {
			FileUtil.delete(datedLog);
		}
		return true;
	}

	/**
	 * 将log写到文件中，以日期为文件名，每条log开头用时间戳分隔
	 *
	 * @param dirPath 目录名
	 * @param message log的内容
	 */
	public static boolean log2File(String dirPath, final String message) {
		return log2File(DEGREE_ASSERT, dirPath, message);
	}

	public static boolean log2File(int degree, String dirPath, final String message) {
		if (degree < LogUtil.degree) return false;

		final String dateStr = TimeUtil.format(new Date(), "yyyyMMdd");
		final String timeStr = TimeUtil.format(new Date(), "HH:mm:ss");
		String fileName = dateStr + ".log";

		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File logFile = new File(dir, fileName);
		return FileUtil.agentOutput(logFile, new FileUtil.Outputer() {
			@Override
			public void doOutput(FileOutputStream fos) throws IOException {
				String boundary = "========== " + timeStr + " ==========" + "\n";
				fos.write(boundary.getBytes());
				fos.write(message.getBytes());
				fos.write("\n\n".getBytes());
			}
		}, true);
	}

	/**
	 * v
	 *
	 * @param tag
	 * @param obj
	 */
	public static void v(String tag, Object obj) {
		if (degree <= DEGREE_VERBOSE) Log.v(generateTag(tag), getStr(obj));
	}

	public static void v(Object obj) {
		if (degree <= DEGREE_VERBOSE) Log.v(generateTag(tag), getStr(obj));
	}

	public static void v0(String tag, Object obj) {
		if (degree <= DEGREE_VERBOSE) Log.v(tag, getStr(obj));
	}

	public static void v0(Object obj) {
		if (degree <= DEGREE_VERBOSE) Log.v(tag, getStr(obj));
	}

	/**
	 * d
	 *
	 * @param tag
	 * @param obj
	 */
	public static void d(String tag, Object obj) {
		if (degree <= DEGREE_DEBUG) Log.d(generateTag(tag), getStr(obj));
	}

	public static void d(Object obj) {
		if (degree <= DEGREE_DEBUG) Log.d(generateTag(tag), getStr(obj));
	}

	public static void d0(String tag, Object obj) {
		if (degree <= DEGREE_DEBUG) Log.d(tag, getStr(obj));
	}

	public static void d0(Object obj) {
		if (degree <= DEGREE_DEBUG) Log.d(tag, getStr(obj));
	}

	/**
	 * i
	 *
	 * @param tag
	 * @param obj
	 */
	public static void i(String tag, Object obj) {
		if (degree <= DEGREE_INFO) Log.i(generateTag(tag), getStr(obj));
	}

	public static void i(Object obj) {
		if (degree <= DEGREE_INFO) Log.i(generateTag(tag), getStr(obj));
	}

	public static void i0(String tag, Object obj) {
		if (degree <= DEGREE_INFO) Log.i(tag, getStr(obj));
	}

	public static void i0(Object obj) {
		if (degree <= DEGREE_INFO) Log.i(tag, getStr(obj));
	}

	/**
	 * w
	 *
	 * @param tag
	 * @param obj
	 */
	public static void w(String tag, Object obj) {
		if (degree <= DEGREE_WARN) Log.w(generateTag(tag), getStr(obj));
	}

	public static void w(Object obj) {
		if (degree <= DEGREE_WARN) Log.w(generateTag(tag), getStr(obj));
	}

	public static void w0(String tag, Object obj) {
		if (degree <= DEGREE_WARN) Log.w(tag, getStr(obj));
	}

	public static void w0(Object obj) {
		if (degree <= DEGREE_WARN) Log.w(tag, getStr(obj));
	}

	/**
	 * e
	 *
	 * @param tag
	 * @param obj
	 */
	public static void e(String tag, Object obj) {
		if (degree <= DEGREE_ERROR) Log.e(generateTag(tag), getStr(obj));
	}

	public static void e(Object obj) {
		if (degree <= DEGREE_ERROR) Log.e(generateTag(tag), getStr(obj));
	}

	public static void e0(String tag, Object obj) {
		if (degree <= DEGREE_ERROR) Log.e(tag, getStr(obj));
	}

	public static void e0(Object obj) {
		if (degree <= DEGREE_ERROR) Log.e(tag, getStr(obj));
	}

	/**
	 * wtf
	 *
	 * @param tag
	 * @param obj
	 */
	public static void wtf(String tag, Object obj) {
		if (degree <= DEGREE_ASSERT) Log.wtf(generateTag(tag), getStr(obj));
	}

	public static void wtf(Object obj) {
		if (degree <= DEGREE_ASSERT) Log.wtf(generateTag(tag), getStr(obj));
	}

	public static void wtf0(String tag, Object obj) {
		if (degree <= DEGREE_ASSERT) Log.wtf(tag, getStr(obj));
	}

	public static void wtf0(Object obj) {
		if (degree <= DEGREE_ASSERT) Log.wtf(tag, getStr(obj));
	}

	/**
	 * 创建含有基本堆栈信息的tag
	 *
	 * @param customTag
	 * @return
	 */
	private static String generateTag(String customTag) {
		StackTraceElement caller = new Throwable().getStackTrace()[2];
		String tag = "%s.%s(L:%d)";
		String callerClazzName = caller.getClassName();
		callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
		tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
		tag = EmptyUtil.isEmpty(customTag) ? tag : customTag + ":" + tag;
		return tag;
	}

	/**
	 * 调用object.toStirng方法拼接字符串
	 *
	 * @param obj
	 * @return
	 */
	private static String getStr(Object obj) {
		return obj == null ? "null" : obj.toString();
	}

	public static long getDatedTime() {
		return datedTime;
	}

	public static void setDatedTime(long datedTime) {
		LogUtil.datedTime = datedTime;
	}

	public static int getDegree() {
		return degree;
	}

	public static void setDegree(int degree) {
		LogUtil.degree = degree;
	}
}
