package com.easy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;

/**
 * DataConvert
 * 
 * @Description
 * @author Joke Huang
 * @createDate 2014年7月2日
 * @version 1.0.0
 */

public class DataUtil {

	private DataUtil() {
	}

	/**
	 * 获取指定文件的MD5码
	 * 
	 * @param file
	 * @return
	 */
	public static String getMD5(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte[] date = new byte[1024 * 32];
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			int len;
			while ((len = fis.read(date)) != -1) {
				md5.update(date, 0, len);
			}
			return bytesToHexString(md5.digest());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 将指定字符串转换为MD5
	 * 
	 * @param src
	 * @return
	 */
	public static String toMD5(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(src.getBytes());
			return bytesToHexString(md5.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src
	 *            byte[] data
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	@SuppressLint("DefaultLocale")
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.trim().toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 反转byte数组
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] revert(byte[] bytes) {
		byte[] result = new byte[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			result[i] = bytes[bytes.length - i - 1];
		}
		return result;
	}

	/**
	 * 合并byte数组
	 * 
	 * @param bytes1
	 * @param bytes2
	 * @return
	 */
	public static byte[] merge(byte[] bytes1, byte[] bytes2) {
		byte[] bytes3 = new byte[bytes1.length + bytes2.length];
		System.arraycopy(bytes1, 0, bytes3, 0, bytes1.length);
		System.arraycopy(bytes2, 0, bytes3, bytes1.length, bytes2.length);
		return bytes3;
	}

	public static int compare(int i1, int i2) {
		return i1 == i2 ? 0 : (i1 < i2 ? -1 : 1);
	}
}
