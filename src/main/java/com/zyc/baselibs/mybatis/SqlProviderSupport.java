package com.zyc.baselibs.mybatis;

import java.lang.reflect.Field;

import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;

public class SqlProviderSupport {
	
	public static final String PARAM_KEY_ENTITY = "entity";
	
	public static final String PARAM_KEY_PAGINATION = "pagination";
	
	public static final String PARAM_KEY_ID = "id";

	public static final String PARAM_KEY_CLASS = "clazz";
	
	protected static final String PK = "pk";
	
	protected static final String VERSION = "version";

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
}
