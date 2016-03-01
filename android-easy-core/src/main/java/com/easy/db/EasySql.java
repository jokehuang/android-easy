package com.easy.db;

import java.util.List;

/**
 * @description
 * @author Joke
 * @email 113979462@qq.com
 * @create 2015年5月2日
 * @version 1.0.0
 */

public class EasySql {

	public static class Type {
		private Type() {
		}

		public static final String INTEGER = "INTEGER";
		public static final String REAL = "REAL";
		public static final String TEXT = "TEXT";
		public static final String BLOG = "BLOG";
		public static final String NULL = "NULL";
	}

	public static class Constraint {
		private Constraint() {
		}

		public static final String NOT_NULL = "NOT NULL";
		public static final String UNIQUE = "UNIQUE";
		public static final String PRIMARY_KEY = "PRIMARY KEY";
		public static final String FOREIGN_KEY = "FOREIGN KEY";
		public static final String CHECK = "CHECK";
		public static final String DEFAULT = "DEFAULT";
	}

	public static class Cmd {
		private Cmd() {
		}

		public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS";
		public static final String DROP_TABLE = "DROP TABLE IF EXISTS";
	}

	public static String buildCreateTableSql(String tbName, List<Field> fields) {
		StringBuilder sb = new StringBuilder();
		sb.append(Cmd.CREATE_TABLE + " " + tbName + " (");
		for (int i = 0; i < fields.size(); i++) {
			sb.append(fields.get(i).toString() + ",");
		}
		sb.replace(sb.length() - 1, sb.length(), ");");
		return sb.toString();
	}

	public static String buildDropTableSql(String tbName) {
		return Cmd.DROP_TABLE + " " + tbName + ";";
	}

	public static class Field {
		public String name;
		public String type;
		public String constraint;

		public Field(String name, String type) {
			this.name = name;
			this.type = type;
		}

		public Field(String name, String type, String constraint) {
			this.name = name;
			this.type = type;
			this.constraint = constraint;
		}

		@Override
		public String toString() {
			return name + " " + type + " "
					+ (constraint == null ? "" : constraint);
		}
	}
}
