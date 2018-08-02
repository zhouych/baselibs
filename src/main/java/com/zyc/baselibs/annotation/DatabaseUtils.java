package com.zyc.baselibs.annotation;

import java.lang.reflect.Field;

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
}
