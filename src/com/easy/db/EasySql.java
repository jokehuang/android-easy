package com.easy.db;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年5月2日
 * @version 1.0.0
 */

public class EasySql {
	public static final String TYPE_INTERGER = "INTERGER";
	public static final String TYPE_REAL = "REAL";
	public static final String TYPE_TEXT = "TEXT";
	public static final String TYPE_BLOG = "BLOG";
	public static final String TYPE_NULL = "NULL";

	public static final String CONSTRAINT_NOT_NULL = "NOT NULL";
	public static final String CONSTRAINT_UNIQUE = "UNIQUE";
	public static final String CONSTRAINT_PRIMARY_KEY = "PRIMARY KEY";
	public static final String CONSTRAINT_FOREIGN_KEY = "FOREIGN KEY";
	public static final String CONSTRAINT_CHECK = "CHECK";
	public static final String CONSTRAINT_DEFAULT = "DEFAULT";

	public static final String CMD_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS";
	public static final String CMD_DROP_TABLE = "DROP TABLE IF EXISTS";

	public static String buildCreateTableSql(String tbName,
			String[] fieldNames, String[] fieldTypes, String[] fieldConstraints) {
		StringBuilder sb = new StringBuilder();
		sb.append(CMD_CREATE_TABLE + " " + tbName + " (");
		for (int i = 0; i < fieldNames.length; i++) {
			String name = fieldNames[i];
			String type = fieldTypes[i];
			String constraint = "";
			if (fieldConstraints != null && i < fieldConstraints.length) {
				constraint = fieldConstraints[i];
			}
			sb.append(name + " " + type + " " + constraint + ",");
		}
		sb.replace(sb.length() - 1, sb.length(), ");");
		return sb.toString();
	}

	public static String buildDropTableSql(String tbName) {
		return CMD_DROP_TABLE + " " + tbName + ";";
	}
}
