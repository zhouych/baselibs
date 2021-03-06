package com.zyc.baselibs.mybatis.mysql;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.dao.SqlScriptProvider;
import com.zyc.baselibs.dao.SqlScriptProviderSupport;

public class SqlScriptProviderForDelete extends SqlScriptProviderSupport implements SqlScriptProvider {
	
	private static final Logger logger = Logger.getLogger(SqlScriptProviderForDelete.class);
	
	public String generateSql(final Object entity) {
		Class<?> clazz = entity.getClass();
		final StringBuilder deleteSql = new StringBuilder("delete from " + this.getTableName(clazz) + " where 1=1");
		int before = deleteSql.length();
		//业务逻辑：支持全字段作为条件进行删除，不支持删除全表数据（即不支持不带任何条件的删除）
		//前提要求：应用到该sql的函数以及函数调用方，在执行最终delete的sql时，确保需要作为删除条件对应的实体对象entity的属性是有值的，不需要作为删除条件的实体对象是没有值的。
		ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				if(validValue(field, entity)) {
					deleteSql.append(" and ").append(DatabaseUtils.getColumnName(field, true)).append("=").append(genMybatisParamPlaceholder(field, null));
				}
				return false;
			}
		}, false, new int[] { Modifier.STATIC, Modifier.FINAL });
		
		if(deleteSql.length() <= before) {
			throw new RuntimeException(EX_METHOD_GENERATESQL + "One-time physical deletion of full table data is not supported. (object=" + clazz.getName() + ")");
		}

		String sql = deleteSql.toString();
		logger.debug(EX_METHOD_GENERATESQL + sql);
		return sql;
	}

}
