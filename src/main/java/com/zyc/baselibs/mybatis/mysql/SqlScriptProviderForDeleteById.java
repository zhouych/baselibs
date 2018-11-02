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

public class SqlScriptProviderForDeleteById extends SqlScriptProviderSupport implements SqlScriptProvider {
	
	private static final Logger logger = Logger.getLogger(SqlScriptProviderForDeleteById.class); 
	
	@SuppressWarnings("unchecked")
	public String generateSql(final Object obj) {
		Map<String, Object> param = (Map<String, Object>) obj;
		String id = (String) param.get(PKEY_ID);
		int version = Integer.parseInt(param.get(PKEY_VERSION).toString());
		Class<?> clazz = (Class<?>) param.get(PKEY_CLASS);
		if(StringUtils.isBlank(id) || version < 0) {
			throw new RuntimeException(EX_METHOD_GENERATESQL + "Unable to delete empty primary key or invalid version of data. (object=" + clazz.getName() + ")");
		}
		
		final StringBuilder deleteSql = new StringBuilder("delete from " + this.getTableName(clazz) + " where 1=1");
		final int[] flags = new int[] { 0 };
		int before = deleteSql.length();
		//业务逻辑：支持全字段作为条件进行删除，不支持删除全表数据（即不支持不带任何条件的删除）
		ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				String col = null;
				if(DatabaseUtils.isPrimaryKey(field)) {
					col = PKEY_ID;
				} else if(DatabaseUtils.isVersion(field)) {
					col = PKEY_VERSION;
				}
				
				if(StringUtils.isNotBlank(col)) {
					JDBCType jdbcType = DatabaseUtils.getJdbcType(field);
					String jdbcTypeValue = jdbcType == null ? "" : (",jdbcType=" + jdbcType.name()); 
					deleteSql.append(" and ").append(DatabaseUtils.getColumnName(field, true)).append("=#{").append(col).append(jdbcTypeValue).append("}");
					return ++flags[0] == 2; //只需取pk主键与version版本号这2列，所以取完了，就终止实体字段扫描。
				}
				return false;
			}
		}, true, new int[] { Modifier.STATIC, Modifier.FINAL });

		if(deleteSql.length() <= before) {
			throw new RuntimeException(EX_METHOD_GENERATESQL + "One-time physical deletion of full table data is not supported. (object=" + clazz.getName() + ")");
		}

		String sql = deleteSql.toString();
		logger.debug(EX_METHOD_GENERATESQL + sql);
		return sql;
	}

}
