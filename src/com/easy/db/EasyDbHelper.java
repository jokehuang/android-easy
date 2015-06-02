package com.easy.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年5月2日
 * @version 1.0.0
 */

public abstract class EasyDbHelper extends SQLiteOpenHelper {

	public EasyDbHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	/**
	 * 代理读取
	 */
	public <T> T agentRead(Reader<T> reader) {
		synchronized (this) {
			T result = null;
			SQLiteDatabase db = this.getReadableDatabase();
			db.beginTransaction();
			try {
				result = reader.doRead(db);
				db.setTransactionSuccessful();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				db.close();
			}
			return result;
		}
	}

	/**
	 * 代理写入
	 */
	public boolean agentWrite(Writer writer) {
		synchronized (this) {
			boolean result = false;
			SQLiteDatabase db = this.getWritableDatabase();
			db.beginTransaction();
			try {
				writer.doWrite(db);
				db.setTransactionSuccessful();
				result = true;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				db.endTransaction();
				db.close();
			}
			return result;
		}
	}

	public interface Reader<T> {
		T doRead(SQLiteDatabase db);
	}

	public interface Writer {
		void doWrite(SQLiteDatabase db);
	}
}
