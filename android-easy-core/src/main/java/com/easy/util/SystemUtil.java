package com.easy.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年6月8日
 * @version 1.0.0
 */

public class SystemUtil {
	private SystemUtil() {
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

	// WINDOW_SERVICE WindowManager 管理打开的窗口程序
	public static WindowManager getWindowManager(Context context) {
		return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	}

	// LAYOUT_INFLATER_SERVICE LayoutInflater 取得xml里定义的view
	public static LayoutInflater getLayoutInflater(Context context) {
		return (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// ACTIVITY_SERVICE ActivityManager 管理应用程序的系统状态
	public static ActivityManager getActivityManager(Context context) {
		return (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
	}

	// POWER_SERVICE PowerManager 电源的服务
	public static PowerManager getPowerManger(Context context) {
		return (PowerManager) context.getSystemService(Context.POWER_SERVICE);
	}

	// ALARM_SERVICE AlarmManager 闹钟的服务
	public static AlarmManager getAlarmManager(Context context) {
		return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}

	// NOTIFICATION_SERVICE NotificationManager 状态栏的服务
	public static NotificationManager getNotificationManager(Context context) {
		return (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	// KEYGUARD_SERVICE KeyguardManager 键盘锁的服务
	public static KeyguardManager getKeyguardManager(Context context) {
		return (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
	}

	// LOCATION_SERVICE LocationManager 位置的服务，如GPS
	public static LocationManager getLocationManager(Context context) {
		return (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
	}

	// SEARCH_SERVICE SearchManager 搜索的服务
	public static SearchManager getSearchManager(Context context) {
		return (SearchManager) context.getSystemService(Context.SEARCH_SERVICE);
	}

	// VIBRATOR_SERVICE Vibrator 手机震动的服务
	public static Vibrator getVebrator(Context context) {
		return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	// CONNECTIVITY_SERVICE Connectivity 网络连接的服务
	public static ConnectivityManager getConnectivityManager(Context context) {
		return (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	// WIFI_SERVICE WifiManager Wi-Fi服务
	public static WifiManager getWifiManager(Context context) {
		return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	}

	// TELEPHONY_SERVICE TelephonyManager 电话服务
	public static TelephonyManager getTelephonyManager(Context context) {
		return (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
	}

	// INPUT_METHOD_SERVICE InputMethodManager 软键盘服务
	public static InputMethodManager getInputMethodManager(Context context) {
		return (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	// AUDIO_SERVICE AudioManager 音频服务
	public static AudioManager getAudioManager(Context context) {
		return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	}

	// BLUETOOTH_SERVICE BluetoothManager 蓝牙服务
	@SuppressLint("InlinedApi")
	public static BluetoothManager getBluetoothManager(Context context) {
		return (BluetoothManager) context
				.getSystemService(Context.BLUETOOTH_SERVICE);
	}

}
