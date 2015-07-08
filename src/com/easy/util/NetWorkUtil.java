package com.easy.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * NetWorkUtil
 * 
 * @Description
 * @author Joke Huang
 * @createDate 2014年7月21日
 * @version 1.0.0
 */

public class NetWorkUtil {

	private NetWorkUtil() {
	}

	/**
	 * 网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connManager = SystemServiceUtil
				.getConnectivityManager(context);
		NetworkInfo netInfo = connManager.getActiveNetworkInfo();
		if (netInfo != null) {
			return netInfo.isConnected();
		}
		return false;
	}

	/**
	 * wifi是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager connManager = SystemServiceUtil
				.getConnectivityManager(context);
		NetworkInfo netInfo = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (netInfo != null) {
			return netInfo.isConnected();
		}
		return false;
	}

	/**
	 * 移动网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context) {
		ConnectivityManager connManager = SystemServiceUtil
				.getConnectivityManager(context);
		NetworkInfo netInfo = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (netInfo != null) {
			return netInfo.isConnected();
		}
		return false;
	}

	/**
	 * 获取手机的ip地址
	 * 
	 * @return
	 */
	public static String getClientIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						// && inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取手机mac地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {
		try {
			WifiManager wifi = SystemServiceUtil.getWifiManager(context);
			WifiInfo info = wifi.getConnectionInfo();
			return info.getMacAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}