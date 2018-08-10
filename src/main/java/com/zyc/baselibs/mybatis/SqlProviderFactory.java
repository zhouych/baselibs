package com.zyc.baselibs.mybatis;

import java.util.Map;

import com.zyc.baselibs.commons.ReflectUtils;

public final class SqlProviderFactory {
	
	private static final String FMT_PACKAGE_NAME = "com.zyc.baselibs.mybatis." + SqlProvider.class.getSimpleName() + "For%s";
	
	public static SqlProvider getInstance(SqlAction sqlAction, Object[] args) {
		return ReflectUtils.clazzInstance(String.format(FMT_PACKAGE_NAME, sqlAction.getName()), args);
	}
	
	public String insert(final Object entity) {
		return getInstance(SqlAction.INSERT, null).generateSql(entity);
	}
	
	public String delete(final Object entity) {
		return getInstance(SqlAction.DELETE, null).generateSql(entity);
	}
	
	public String update(final Object entity) {
		return getInstance(SqlAction.UPDATE, null).generateSql(entity);
	}
	
	public String selectByPage(Map<String, Object> param) {
		return getInstance(SqlAction.SELECTBYPAGE, null).generateSql(param);
	}
	
	public String select(final Object entity) {
		return getInstance(SqlAction.SELECT, null).generateSql(entity);
	}
	
	public String load(Map<String, Object> param) {
		return getInstance(SqlAction.LOAD, null).generateSql(param);
	}
}
