package com.easy.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Joke on 2015/10/10.
 */
public class ReflectUtil {

	/**
	 * 填充对象中值为null的成员变量，基础型的封装类统一为0，字符串为""。
	 * @param obj
	 */
	public static void fillNullField(Object obj) {
		for (Field field : obj.getClass().getDeclaredFields()) {
			Class<?> fieldType = field.getType();
			if (fieldType == String.class) {
				field.setAccessible(true);
				try {
					field.set(obj, "");
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else if (fieldType == Integer.class || fieldType == Long.class || fieldType == Float
					.class || fieldType == Double.class) {
				field.setAccessible(true);
				try {
					Object value = fieldType.getConstructor(String.class).newInstance("0");
					field.set(obj, value);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

