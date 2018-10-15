package com.zyc.baselibs.annotation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.zyc.baselibs.commons.StringUtils;

public class DatabaseUtils {
	public static String getTableName(Class<?> clazz) {
		return clazz.isAnnotationPresent(DatabaseTable.class) ? clazz.getAnnotation(DatabaseTable.class).name() : null;
	}
	
	public static DatabaseColumn getColumn(Field field) {
		return field.isAnnotationPresent(DatabaseColumn.class) ? field.getAnnotation(DatabaseColumn.class) : null;
	}
	
	public static boolean isPrimaryKey(Field field) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		return null != column && column.pk();
	}
	
	public static boolean isVersion(Field field) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		return null != column && column.version();
	}
	
	public static String getColumnName(Field field) {
		return DatabaseUtils.getColumnName(field, false);
	}
	
	/**
	 * 获取对应数据库的列名
	 * @param field 实体对象的字段
	 * @param defaultFieldName 是否启用默认取字段名。<tt></br>当<code>field</code>不是{@link DatabaseColumn}注解时，是否取字段名称</tt>。
	 * @return
	 */
	public static String getColumnName(Field field, boolean defaultFieldName) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		if(column == null) {
			return defaultFieldName ? field.getName() : null;
		}
		
		return StringUtils.isBlank(column.name()) ? field.getName() : column.name();
	}
	
	private static final Map<String, String> fieldType2jdbcType = new HashMap<String, String>();
	
	static {
		fieldType2jdbcType.put("boolean", "BOOLEAN");
		fieldType2jdbcType.put("java.lang.Boolean", "BOOLEAN");
		fieldType2jdbcType.put("char", "CHAR");
		fieldType2jdbcType.put("java.lang.Character", "CHAR");
		fieldType2jdbcType.put("int", "INTEGER");
		fieldType2jdbcType.put("java.lang.Integer", "INTEGER");
		fieldType2jdbcType.put("long", "NUMERIC");
		fieldType2jdbcType.put("java.lang.Long", "NUMERIC");
		fieldType2jdbcType.put("float", "FLOAT");
		fieldType2jdbcType.put("java.lang.Float", "FLOAT");
		fieldType2jdbcType.put("double", "DOUBLE");
		fieldType2jdbcType.put("java.lang.Double", "DOUBLE");
		fieldType2jdbcType.put("java.lang.String", "VARCHAR");
		fieldType2jdbcType.put("java.util.Date", "TIMESTAMP");
	}
	
	public static String getJdbcType(Field field) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		String jdbcType = column != null ? column.jdbcType() : null;
		if(StringUtils.isBlank(jdbcType)) {
			jdbcType = fieldType2jdbcType.get(field.getType().getName());
		}
		return jdbcType;
	}
	
	public static boolean getNullable(Field field) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		return column == null || column.nullable();
	}

	private static final Map<String, String> jdbcType2dbType = new HashMap<String, String>();

	static {
		jdbcType2dbType.put("BOOLEAN", "TINYINT(1)");
		jdbcType2dbType.put("CHAR", "CHAR(1)");
		jdbcType2dbType.put("INT", "INT");
		jdbcType2dbType.put("NUMERIC", "LONGBLOB");
		jdbcType2dbType.put("FLOAT", "FLOAT");
		jdbcType2dbType.put("DOUBLE", "DOUBLE");
		jdbcType2dbType.put("VARCHAR", "VARCHAR(128)");
		jdbcType2dbType.put("TIMESTAMP", "TIMESTAMP");
	}

	public static String getDbType(Field field) {
		return jdbcType2dbType.get(getJdbcType(field));
	}
}
