package com.zyc.baselibs.mybatis;

import com.zyc.baselibs.commons.ReflectUtils;

public final class SqlbuilderFactory {
	
	private static final String FMT_PACKAGE_NAME = "com.zyc.baselibs.mybatis.%s" + Sqlbuilder.class.getSimpleName(); 
	
	public static Sqlbuilder getInstance(SqlType sqlType, Object ... args) {
		return ReflectUtils.clazzInstance(String.format(FMT_PACKAGE_NAME, sqlType.getName()), args);
	}
}
