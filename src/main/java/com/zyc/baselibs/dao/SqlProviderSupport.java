package com.zyc.baselibs.dao;

import java.lang.reflect.Field;

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
	
	public static String genParamPlaceholder(Field field) {
		String jdbcType = DatabaseUtils.getJdbcType(field);
		if(StringUtils.isBlank(jdbcType)) {
			return "#{" + field.getName() + "}";
		} else {
			return String.format("#{%s,jdbcType=%s}", field.getName(), jdbcType);
		}
	}
}
