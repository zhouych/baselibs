package com.zyc.baselibs.mybatis.mysql;

import java.util.Map;

import org.apache.log4j.Logger;

public class SqlScriptProviderForKeywordSelectTotalCount extends SqlScriptProviderForKeywordSelect {

	private static final Logger logger = Logger.getLogger(SqlScriptProviderForKeywordSelectTotalCount.class);

	@SuppressWarnings("unchecked")
	public String generateSql(Object obj) {
		Map<String, Object> param = (Map<String, Object>) obj;
		param.put(PARAM_KEY_ONLY_TOTALCOUNT, true);
		String sql = super.generateSql(obj).toString();
		logger.debug(EX_METHOD_GENERATESQL + sql);
		return sql;
	}

}
