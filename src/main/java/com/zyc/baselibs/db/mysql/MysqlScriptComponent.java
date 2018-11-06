package com.zyc.baselibs.db.mysql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ClassLoaderUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;

@Component
public class MysqlScriptComponent {
	
	private static final String FORAMT_TABLE_CREATE_SQL_SCRIPT 
	= "\n"
	+ "DROP TABLE IF EXISTS `%s`;" + "\n"
	+ "CREATE TABLE `%s` (" + "\n"
	+ "%s" + "\n"
	+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;" + "\n";
	
	private static final String FORMAT_COLUMN_DEFINITION = "  `%s` %s %s %s";
	
	private static final String FORMAT_PK_DEFINITION = "  PRIMARY KEY (`%s`)";
	
	/**
	 * 得到指定包下所有orm数据实体的建表sql脚本
	 * @param entityPackageName orm实体的包名
	 * @return
	 */
	public List<String> entity2tableSqlScripts(String entityPackageName) {
		List<String> classNames = ClassLoaderUtils.getClazzNames(entityPackageName, true);
		if(classNames == null || classNames.isEmpty()) {
			return null;
		}
		
		List<String> tableSqlScripts = new ArrayList<String>();
		for (String className : classNames) {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				clazz = null;
				e.printStackTrace();
			}
			
			if(clazz != null) {
				String table = DatabaseUtils.getTableName(clazz);
				if(StringUtils.isNotBlank(table)) {
					final List<String> columns = new ArrayList<String>();
					ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
						public Boolean visit(Field field) {
							String column = DatabaseUtils.getColumnName(field, true);
							boolean pk = DatabaseUtils.isPrimaryKey(field);
							String dbType = DatabaseUtils.getMysqlDbType(field);
							boolean nullable = !pk && DatabaseUtils.getNullable(field);
							String nullPart = nullable ? "NULL" : "NOT NULL";
							String defaulPart = nullable ? "DEFAULT NULL" : "";
							columns.add(pk ? 0 : columns.size(), String.format(FORMAT_COLUMN_DEFINITION, column, dbType, nullPart, defaulPart));
							if(pk) {
								columns.add(1, String.format(FORMAT_PK_DEFINITION, column));
							}
							return false;
						}
					}, false, ReflectUtils.MODIFIER_STATIC$FINAL);
					
					if(!columns.isEmpty()) {
						tableSqlScripts.add(String.format(FORAMT_TABLE_CREATE_SQL_SCRIPT, table, table, String.join(",\n", columns)));	
					}
				}
			}
		}
		
		return tableSqlScripts;
	}
	
	public String formatInsertSqlScripts(Class<?> clazz) {
		String table = DatabaseUtils.getTableName(clazz);
		if(StringUtils.isBlank(table)) {
			return null;
		}

		final List<String> columns = new ArrayList<String>();
		ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				columns.add(DatabaseUtils.getColumnName(field, true));
				return false;
			}
		}, false, ReflectUtils.MODIFIER_STATIC$FINAL);
		
		if(!columns.isEmpty()) {
			return "insert into " + table + "(" + String.join(",", columns) + ") values(@" + String.join(",@", columns) + ")";
		}
		
		return null;
	}
}
