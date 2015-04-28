package com.easy.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;
import android.util.Log;

/**
 * LogObject
 * 
 * @Description
 * @author Joke Huang
 * @createDate 2014年6月30日
 * @version 1.0.0
 */

public class LogUtil {
	// default tag
	public static final String tag = LogUtil.class.getSimpleName();
	// output degree
	public static final int DEGREE_VERBOSE = 1;
	public static final int DEGREE_DEBUG = 2;
	public static final int DEGREE_INFO = 3;
	public static final int DEGREE_WARN = 4;
	public static final int DEGREE_ERROR = 5;
	public static final int DEGREE_ASSERT = 6;
	// log到文件中的有效期60天
	public static long datedTime = 60 * 24 * 3600000l;
	// log的输出等级
	public static int degree = DEGREE_VERBOSE;

	private LogUtil() {
	}

	/**
	 * 清除已过期的log，一般在应用启动的时候调用
	 * 
	 * @param dirName
	 *            目录名
	 */
	public static void clearDatedLog(String dirName) {
		if (!FileUtil.hasExternalStorage()) {
			return;
		}

		File dir = new File(Environment.getExternalStorageDirectory(), dirName);

		if (!dir.exists()) {
			return;
		}

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",
				Locale.getDefault());
		final long now = System.currentTimeMillis();
		File[] datedLogs = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				try {
					Date logTime = sdf.parse(filename.replaceAll(".log", ""));
					if (now - logTime.getTime() > datedTime)
						return true;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return false;
			}
		});

		for (File datedLog : datedLogs) {
			try {
				datedLog.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将log写到文件中，以日期为文件名，每条log开头用时间戳分隔
	 * 
	 * @param dirName
	 *            目录名
	 * @param message
	 *            log的内容
	 */
	public static void log2File(String dirName, String message) {
		if (!FileUtil.hasExternalStorage()) {
			return;
		}

		String[] currentTime = new SimpleDateFormat("yyyyMMdd-HH:mm:ss",
				Locale.getDefault()).format(new Date()).split("-");
		String fileName = currentTime[0] + ".log";

		File dir = new File(Environment.getExternalStorageDirectory(), dirName);

		if (!dir.exists()) {
			dir.mkdirs();
		}
		File logFile = new File(dir, fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(logFile, logFile.exists());
			String boundary = "========== " + currentTime[1] + " =========="
					+ "\n";
			fos.write(boundary.getBytes());
			fos.write(message.getBytes());
			fos.write("\n\n".getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void v(String tag, Object obj) {
		if (degree <= DEGREE_VERBOSE)
			Log.v(tag, getStr(obj));
	}

	public static void v0(Object obj) {
		if (degree <= DEGREE_VERBOSE)
			Log.v(tag, getStr(obj));
	}

	public static void d(String tag, Object obj) {
		if (degree <= DEGREE_DEBUG)
			Log.d(tag, getStr(obj));
	}

	public static void d0(Object obj) {
		if (degree <= DEGREE_DEBUG)
			Log.d(tag, getStr(obj));
	}

	public static void i(String tag, Object obj) {
		if (degree <= DEGREE_INFO)
			Log.i(tag, getStr(obj));
	}

	public static void i0(Object obj) {
		if (degree <= DEGREE_INFO)
			Log.i(tag, getStr(obj));
	}

	public static void w(String tag, Object obj) {
		if (degree <= DEGREE_WARN)
			Log.w(tag, getStr(obj));
	}

	public static void w0(Object obj) {
		if (degree <= DEGREE_WARN)
			Log.w(tag, getStr(obj));
	}

	public static void e(String tag, Object obj) {
		if (degree <= DEGREE_ERROR)
			Log.e(tag, getStr(obj));
	}

	public static void e0(Object obj) {
		if (degree <= DEGREE_ERROR)
			Log.e(tag, getStr(obj));
	}

	public static void wtf(String tag, Object obj) {
		if (degree <= DEGREE_ASSERT)
			Log.wtf(tag, getStr(obj));
	}

	public static void wtf0(Object obj) {
		if (degree <= DEGREE_ASSERT)
			Log.wtf(tag, getStr(obj));
	}

	/**
	 * 调用object.toStirng方法拼接字符串
	 * 
	 * @param objs
	 * @return
	 */
	private static String getStr(Object obj) {
		return obj == null ? "null" : obj.toString();
	}

	// public static void v(String tag, Object... objs) {
	// if (degree <= DEGREE_VERBOSE)
	// Log.v(tag, getStr(objs));
	// }
	//
	// public static void v0(Object... objs) {
	// if (degree <= DEGREE_VERBOSE)
	// Log.v(tag, getStr(objs));
	// }
	//
	// public static void d(String tag, Object... objs) {
	// if (degree <= DEGREE_DEBUG)
	// Log.d(tag, getStr(objs));
	// }
	//
	// public static void d0(Object... objs) {
	// if (degree <= DEGREE_DEBUG)
	// Log.d(tag, getStr(objs));
	// }
	//
	// public static void i(String tag, Object... objs) {
	// if (degree <= DEGREE_INFO)
	// Log.i(tag, getStr(objs));
	// }
	//
	// public static void i0(Object... objs) {
	// if (degree <= DEGREE_INFO)
	// Log.i(tag, getStr(objs));
	// }
	//
	// public static void w(String tag, Object... objs) {
	// if (degree <= DEGREE_WARN)
	// Log.w(tag, getStr(objs));
	// }
	//
	// public static void w0(Object... objs) {
	// if (degree <= DEGREE_WARN)
	// Log.w(tag, getStr(objs));
	// }
	//
	// public static void e(String tag, Object... objs) {
	// if (degree <= DEGREE_ERROR)
	// Log.e(tag, getStr(objs));
	// }
	//
	// public static void e0(Object... objs) {
	// if (degree <= DEGREE_ERROR)
	// Log.e(tag, getStr(objs));
	// }
	//
	// public static void wtf(String tag, Object... objs) {
	// if (degree <= DEGREE_ASSERT)
	// Log.wtf(tag, getStr(objs));
	// }
	//
	// public static void wtf0(Object... objs) {
	// if (degree <= DEGREE_ASSERT)
	// Log.wtf(tag, getStr(objs));
	// }
	//
	// /**
	// * 调用object.toStirng方法拼接字符串
	// *
	// * @param objs
	// * @return
	// */
	// private static String getStr(Object... objs) {
	// if (objs == null || objs.length == 0 || objs[0] == null) {
	// return "null";
	// }
	//
	// String str = objs[0].toString();
	// for (int i = 1; i < objs.length; i++) {
	// str += "; " + objs[i];
	// }
	// return str;
	// }

	public static long getDatedTime() {
		return datedTime;
	}

	public static void setDatedTime(long datedTime) {
		LogUtil.datedTime = datedTime;
	}
}
