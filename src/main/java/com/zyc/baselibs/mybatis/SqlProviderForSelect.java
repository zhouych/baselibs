package com.zyc.baselibs.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;

public class SqlProviderForSelect extends SqlProviderSupport implements SqlProvider {

	private static final Logger logger = Logger.getLogger(SqlProviderForInsert.class);

	private static final String EX_PREFIX = "[SelectSqlBuilder.generateSql(...)] - ";
	
	public String generateSql(final Object entity) {
		Class<?> clazz = entity.getClass();
		String table = DatabaseUtils.getTableName(clazz);
		final StringBuilder selectSql = new StringBuilder();
		selectSql.append("select * from ").append(table).append(" where 1=1 ");

		//业务逻辑：支持全字段作为条件进行查询，没有条件（实体对象中所有字段都没有值）则查询全表数据
		ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				if(validValue(field, entity)) {
					selectSql.append(" and ").append(DatabaseUtils.getColumnName(field, true)).append("=").append(genParamPlaceholder(field));
				}
				return false;
			}
		}, false, new int[] { Modifier.STATIC, Modifier.FINAL });
		
		String sql = selectSql.toString();
		logger.debug(EX_PREFIX + sql);
		return sql;
	}

}
