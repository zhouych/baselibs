package com.zyc.baselibs.mybatis.mysql;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.dao.SqlScriptProvider;
import com.zyc.baselibs.dao.SqlScriptProviderSupport;

public class SqlScriptProviderForKeywordSelect extends SqlScriptProviderSupport implements SqlScriptProvider {

	private static final Logger logger = Logger.getLogger(SqlScriptProviderForKeywordSelect.class);

	private static final String EX_PREFIX = "[SqlScriptProviderForKeywordSelect.generateSql(...)] - ";

	public static final String PARAM_KEY_ONLY_TOTALCOUNT = "onlyTotalCount";
	
	protected boolean isOnlyTotalCount(Map<String, Object> param) {
		return !param.containsKey(PARAM_KEY_ONLY_TOTALCOUNT) || param.get(PARAM_KEY_ONLY_TOTALCOUNT) == null 
				? false : Boolean.parseBoolean(param.get(PARAM_KEY_ONLY_TOTALCOUNT).toString());
	}
	
	@SuppressWarnings("unchecked")
	public String generateSql(Object obj) {
		Map<String, Object> param = (Map<String, Object>) obj;
		final Object entity = this.convertToDatabaseEntity(param.get(PARAM_KEY_ENTITY));
		final String keyword = (String) param.get(PARAM_KEY_KEYWORD);
		boolean onlyTotalCount = this.isOnlyTotalCount(param);
		Class<?> clazz = entity.getClass();
		String table = DatabaseUtils.getTableName(clazz);
		final StringBuilder selectSql = new StringBuilder("select " + (onlyTotalCount ? "count(1)" : "*") + " from " + table + " where 1=1 ");
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
			keywordMatchSql.append(" or ").append(DatabaseUtils.getColumnName(field, true)).append(" like concat('%',#{").append(PARAM_KEY_KEYWORD).append("},'%')");
		}
	}
	
	protected boolean supportKeyword(Field field) {
		DatabaseColumn dbcolumn = DatabaseUtils.getColumn(field);
		//判断当前字段是否属于非特殊字段（主键、被注解标记为枚举的字段都属于java.lang.String类型，这些属于特殊字段）
		boolean nonSpecial = (dbcolumn == null || (!dbcolumn.pk())) && !DatabaseUtils.isEnumMapping(field);
		String fieldType = field.getType().getName();
		//非特殊字段且属于java.lang.String类型都支持支持关键字匹配检索
		return nonSpecial && String.class.getName().equals(fieldType);
	}

}
