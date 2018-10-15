package com.zyc.baselibs.dao;

/**
 * Sql生成器
 * @author zhouyancheng
 *
 */
public interface SqlProvider {
	
	/**
	 * 生成Sql
	 * @return 符合mybatis规范的sql（即支持含有参数占位符等等）
	 */
	String generateSql(Object obj);
}
