package com.zyc.baselibs.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.dao.SqlProvider;
import com.zyc.baselibs.dao.SqlProviderSupport;

public class SqlProviderForKeywordSelect extends SqlProviderSupport implements SqlProvider {

	private static final Logger logger = Logger.getLogger(SqlProviderForKeywordSelect.class);

	private static final String EX_PREFIX = "[SqlProviderForKeywordSelect.generateSql(...)] - ";
	
	@SuppressWarnings("unchecked")
	public String generateSql(Object obj) {
		Map<String, Object> param = (Map<String, Object>) obj;
		final Object entity = param.get(PARAM_KEY_ENTITY);
		final String keyword = (String) param.get(PARAM_KEY_KEYWORD);
		Class<?> clazz = entity.getClass();
		String table = DatabaseUtils.getTableName(clazz);
		final StringBuilder selectSql = new StringBuilder("select * from " + table + " where 1=1 ");
		final StringBuilder keywordMatchSql = new StringBuilder();
		
		//业务逻辑：支持全字段作为条件进行查询，没有条件（实体对象中所有字段都没有值）则查询全表数据
		ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				if(validValue(field, entity)) {
					appendSqlWhereConditon(selectSql, field);
				}
				if(StringUtils.isNotBlank(keyword)) {
					appendSqlWhereKeywordMatch(keywordMatchSql, field);
				}
				return false;
			}
		}, false, new int[] { Modifier.STATIC, Modifier.FINAL });
		
		if(keywordMatchSql.length() > 0) {
			selectSql.append("and (1=2").append(keywordMatchSql).append(")");
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug(EX_PREFIX + selectSql.toString());
		}
		
		return selectSql.toString();
	}

	protected void appendSqlWhereConditon(final StringBuilder targetSql, Field field) {
		targetSql.append(" and ").append(DatabaseUtils.getColumnName(field, true)).append("=").append(genMybatisParamPlaceholder(field));
	}

	protected void appendSqlWhereKeywordMatch(final StringBuilder keywordMatchSql, Field field) {
		if(this.supportKeyword(field)) {
			keywordMatchSql.append(" or ").append(DatabaseUtils.getColumnName(field, true)).append(" like '%' || #{").append(PARAM_KEY_KEYWORD).append("} || '%'");
		}
	}
	
	protected boolean supportKeyword(Field field) {
		String fieldType = field.getType().getName();
		return String.class.getName().equals(fieldType);
	}

}
