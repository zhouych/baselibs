package com.zyc.baselibs.annotation;

import java.lang.reflect.Field;
import java.sql.JDBCType;
import java.util.HashMap;
import java.util.Map;

import com.zyc.baselibs.commons.StringUtils;

public class DatabaseUtils {
	public static String getTableName(Class<?> clazz) {
		return clazz.isAnnotationPresent(DatabaseTable.class) ? clazz.getAnnotation(DatabaseTable.class).name() : null;
	}
	
	public static DatabaseColumn getColumn(Field field) {
		return field.isAnnotationPresent(DatabaseColumn.class) ? field.getAnnotation(DatabaseColumn.class) : null;
	}
	
	public static boolean isPrimaryKey(Field field) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		return null != column && column.pk();
	}
	
	public static boolean isVersion(Field field) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		return null != column && column.version();
	}

	/**
	 * 获取对应数据库的列名
	 * @param field 实体对象的字段
	 * @return 
	 * 返回该字段的{@link DatabaseColumn}注解的<code>name</code>值。</br>
	 * 如果字段没有被{@link DatabaseColumn}注解标记，返回<code>null</code>值。</br>
	 * 如果字段被{@link DatabaseColumn}注解标记，但是没有定义<code>name</code>值，则返回字段名称。
	 */
	public static String getColumnName(Field field) {
		return DatabaseUtils.getColumnName(field, false);
	}
	
	/**
	 * 获取对应数据库的列名
	 * @param field 实体对象的字段
	 * @param defaultFieldName 是否启用默认取字段名。<tt></br>当<code>field</code>不是{@link DatabaseColumn}注解时，是否取字段名称</tt>。
	 * @return
	 */
	public static String getColumnName(Field field, boolean defaultFieldName) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		if(column == null) {
			return defaultFieldName ? field.getName() : null;
		}
		
		return StringUtils.isBlank(column.name()) ? field.getName() : column.name();
	}
	
	private static final Map<String, JDBCType> fieldType2jdbcType = new HashMap<String, JDBCType>();
	
	static {
		fieldType2jdbcType.put("boolean", JDBCType.BOOLEAN);
		fieldType2jdbcType.put("java.lang.Boolean", JDBCType.BOOLEAN);
		fieldType2jdbcType.put("char", JDBCType.CHAR);
		fieldType2jdbcType.put("java.lang.Character", JDBCType.CHAR);
		fieldType2jdbcType.put("int", JDBCType.INTEGER);
		fieldType2jdbcType.put("java.lang.Integer", JDBCType.INTEGER);
		fieldType2jdbcType.put("long", JDBCType.NUMERIC);
		fieldType2jdbcType.put("java.lang.Long", JDBCType.NUMERIC);
		fieldType2jdbcType.put("float", JDBCType.FLOAT);
		fieldType2jdbcType.put("java.lang.Float", JDBCType.FLOAT);
		fieldType2jdbcType.put("double", JDBCType.DOUBLE);
		fieldType2jdbcType.put("java.lang.Double", JDBCType.DOUBLE);
		fieldType2jdbcType.put("java.lang.String", JDBCType.VARCHAR);
		fieldType2jdbcType.put("java.util.Date", JDBCType.TIMESTAMP);
	}
	
	public static JDBCType getJdbcType(Field field) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		JDBCType jdbcType = column != null ? column.jdbcType() : null;
		if(jdbcType == null || jdbcType == JDBCType.NULL) {
			String fieldType = field.getType().getName();
			if(!fieldType2jdbcType.containsKey(fieldType)) {
				throw new RuntimeException("当前字段[" + field.getName() + "]的类型[type=" + fieldType + "]对应的java.sql.JDBCType未定义。");
			}
			jdbcType = fieldType2jdbcType.get(fieldType);
		}
		return jdbcType;
	}
	
	public static int getJdbcTypeVarcharLength(Field field) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		return column != null ? column.jdbcTypeVarcharLength() : 128;
	}
	
	public static boolean getNullable(Field field) {
		DatabaseColumn column = DatabaseUtils.getColumn(field);
		return column == null || column.nullable();
	}

	private static final Map<JDBCType, String> jdbcType2mysqlDbType = new HashMap<JDBCType, String>();

	static {
		jdbcType2mysqlDbType.put(JDBCType.BOOLEAN, "TINYINT(1)");
		jdbcType2mysqlDbType.put(JDBCType.CHAR, "CHAR(1)");
		jdbcType2mysqlDbType.put(JDBCType.INTEGER, "INT");
		jdbcType2mysqlDbType.put(JDBCType.NUMERIC, "LONGBLOB");
		jdbcType2mysqlDbType.put(JDBCType.FLOAT, "FLOAT");
		jdbcType2mysqlDbType.put(JDBCType.DOUBLE, "DOUBLE");
		jdbcType2mysqlDbType.put(JDBCType.VARCHAR, "VARCHAR");
		jdbcType2mysqlDbType.put(JDBCType.NVARCHAR, "NVARCHAR");
		jdbcType2mysqlDbType.put(JDBCType.TIMESTAMP, "TIMESTAMP");
	}

	public static String getMysqlDbType(Field field) {
		JDBCType jdbcType = getJdbcType(field);
		if(!jdbcType2mysqlDbType.containsKey(jdbcType)) {
			throw new RuntimeException("当前JDBCType值[jdbcType=" + jdbcType.name() + "]对应Mysql下的DbType未定义。");
		}
		
		String dbType = jdbcType2mysqlDbType.get(jdbcType);
		if(jdbcType == JDBCType.VARCHAR || jdbcType == JDBCType.NVARCHAR) {
			dbType += "(" + getJdbcTypeVarcharLength(field) + ")";
		}
		
		return dbType;
	}
}
