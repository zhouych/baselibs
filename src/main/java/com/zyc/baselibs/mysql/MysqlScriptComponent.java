package com.zyc.baselibs.mysql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.ClassLoaderUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;

@Component
public class MysqlScriptComponent {
	
	static final String FORAMT_TABLE_CREATE_SQL_SCRIPT 
	= "DROP TABLE IF EXISTS `%s`;" + "\n"
	+ "CREATE TABLE `%s` (" + "\n"
	+ "%s" + "\n"
	+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
	
	static final String FORMAT_COLUMN_DEFINITION = "  `%s` %s %s %s";
	
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
							String dbType = DatabaseUtils.getDbType(field);
							boolean nullable = DatabaseUtils.getNullable(field);
							String _null = nullable ? "NULL" : "NOT NULL";
							String defaultValue = nullable ? "DEFAULT NULL" : "";

							DatabaseColumn dc = DatabaseUtils.getColumn(field);
							if(dc != null) {
								if(dc.pk()) {
									dbType = "varchar(36)";
								} else if(dc.version()) {
									dbType = "int(11)";
								}
							}
							
							columns.add("id".equals(column) ? 0 : columns.size(), String.format(FORMAT_COLUMN_DEFINITION, column, dbType, _null, defaultValue));
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
	
	public static void main(String[] args) {
		List<String> cities = Arrays.asList("Milan", 
                "London", 
                "New York", 
                "San Francisco");
		String citiesCommaSeparated = String.join(",", cities);
		System.out.println(citiesCommaSeparated);
	}
}
