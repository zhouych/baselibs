package com.zyc.baselibs.mybatis;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.dao.SqlProvider;
import com.zyc.baselibs.dao.SqlProviderSupport;

public class SqlProviderForUpdate extends SqlProviderSupport implements SqlProvider {
	
	private static final Logger logger = Logger.getLogger(SqlProviderForUpdate.class); 

	private static final String EX_PREFIX = "[UpdateSqlBuilder.generateSql(...)] - "; 

	public String generateSql(final Object entity) {
		Class<?> clazz = entity.getClass();
		final String table = DatabaseUtils.getTableName(clazz);
		final StringBuilder builder = new StringBuilder("update ").append(table).append(" set ");
		int before = builder.length();
		final Map<String, Object> container = new HashMap<String, Object>();
		
		//业务逻辑：为了达到该更新sql的通用性，这里使用全字段更新。
		//前提要求：应用到该sql的函数以及函数调用方，在执行最终update的sql之前，传递的实体对象必须保持完整性（即建议先读取，再更新读取到的实体，再将该更新的实体传入）。
		ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				if(DatabaseUtils.isPrimaryKey(field)) {
					container.put(PK, field);
				} else {
					if(DatabaseUtils.isVersion(field)) {
						container.put(VERSION, field);
					}
					String columnName = DatabaseUtils.getColumnName(field, true);
					builder.append(columnName).append("=").append(genMybatisParamPlaceholder(field)).append(",");
				}
				return false;
			}
		}, false, new int[] { Modifier.STATIC, Modifier.FINAL });
		
		//如果存在更新字段，需要删除因拼接导致的sql末尾的多余逗号；否则，sql是不符合语法的，抛出异常。
		if(builder.length() > before) {
			builder.delete(builder.length() - 1, builder.length());
		} else {
			throw new RuntimeException(EX_PREFIX + "Cannot find the field to be updated. (object=" + clazz.getName() + ")");
		}
		
		Field pk = (Field) container.get(PK);
		if(null == pk) {
			throw new RuntimeException(EX_PREFIX + "The primary key of the object could not be found. (object=" + clazz.getName() + ")");
		}
		
		//更新必须条件之一：主键匹配
		builder.append(" where ").append(DatabaseUtils.getColumnName(pk, true)).append("=#{").append(pk.getName()).append("}");

		Field version = (Field) container.get(VERSION);
		if(null != version) {
			//更新可选条件：旧数据的版本号小于新数据的版本号（乐观锁，防止用脏读数据作为基础的更新被提交）
			builder.append(" and ").append(DatabaseUtils.getColumnName(version, true)).append("<#{").append(version.getName()).append("}");
		}
		
		String sql = builder.toString();
		logger.debug(EX_PREFIX + sql);
		return sql;
	}
}
