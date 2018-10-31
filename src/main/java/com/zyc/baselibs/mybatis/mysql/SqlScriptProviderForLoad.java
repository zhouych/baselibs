package com.zyc.baselibs.mybatis.mysql;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.JDBCType;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.dao.SqlScriptProvider;
import com.zyc.baselibs.dao.SqlScriptProviderSupport;

public class SqlScriptProviderForLoad extends SqlScriptProviderSupport implements SqlScriptProvider {
	
	private static final Logger logger = Logger.getLogger(SqlScriptProviderForLoad.class); 

	private static final String EX_PREFIX = "[SqlScriptProviderForLoad.generateSql(...)] - ";
	
	@SuppressWarnings("unchecked")
	public String generateSql(Object obj) {
		Map<String, Object> param = (Map<String, Object>) obj;
		String id = (String) param.get(PARAM_KEY_ID);
		Class<?> clazz = (Class<?>) param.get(PARAM_KEY_CLASS);
		if(StringUtils.isBlank(id)) {
			throw new RuntimeException(EX_PREFIX + "Unable to load data with a null primary key. (object=" + clazz.getName() + ")");
		}
		
		final StringBuilder selectSql = new StringBuilder();
		selectSql.append("select * from ").append(DatabaseUtils.getTableName(clazz)).append(" where 1=1 ");
		
		ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				if(DatabaseUtils.isPrimaryKey(field)) {
					JDBCType jdbcType = DatabaseUtils.getJdbcType(field);
					String jdbcTypeValue = jdbcType == null ? "" : (",jdbcType=" + jdbcType.name()); 
					selectSql.append(" and ").append(DatabaseUtils.getColumnName(field, true)).append("=#{").append(PARAM_KEY_ID).append(jdbcTypeValue).append("}");
					return true;
				}
				return false;
			}
		}, true, new int[] { Modifier.STATIC, Modifier.FINAL });
		
		String sql = selectSql.toString();
		logger.debug(EX_PREFIX + sql);
		return sql;
	}

}
