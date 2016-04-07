package com.easy.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * FileUtil
 *
 * @author Joke Huang
 * @version 1.0.0
 * @createDate 2014年2月28日
 */

public class FileUtil {
	private static final String TAG = FileUtil.class.getSimpleName();

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
		return agentInput(new File(path), inputer);
	}

	public static <T> T agentInput(File file, Inputer<T> inputer) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			return inputer.doInput(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

	public static boolean agentOutput(String path, Outputer outputer, boolean append) {
		return agentOutput(new File(path), outputer, append);
	}

	public static boolean agentOutput(File file, Outputer outputer, boolean append) {
		FileOutputStream fos = null;
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(file, append);
			outputer.doOutput(fos);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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

	public static boolean clear(String path, boolean isDeep) {
		return clear(new File(path), isDeep);
	}

	public static boolean clear(File file, boolean isDeep) {
		if (!file.isDirectory()) return false;

		File[] list = file.listFiles();
		if (list != null) {// 子文件正在被写入, 文件属性异常返回null.
			for (File subFile : list) {
				if (!subFile.isDirectory() || isDeep) {
					delete(file);
				}
			}
		}
		return true;
	}

	public static boolean delete(String path) {
		return delete(new File(path));
	}

	public static boolean delete(File file) {
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			if (list != null) {// 子文件正在被写入, 文件属性异常返回null.
				for (File subFile : list) {
					delete(subFile);
				}
			}
		}
		try {
			LogUtil.i(TAG, "delete file: " + file.getAbsolutePath());
			return file.delete();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static long calculateSize(String path) {
		return calculateSize(new File(path));
	}

	public static long calculateSize(File file) {
		if (!file.exists()) return 0;
		if (!file.isDirectory()) return file.length();

		long length = 0;
		File[] list = file.listFiles();
		if (list != null) { // 子文件正在被写入, 文件属性异常返回null.
			for (File item : list) {
				length += calculateSize(item);
			}
		}

		return length;
	}

	public static boolean hasExternalStorage() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	public static String encodeMD5(String path) {
		return encodeMD5(new File(path));
	}

	public static String encodeMD5(File file) {
		return DataUtil.encodeMD5(file);
	}

	public interface Inputer<T> {
		T doInput(FileInputStream fis) throws IOException;
	}

	public interface Outputer {
		void doOutput(FileOutputStream fos) throws IOException;
	}
}
