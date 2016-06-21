package com.easy.util;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

/**
 * DataConvert
 *
 * @author Joke Huang
 * @version 1.0.0
 * @Description
 * @createDate 2014年7月2日
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
    public static String encodeMD5(File file) {
        return FileUtil.agentInput(file, new FileUtil.Inputer<String>() {
            @Override
            public String doInput(FileInputStream fis) throws IOException {
                try {
                    MessageDigest md5 = MessageDigest.getInstance("MD5");
                    byte[] date = new byte[1024 * 32];
                    int len;
                    while ((len = fis.read(date)) != -1) {
                        md5.update(date, 0, len);
                    }
                    return bytesToHexString(md5.digest());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    /**
     * 将指定字符串转换为MD5
     *
     * @param str
     * @return
     */
    public static String encodeMD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
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
     * @param src byte[] data
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
     * @param hexString the hex string
     * @return byte[]
     */
    @SuppressLint("DefaultLocale")
    public static byte[] hexStringToBytes(String hexString) throws ParseException {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.replaceFirst("0x", "");
        if (hexString.length() % 2 == 1) {
            hexString = "0" + hexString;
        }
        hexString = hexString.trim().toLowerCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            byte hight4Bit = charToByte(hexChars[pos]);
            byte low4Bit = charToByte(hexChars[pos + 1]);
            if (hight4Bit < 0) throw new ParseException("found not hex string: " + hexString, pos);
            if (low4Bit < 0) throw new ParseException("found not hex string: " + hexString, pos + 1);
            d[i] = (byte) (hight4Bit << 4 | low4Bit);
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }

    /**
     * 反转byte数组
     *
     * @param bytes
     * @return
     */
    public static byte[] reverse(byte[] bytes) {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[bytes.length - i - 1];
        }
        return result;
    }

    /**
     * 复制一个byte数组
     *
     * @param bytes
     * @return
     */
    public static byte[] copy(byte[] bytes) {
        byte[] copyBytes = new byte[bytes.length];
        System.arraycopy(bytes, 0, copyBytes, 0, bytes.length);
        return copyBytes;
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

    /**
     * 兼容低版本
     *
     * @param i1
     * @param i2
     * @return
     */
    static int compare(int i1, int i2) {
        return i1 == i2 ? 0 : (i1 > i2 ? 1 : -1);
    }
}
