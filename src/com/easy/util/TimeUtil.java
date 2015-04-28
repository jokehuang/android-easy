package com.easy.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	/**
	 * 描述之前的某个时刻距离现在多久，一般用于检查版本更新
	 * 
	 * @param currentTime
	 * @param lastTime
	 * @return
	 */
	public static String toLastTimeString(long currentTime, long lastTime) {
		if (currentTime < lastTime) {
			return "";
		}
		if (lastTime == 0) {
			return "";
		}

		int subTime = (int) (currentTime - lastTime);
		subTime /= 1000 * 60;
		if (subTime == 0) {
			return "刚刚";
		}
		if (subTime < 60) {
			return subTime + "分钟前";
		}
		subTime /= 60;
		if (subTime < 24) {
			return subTime + "小时前";
		}

		Calendar currentCalendar = Calendar.getInstance();
		Calendar lastCalendar = Calendar.getInstance();
		currentCalendar.setTimeInMillis(currentTime);
		lastCalendar.setTimeInMillis(lastTime);
		if (currentCalendar.get(Calendar.YEAR) == lastCalendar
				.get(Calendar.YEAR)) {
			return (lastCalendar.get(Calendar.MONTH) + 1) + "月"
					+ lastCalendar.get(Calendar.DATE) + "日";
		}
		return lastCalendar.get(Calendar.YEAR) + "年"
				+ (lastCalendar.get(Calendar.MONTH) + 1) + "月"
				+ lastCalendar.get(Calendar.DATE) + "日";
	}
}
