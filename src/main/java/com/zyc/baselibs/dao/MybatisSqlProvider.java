package com.zyc.baselibs.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;

public class MybatisSqlProvider {
	
	private static final Logger logger = Logger.getLogger(MybatisSqlProvider.class); 
	
	protected static final String PK = "pk";
	
	protected static final String VERSION = "version";
	
	public String insert(final Object entity) {
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
					throw new RuntimeException("[MybatisSqlProvider.update()] - " + e.getMessage(), e);
				}
				
				if(null != value) {
					columnSql.append(DatabaseUtils.getColumnName(field, true)).append(",");
					valueParamSql.append("#{" + field.getName() + "},");
				}
				return false;
			}
		}, false, new int[] { Modifier.STATIC, Modifier.FINAL });
		
		//如果存在插入列，需要删除因拼接导致的sql末尾的多余逗号；否则，sql是不符合语法的，抛出异常。
		if(columnSql.length() > 0) {
			columnSql.delete(columnSql.length() - 1, columnSql.length());
			valueParamSql.delete(valueParamSql.length() - 1, valueParamSql.length());
		} else {
			throw new RuntimeException("[MybatisSqlProvider.update()] - Cannot find the field to be inserted. (object=" + clazz.getName() + ")");
		}

		String table = DatabaseUtils.getTableName(clazz);
		String sql = String.format("insert into %s(%s) values(%s)", table, columnSql.toString(), valueParamSql.toString());
		logger.debug("[MybatisSqlProvider.insert()] - " + sql);
		return sql;
	}
	
	public String delete(final Object entity) {
		Class<?> clazz = entity.getClass();
		String table = DatabaseUtils.getTableName(clazz);
		final StringBuilder deleteSql = new StringBuilder();
		deleteSql.append("delete from ").append(table).append(" where 1=1 ");
		int before = deleteSql.length();

		//业务逻辑：支持全字段作为条件进行删除，不支持删除全表数据（即不支持不带任何条件的删除）
		//前提要求：应用到该sql的函数以及函数调用方，在执行最终delete的sql时，确保需要作为删除条件对应的实体对象entity的属性是有值的，不需要作为删除条件的实体对象是没有值的。
		ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				Object value = null;
				try {
					value = field.get(entity);
				} catch (Exception e) {
					throw new RuntimeException("[MybatisSqlProvider.delete()] - " + e.getMessage(), e);
				}

				if(null != value) {
					deleteSql.append(" and ").append(DatabaseUtils.getColumnName(field, true)).append("=#{").append(field.getName()).append("}");
				}
				return false;
			}
		}, false, new int[] { Modifier.STATIC, Modifier.FINAL });
		
		if(deleteSql.length() <= before) {
			throw new RuntimeException("[MybatisSqlProvider.delete()] - One-time physical deletion of full table data is not supported. (object=" + clazz.getName() + ")");
		}

		String sql = deleteSql.toString();
		logger.debug("[MybatisSqlProvider.delete()] - " + sql);
		return sql;
	}
	
	public String update(final Object entity) {
		Class<?> clazz = entity.getClass();
		final String table = DatabaseUtils.getTableName(clazz);
		final StringBuilder builder = new StringBuilder(" update ").append(table).append(" set ");
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
					builder.append(columnName).append("=#{").append(field.getName()).append("},");	
				}
				return false;
			}
		}, false, new int[] { Modifier.STATIC, Modifier.FINAL });
		
		//如果存在更新字段，需要删除因拼接导致的sql末尾的多余逗号；否则，sql是不符合语法的，抛出异常。
		if(builder.length() > before) {
			builder.delete(builder.length() - 1, builder.length());
		} else {
			throw new RuntimeException("[MybatisSqlProvider.update()] - Cannot find the field to be updated. (object=" + clazz.getName() + ")");
		}
		
		Field pk = (Field) container.get(PK);
		if(null == pk) {
			throw new RuntimeException("[MybatisSqlProvider.update()] - The primary key of the object could not be found. (object=" + clazz.getName() + ")");
		}
		
		//更新必须条件之一：主键匹配
		builder.append(" where ").append(DatabaseUtils.getColumnName(pk, true)).append("=#{").append(pk.getName()).append("}");
		

		Field version = (Field) container.get(VERSION);
		if(null != version) {
			//更新可选条件：旧数据的版本号小于新数据的版本号（乐观锁，防止用脏读数据作为基础的更新被提交）
			builder.append(" and ").append(DatabaseUtils.getColumnName(version, true)).append("<#{").append(version.getName()).append("}");
		}
		
		String sql = builder.toString();
		logger.debug("[MybatisSqlProvider.update()] - " + sql);
		return sql;
	}
	
	public String select(final Object entity) {
		return null;
	}
}
