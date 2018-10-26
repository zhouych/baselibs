package com.zyc.baselibs.mybatis.mysql;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.dao.SqlScriptProvider;
import com.zyc.baselibs.dao.SqlScriptProviderSupport;

public class SqlScriptProviderForInsert extends SqlScriptProviderSupport implements SqlScriptProvider {
	
	private static final Logger logger = Logger.getLogger(SqlScriptProviderForInsert.class);

	private static final String EX_PREFIX = "[SqlScriptProviderForInsert.generateSql(...)] - ";

	public String generateSql(final Object entity) {
		Class<?> clazz = entity.getClass();
		final StringBuilder columnSql = new StringBuilder();
		final StringBuilder valueParamSql = new StringBuilder();

		//业务逻辑：为空值的实体字段将被忽略，不插入值，采用数据库默认值
		ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				Object value = null;
				try {
					value = field.get(entity);
				} catch (Exception e) {
					throw new RuntimeException(EX_PREFIX + e.getMessage(), e);
				}
				
				if(null != value) {
					columnSql.append(DatabaseUtils.getColumnName(field, true)).append(",");
					valueParamSql.append(genMybatisParamPlaceholder(field)).append(",");
				}
				return false;
			}
		}, false, new int[] { Modifier.STATIC, Modifier.FINAL });
		
		//如果存在插入列，需要删除因拼接导致的sql末尾的多余逗号；否则，sql是不符合语法的，抛出异常。
		if(columnSql.length() > 0) {
			columnSql.delete(columnSql.length() - 1, columnSql.length());
			valueParamSql.delete(valueParamSql.length() - 1, valueParamSql.length());
		} else {
			throw new RuntimeException(EX_PREFIX + "Cannot find the field to be inserted. (object=" + clazz.getName() + ")");
		}

		String table = DatabaseUtils.getTableName(clazz);
		String sql = String.format("insert into %s(%s) values(%s)", table, columnSql.toString(), valueParamSql.toString());
		logger.debug(EX_PREFIX + sql);
		return sql;
	}
}
