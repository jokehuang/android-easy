package com.easy.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Joke Huang
 * @createDate 2014年11月26日
 * @version 1.0.0
 */

public class EmptyUtil {

	private EmptyUtil() {
	}

	// String
	public static boolean notEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.equals("");
	}

	// byte[]
	public static boolean notEmpty(byte[] array) {
		return !isEmpty(array);
	}

	public static boolean isEmpty(byte[] array) {
		return array == null || array.length == 0;
	}

	// char[]
	public static boolean notEmpty(char[] array) {
		return !isEmpty(array);
	}

	public static boolean isEmpty(char[] array) {
		return array == null || array.length == 0;
	}

	// short[]
	public static boolean notEmpty(short[] array) {
		return !isEmpty(array);
	}

	public static boolean isEmpty(short[] array) {
		return array == null || array.length == 0;
	}

	// int[]
	public static boolean notEmpty(int[] array) {
		return !isEmpty(array);
	}

	public static boolean isEmpty(int[] array) {
		return array == null || array.length == 0;
	}

	// long[]
	public static boolean notEmpty(long[] array) {
		return !isEmpty(array);
	}

	public static boolean isEmpty(long[] array) {
		return array == null || array.length == 0;
	}

	// float[]
	public static boolean notEmpty(float[] array) {
		return !isEmpty(array);
	}

	public static boolean isEmpty(float[] array) {
		return array == null || array.length == 0;
	}

	// double[]
	public static boolean notEmpty(double[] array) {
		return !isEmpty(array);
	}

	public static boolean isEmpty(double[] array) {
		return array == null || array.length == 0;
	}

	// boolean[]
	public static boolean notEmpty(boolean[] array) {
		return !isEmpty(array);
	}

	public static boolean isEmpty(boolean[] array) {
		return array == null || array.length == 0;
	}

	// Object[]
	public static boolean notEmpty(Object[] array) {
		return !isEmpty(array);
	}

	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	// List
	public static boolean notEmpty(List<?> list) {
		return !isEmpty(list);
	}

	public static boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	// Collection
	public static boolean notEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	// Map
	public static boolean notEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
}
