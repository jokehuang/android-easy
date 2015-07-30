package com.easy.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * TimeUtil
 * 
 * @author Joke Huang
 * @createDate 2014年3月22日
 * @version 1.0.0
 */

public class TimeUtil {
	
	private TimeUtil(){
	}
	
	/**
	 * 解析时间
	 * 
	 * @param time
	 *            时间字符串
	 * @param pattern
	 *            时间表达式
	 * @return
	 */
	public static Date parse(String time, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					Locale.getDefault());
			return sdf.parse(time);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 格式化时间
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String format(long time, String pattern) {
		return format(new Date(time), pattern);
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern,
					Locale.getDefault());
			return sdf.format(date);
		} catch (Exception e) {
			return "";
		}
	}
}
