package com.zyc.baselibs.mybatis;

import java.util.Map;

import org.apache.log4j.Logger;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.vo.Pagination;

public class SqlProviderForKeywordSelectByPage extends SqlProviderForKeywordSelect {

	private static final Logger logger = Logger.getLogger(SqlProviderForKeywordSelectByPage.class);

	private static final String EX_PREFIX_PAGINATION = "[SqlProviderForKeywordSelectByPage.generateSql(...)] - ";

	@SuppressWarnings("unchecked")
	public String generateSql(Object obj) {
		Map<String, Object> param = (Map<String, Object>) obj;
		Object entity = param.get(PARAM_KEY_ENTITY);
		Pagination pagination = (Pagination) param.get(PARAM_KEY_PAGINATION);
		
		StringBuilder selectSql = new StringBuilder(super.generateSql(obj));
		
		//根据传入的排序字段进行排序，如果排序字段没有值则认为是业务上不需要可以排序，采用数据库层面的默认排序即可。
		if(StringUtils.isNotBlank(pagination.getOrder())) {
			String column = DatabaseUtils.getColumnName(ReflectUtils.getField(pagination.getOrder(), entity.getClass()), true);
			selectSql.append(" order by ").append(column).append(" ").append(pagination.isAsc() ? "asc" : "desc");
		}
		
		//根据分页参数进行分页
		selectSql.append(" limit ").append(pagination.getStartIndex() - 1).append(",").append(pagination.getPageRowCount());

		String sql = selectSql.toString();
		logger.debug(EX_PREFIX_PAGINATION + sql);
		return sql;
	}
}
