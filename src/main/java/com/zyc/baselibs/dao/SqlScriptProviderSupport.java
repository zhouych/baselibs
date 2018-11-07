package com.zyc.baselibs.dao;

import java.lang.reflect.Field;
import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.entities.EntityCopyable;

public class SqlScriptProviderSupport {
	
	protected static final String EX_METHOD_GENERATESQL = "[generateSql(...)] - ";
	
	public static final String PKEY_ENTITY = "entity";
	
	public static final String PKEY_KEYWORD = "keyword";
	
	public static final String PKEY_PAGINATION = "pagination";
	
	public static final String PKEY_ID = "id";

	public static final String PKEY_VERSION = "version";

	public static final String PKEY_CLASS = "clazz";
	
	public static final String PKEY_FIELD2VALUES = "field2values";
	
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

	private static final String EX_ENTITY_MAPPED_TABLE = "This entity cannot be mapped to a database table. (entity=%s)";
	
	public Object convertToDatabaseEntity(Object entity) {
		if(entity.getClass().isAnnotationPresent(DatabaseTable.class)) {
			return entity;
		} else if(entity instanceof EntityCopyable) {
			return ((EntityCopyable<?>) entity).copyEntity();
		}
		throw new RuntimeException(String.format(EX_ENTITY_MAPPED_TABLE, entity.getClass().getName())); 
	}
	
	public String getTableName(Class<?> clazz) {
		String table = DatabaseUtils.getTableName(clazz);
		if(StringUtils.isBlank(table)) {
			throw new RuntimeException(String.format(EX_ENTITY_MAPPED_TABLE, clazz.getName())); 
		}
		return table;
	}
	
	public static String genMybatisParamPlaceholder(Field field, String paramVarName) {
		paramVarName = StringUtils.isBlank(paramVarName) ? "" : (paramVarName + ".");
		JDBCType jdbcType = DatabaseUtils.getJdbcType(field);
		if(jdbcType == null) {
			return "#{" + paramVarName + field.getName() + "}";
		} else {
			return String.format("#{%s%s,jdbcType=%s}", paramVarName, field.getName(), jdbcType.name());
		}
	}
}
