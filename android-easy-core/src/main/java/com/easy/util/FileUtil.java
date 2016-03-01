package com.easy.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Environment;

/**
 * FileUtil
 *
 * @author Joke Huang
 * @version 1.0.0
 * @createDate 2014年2月28日
 */

public class FileUtil {

	public static final long B = 1;
	public static final long KB = B << 10;
	public static final long MB = KB << 10;
	public static final long GB = MB << 10;
	public static final long TB = GB << 10;
	public static final long PB = TB << 10;

	private FileUtil() {
	}

	public static String formatSize(long size) {
		if (size >= PB) {
			return formatSize(size, PB, "PB");
		} else if (size >= TB) {
			return formatSize(size, TB, "TB");
		} else if (size >= GB) {
			return formatSize(size, GB, "GB");
		} else if (size >= MB) {
			return formatSize(size, MB, "MB");
		} else if (size >= KB) {
			return formatSize(size, KB, "KB");
		} else {
			return formatSize(size, B, "B");
		}
	}

	public static String formatSize(long size, long unit, String unitString) {
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(2);
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format((double) size / unit) + " " + unitString;
	}

	public static <T> T getObj(Context context, String key, Class<T> objClass) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(context.getFileStreamPath(key + "" +
					".obj")));
			return (T) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean putObj(Context context, String key, Object obj) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(context.getFileStreamPath(key + "" +
					".obj"), false));
			oos.writeObject(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static <T> T agentInput(String path, Inputer<T> inputer) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			return inputer.doInput(fis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean agentOutput(String path, Outputer outputer) {
		FileOutputStream fos = null;
		try {
			File file = new File(path);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(file);
			outputer.doOutput(fos);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean clear(File file) {
		return delete(file, true, null);
	}

	public static boolean delete(File file, boolean isDeep, FileFilter filter) {
		if (filter == null || filter.accept(file)) {
			if (isDeep && file.isDirectory()) {
				for (File subFile : file.listFiles()) {
					if (!delete(subFile, true, filter)) {
						return false;
					}
				}
			}
			return delete(file);
		}
		return true;
	}

	public static boolean delete(File file) {
		try {
			return file.delete();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean hasExternalStorage() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	public interface Inputer<T> {
		T doInput(FileInputStream fis) throws IOException;
	}

	public interface Outputer {
		void doOutput(FileOutputStream fos) throws IOException;
	}
}
