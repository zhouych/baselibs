package com.zyc.baselibs.mybatis;

import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.dao.DatabaseType;
import com.zyc.baselibs.dao.SqlAction;
import com.zyc.baselibs.dao.SqlScriptProvider;

public final class SqlScriptProviderFactory {

	private static final String FMT_PACKAGE_NAME = "com.zyc.baselibs.mybatis.%s." + SqlScriptProvider.class.getSimpleName() + "For%s";
	
	public static SqlScriptProvider getInstance(DatabaseType databaseType, SqlAction sqlAction, Object[] args) {
		return ReflectUtils.clazzInstance(String.format(FMT_PACKAGE_NAME, databaseType.getName(), sqlAction.getName()), args);
	}
}
