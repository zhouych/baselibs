package com.zyc.baselibs.mybatis;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;

public class SqlProviderSupport {
	
	public static final String PARAM_KEY_ENTITY = "entity";
	
	public static final String PARAM_KEY_PAGINATION = "pagination";
	
	public static final String PARAM_KEY_ID = "id";

	public static final String PARAM_KEY_CLASS = "clazz";
	
	protected static final String PK = "pk";
	
	protected static final String VERSION = "version";

	/**
	 * 判断指定对象的指定字段的值基于Database层面是否有效
	 * @param field 指定字段
	 * @param target 指定字段所属对象的实例
	 * @return true - 值有效; false - 值无效
	 */
	public boolean validValue(Field field, Object target) {
		Object value = ReflectUtils.getValue(field, target);
		if(null == value) {
			return false;
		}
		
		if(value instanceof String) {
			return StringUtils.isNotBlank((String) value);
		} else {
			return true;
		}
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
	
	public String getJdbcType(Field field) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		String jdbcType = column != null ? column.jdbcType() : null;
		if(StringUtils.isBlank(jdbcType)) {
			jdbcType = fieldType2jdbcType.get(field.getType().getName());
		}
		return jdbcType;
	}
	
	public String genParamPlaceholder(Field field) {
		String jdbcType = this.getJdbcType(field);
		if(StringUtils.isBlank(jdbcType)) {
			return "#{" + field.getName() + "}";
		} else {
			return String.format("#{%s,jdbcType=%s}", field.getName(), jdbcType);
		}
	}
}
