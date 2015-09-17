package com.easy.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.os.Environment;

/**
 * FileUtil
 * 
 * @author Joke Huang
 * @createDate 2014年2月28日
 * @version 1.0.0
 */

public class FileUtil {

	private FileUtil() {
	}

	public static Object getObj(Context context, String key) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(
					context.getFileStreamPath(key + ".obj")));
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean putObj(Context context, String key, Object obj) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(
					context.getFileStreamPath(key + ".obj"), false));
			oos.writeObject(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
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
				if (fis != null)
					fis.close();
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
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean clear(File[] files) {
		boolean result = true;
		for (File subFile : files) {
			try {
				subFile.delete();
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	}

	public static boolean clear(String dir) {
		return clear(new File(dir).listFiles());
	}

	public static boolean clear(String dir, FileFilter fileFilter) {
		return clear(new File(dir).listFiles(fileFilter));
	}

	public static boolean clear(String dir, FilenameFilter filenameFilter) {
		return clear(new File(dir).listFiles(filenameFilter));
	}

	public static boolean delete(String path) {
		try {
			return new File(path).delete();
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean hasExternalStorage() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	public interface Inputer<T> {
		T doInput(FileInputStream fis) throws IOException;
	}

	public interface Outputer {
		void doOutput(FileOutputStream fos) throws IOException;
	}
}
