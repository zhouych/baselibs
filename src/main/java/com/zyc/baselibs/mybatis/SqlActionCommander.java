package com.zyc.baselibs.mybatis;

import java.util.Map;

public final class SqlActionCommander {
	
	public String insert(final Object entity) {
		return SqlProviderFactory.getInstance(SqlAction.INSERT, null).generateSql(entity);
	}
	
	public String delete(final Object entity) {
		return SqlProviderFactory.getInstance(SqlAction.DELETE, null).generateSql(entity);
	}
	
	public String update(final Object entity) {
		return SqlProviderFactory.getInstance(SqlAction.UPDATE, null).generateSql(entity);
	}
	
	public String selectByPage(Map<String, Object> param) {
		return SqlProviderFactory.getInstance(SqlAction.SELECTBYPAGE, null).generateSql(param);
	}
	
	public String select(final Object entity) {
		return SqlProviderFactory.getInstance(SqlAction.SELECT, null).generateSql(entity);
	}
	
	public String load(Map<String, Object> param) {
		return SqlProviderFactory.getInstance(SqlAction.LOAD, null).generateSql(param);
	}
}
