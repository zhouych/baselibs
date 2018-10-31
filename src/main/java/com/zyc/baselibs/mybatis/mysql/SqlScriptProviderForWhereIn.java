package com.zyc.baselibs.mybatis.mysql;

import java.lang.reflect.Field;
import java.sql.JDBCType;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.dao.SqlScriptProvider;
import com.zyc.baselibs.dao.SqlScriptProviderSupport;

public class SqlScriptProviderForWhereIn extends SqlScriptProviderSupport implements SqlScriptProvider {

	private static final Logger logger = Logger.getLogger(SqlScriptProviderForWhereIn.class);

	private static final String EX_PREFIX = "[generateSql(...)] - ";
	
	private static final String FORMAT_FOREACH = 
	"<if test=\"konwledges!=null\"> " + 
	"and %s in " + 
	"<foreach collection=\"%s\" item=\"tmp\" separator=\",\" open=\"(\" close=\")\"> " + 
	"#{tmp%s} " +
	"</foreach>" + 
	"</if>";

	@SuppressWarnings("unchecked")
	public String generateSql(final Object obj) {
		Map<String, Object> param = (Map<String, Object>) obj;
		Map<String, Object> field2values = (Map<String, Object>) param.get(PARAM_KEY_FIELD2VALUES);
		Class<?> clazz = (Class<?>) param.get(PARAM_KEY_CLASS);
		String table = this.getTableName(clazz);
		final StringBuilder selectSql = new StringBuilder();
		selectSql.append("select * from ").append(table).append(" where 1=1 ");

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

			JDBCType jdbcType = DatabaseUtils.getJdbcType(field);
			String jdbcTypeValue = jdbcType == null ? "" : (",jdbcType=" + jdbcType.name()); 
			
			String a =  " <if test=\"id != null\"> " + 
							//"and %s in " + 
							//"<foreach collection=\"%s\" item=\"item\" separator=\",\" open=\"(\" close=\")\"> " + 
							//"#{item%s} " + //field2values
							//"#{item%s} " + //field2values
							//"</foreach>" 
							//+
							"and id='1'" 
							+
							"</if>"
							;
			if(Collection.class.isAssignableFrom(values.getClass())) {
				//selectSql.append(String.format(a, fieldName, DatabaseUtils.getColumnName(field, true), fieldName,jdbcTypeValue));
				//selectSql.append(String.format(a, DatabaseUtils.getColumnName(field, true), "id", jdbcTypeValue));
				selectSql.append(a);
			} else if(values.getClass().isArray()) {
				//selectSql.append(String.format(a, fieldName, DatabaseUtils.getColumnName(field, true), fieldName,jdbcTypeValue));
				//selectSql.append(String.format(a, DatabaseUtils.getColumnName(field, true), "id", jdbcTypeValue));
				selectSql.append(a);
			} else {
				selectSql.append(" and ").append(DatabaseUtils.getColumnName(field, true)).append("=#{").append(fieldName).append(jdbcTypeValue).append("}");
			}
		}

		String sql = selectSql.toString();
		logger.debug(EX_PREFIX + sql);
		return sql;
	}

}
