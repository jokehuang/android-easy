package com.easy.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * NetworkUtil
 *
 * @author Joke Huang
 * @version 1.0.0
 * @Description
 * @createDate 2014年7月21日
 */

public class NetworkUtil {

    private NetworkUtil() {
    }

    /**
     * 网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connManager = SystemUtil.getConnectivityManager(context);
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
        ConnectivityManager connManager = SystemUtil.getConnectivityManager(context);
        NetworkInfo netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
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
        ConnectivityManager connManager = SystemUtil.getConnectivityManager(context);
        NetworkInfo netInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
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
    public static String getIpAddress(Context context) {
        try {
            if (!isNetworkConnected(context)) {
                return null;
            }
            if (isMobileConnected(context)) {
                Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                while (en.hasMoreElements()) {
                    NetworkInterface intf = en.nextElement();
                    Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                    while (enumIpAddr.hasMoreElements()) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address || inetAddress
                                instanceof Inet6Address)) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            } else if (isWifiConnected(context)) {
                WifiManager wifiManager = SystemUtil.getWifiManager(context);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int ipAddress = wifiInfo.getIpAddress();
                return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 &
                        0xff), (ipAddress >> 24 & 0xff));
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
            WifiManager wifi = SystemUtil.getWifiManager(context);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWifiSSID(Context context) {
        WifiManager wifiManager = SystemUtil.getWifiManager(context);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        int deviceVersion = Build.VERSION.SDK_INT;
        if (deviceVersion >= 17 && ssid.startsWith("\"") && ssid.endsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        if (EmptyUtil.isEmpty(ssid) || ssid.equals("<unknown ssid>")) {
            return null;
        } else {
            return ssid;
        }
    }
}