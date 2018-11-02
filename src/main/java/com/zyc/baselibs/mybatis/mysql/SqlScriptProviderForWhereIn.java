package com.zyc.baselibs.mybatis.mysql;

import java.lang.reflect.Field;
import java.sql.JDBCType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.dao.SqlScriptProvider;
import com.zyc.baselibs.dao.SqlScriptProviderSupport;

public class SqlScriptProviderForWhereIn extends SqlScriptProviderSupport implements SqlScriptProvider {

	private static final Logger logger = Logger.getLogger(SqlScriptProviderForWhereIn.class);
	
	/*
	private static final String FORMAT_FOREACH = 
	"<if test=\"%s!=null\"> " + 
	"and %s in " + 
	"<foreach collection=\"%s\" item=\"%s\" separator=\",\" open=\"(\" close=\")\"> " + 
	"#{%s%s} " +
	"</foreach>" + 
	"</if>";
	*/

	@SuppressWarnings("unchecked")
	public String generateSql(final Object obj) {
		Map<String, Object> param = (Map<String, Object>) obj;
		Map<String, Object> field2values = (Map<String, Object>) param.get(PKEY_FIELD2VALUES);
		Class<?> clazz = (Class<?>) param.get(PKEY_CLASS);
		String table = this.getTableName(clazz);
		final StringBuilder selectSql = new StringBuilder("select * from " + table + " where 1=1");

		Field field;
		String fieldName;
		Object values;
		for (Entry<String, Object> entry : field2values.entrySet()) {
			fieldName = entry.getKey();
			values = entry.getValue();
			if(values == null) {
				continue;
			}
			
			field = ReflectUtils.getField(fieldName, clazz);
			if(field == null) {
				throw new IllegalArgumentException("The field does not exist in the entity. (field=" + entry.getKey() + "; entity=" + clazz.getName() + ")");
			}

			/*
			JDBCType jdbcType = DatabaseUtils.getJdbcType(field);
			String jdbcTypeValue = jdbcType == null ? "" : (",jdbcType=" + jdbcType.name());
			String paramKey = PARAM_KEY_FIELD2VALUES + "." + fieldName;
			
			if(Collection.class.isAssignableFrom(values.getClass()) || values.getClass().isArray()) {
				selectSql.append(String.format(FORMAT_FOREACH, paramKey, DatabaseUtils.getColumnName(field, true), paramKey, "item", "item", jdbcTypeValue));
			} else {
				selectSql.append(" and ").append(DatabaseUtils.getColumnName(field, true)).append("=#{").append(paramKey).append(jdbcTypeValue).append("}");
			}
			*/
			
			// 注释以上代码原因：
			// ************************** 使用SelectProvider注解时遇到的严重未解决问题备注 **************************
			// Mapper接口的SQL查询函数传入参数存在java.util.Map<String, List<Object>>时，mybatis无法解析foreach的item值。
			//
			// 具体说就是：
			// <foreach collection="xxx" item="item" open="(" separator="," close=")">
			//	 #{item}
			// </foreach>
			//
			// 以上sql组装无法得到基于SelectProvider注解的mybatis的正确解析，通常会报以下错误：
			// Parameter 'item' not found. Available parameters are [...] with root cause.
			//
			// 所以，改用sql与值直接拼接的方式（有sql注入风险）临时解决。
			this.appendSqlWhereCondition(selectSql, fieldName, values, field);
		}

		String sql = selectSql.toString();
		logger.debug(EX_METHOD_GENERATESQL + sql);
		return sql;
	}
	
	@SuppressWarnings("unchecked")
	private void appendSqlWhereCondition(StringBuilder selectSql, String fieldName, Object values, Field field) {
		JDBCType jdbcType = DatabaseUtils.getJdbcType(field);
		String jdbcTypeValue = jdbcType == null ? "" : (",jdbcType=" + jdbcType.name());
		String paramKey = PKEY_FIELD2VALUES + "." + fieldName;
		
		boolean flag1 = Collection.class.isAssignableFrom(values.getClass());
		boolean flag2 = values.getClass().isArray();
		if(flag1 || flag2) {
			Collection<Object> collection = null; 
			if(flag1) {
				collection = (Collection<Object>) values;
			} else if(flag2) {
				collection = Arrays.asList((Object[]) values);
			}
			
			if(CollectionUtils.hasElement(collection)) {
				StringBuilder insql = new StringBuilder();
				for(Object value : collection) {
					if(value != null) {
						if(value instanceof String) {
							insql.append("\'").append(value).append("\',"); //*** 有SQL注入风险 ***
						}
					}
				}
				
				if(insql.length() > 0) {
					selectSql.append(" and ").append(DatabaseUtils.getColumnName(field, true)).append(" in (").append(insql.substring(0, insql.length() - 1)).append(")");
				}
			}
		} else {
			selectSql.append(" and ").append(DatabaseUtils.getColumnName(field, true)).append("=#{").append(paramKey).append(jdbcTypeValue).append("}");
		}
	}
}
