package com.zyc.baselibs.mybatis.mysql;

import java.util.Map;

import org.apache.log4j.Logger;

import com.zyc.baselibs.vo.Pagination;

public class SqlScriptProviderForSelectByPage extends SqlScriptProviderForSelect {

	private static final Logger logger = Logger.getLogger(SqlScriptProviderForSelectByPage.class);

	@SuppressWarnings("unchecked")
	public String generateSql(Object obj) {
		Map<String, Object> param = (Map<String, Object>) obj;
		Object entity = param.get(PKEY_ENTITY);
		Pagination pagination = (Pagination) param.get(PKEY_PAGINATION);
		
		StringBuilder selectSql = new StringBuilder(super.generateSql(entity));
		selectSql.append(SqlScriptPaginationUtils.generateOrderbySql(pagination, entity)); //根据传入的排序字段进行排序
		SqlScriptPaginationUtils.appendLimitSql(selectSql, pagination); //根据分页参数进行分页
		
		String sql = selectSql.toString();
		logger.debug(EX_METHOD_GENERATESQL + sql);
		return sql;
	}
}
