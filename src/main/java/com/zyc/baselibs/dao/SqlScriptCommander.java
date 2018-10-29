package com.zyc.baselibs.dao;

import java.util.Map;

import com.zyc.baselibs.mybatis.SqlScriptProviderFactory;

public final class SqlScriptCommander {
	
	private static final DatabaseType databaseType = DatabaseType.MYSQL;
	
	public String insert(final Object entity) {
		return SqlScriptProviderFactory.getInstance(databaseType, SqlAction.INSERT, null).generateSql(entity);
	}
	
	public String delete(final Object entity) {
		return SqlScriptProviderFactory.getInstance(databaseType, SqlAction.DELETE, null).generateSql(entity);
	}
	
	public String update(final Object entity) {
		return SqlScriptProviderFactory.getInstance(databaseType, SqlAction.UPDATE, null).generateSql(entity);
	}
	
	public String selectByPage(Map<String, Object> param) {
		return SqlScriptProviderFactory.getInstance(databaseType, SqlAction.SELECTBYPAGE, null).generateSql(param);
	}
	
	public String selectByPageSupportKeyword(Map<String, Object> param) {
		return SqlScriptProviderFactory.getInstance(databaseType, SqlAction.KEYWORDSELECTBYPAGE, null).generateSql(param);
	}
	
	public String selectTotalCountSupportKeyword(Map<String, Object> param) {
		return SqlScriptProviderFactory.getInstance(databaseType, SqlAction.KEYWORDSELECTTOTALCOUNT, null).generateSql(param);
	}
	
	
	public String select(final Object entity) {
		return SqlScriptProviderFactory.getInstance(databaseType, SqlAction.SELECT, null).generateSql(entity);
	}
	
	public String selectSupportKeyword(Map<String, Object> param) {
		return SqlScriptProviderFactory.getInstance(databaseType, SqlAction.KEYWORDSELECT, null).generateSql(param);
	}
	
	public String load(Map<String, Object> param) {
		return SqlScriptProviderFactory.getInstance(databaseType, SqlAction.LOAD, null).generateSql(param);
	}
}
