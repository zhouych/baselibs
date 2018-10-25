package com.zyc.baselibs.dao;

import java.util.Map;

import com.zyc.baselibs.mybatis.SqlProviderFactory;

public final class SqlScriptCommander {
	
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
	
	public String selectByPageSupportKeyword(Map<String, Object> param) {
		return SqlProviderFactory.getInstance(SqlAction.KEYWORDSELECTBYPAGE, null).generateSql(param);
	}
	
	public String select(final Object entity) {
		return SqlProviderFactory.getInstance(SqlAction.SELECT, null).generateSql(entity);
	}
	
	public String selectSupportKeyword(Map<String, Object> param) {
		return SqlProviderFactory.getInstance(SqlAction.KEYWORDSELECT, null).generateSql(param);
	}
	
	public String load(Map<String, Object> param) {
		return SqlProviderFactory.getInstance(SqlAction.LOAD, null).generateSql(param);
	}
}
