package com.zyc.baselibs.mybatis;

import com.zyc.baselibs.commons.ReflectUtils;

public final class SqlProviderFactory {

	private static final String FMT_PACKAGE_NAME = "com.zyc.baselibs.mybatis." + SqlProvider.class.getSimpleName() + "For%s";
	
	public static SqlProvider getInstance(SqlAction sqlAction, Object[] args) {
		return ReflectUtils.clazzInstance(String.format(FMT_PACKAGE_NAME, sqlAction.getName()), args);
	}
}
