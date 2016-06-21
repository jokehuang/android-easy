package com.easy.helper;

import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @Description
 * @author Joke Huang
 * @createDate 2014年12月8日
 * @version 1.0.0
 */

public class SPFile {
	private SharedPreferences sp;

	public SPFile(Context context, String fileName) {
		sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
	}

	public String getString(String key, String defValue) {
		return sp.getString(key, defValue);
	}

	public void put(String key, String value) {
		Editor ed = sp.edit();
		ed.putString(key, value);
		ed.commit();
	}

	public boolean getBoolean(String key, boolean defValue) {
		return sp.getBoolean(key, defValue);
	}

	public void put(String key, boolean value) {
		Editor ed = sp.edit();
		ed.putBoolean(key, value);
		ed.commit();
	}

	public int getInt(String key, int defValue) {
		return sp.getInt(key, defValue);
	}

	public void put(String key, int value) {
		Editor ed = sp.edit();
		ed.putInt(key, value);
		ed.commit();
	}

	public long getLong(String key, long defValue) {
		return sp.getLong(key, defValue);
	}

	public void put(String key, long value) {
		Editor ed = sp.edit();
		ed.putLong(key, value);
		ed.commit();
	}

	public void remove(String key) {
		Editor ed = sp.edit();
		ed.remove(key);
		ed.commit();
	}

	public void clear() {
		Editor ed = sp.edit();
		ed.clear();
		ed.commit();
	}

	/** android api 11 */
	@SuppressLint("NewApi")
	public Set<String> getStringSet(String key) {
		return sp.getStringSet(key, null);
	}

	@SuppressLint("NewApi")
	public void putStringSet(String key, Set<String> values) {
		Editor ed = sp.edit();
		ed.remove(key);
		ed.commit();
		ed.putStringSet(key, values);
		ed.commit();
	}

	public void addString2Set(String key, String value) {
		Set<String> values = getStringSet(key);
		if (values == null) {
			values = new HashSet<String>();
		}
		values.add(value);
		putStringSet(key, values);
	}
}
