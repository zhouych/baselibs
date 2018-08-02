package com.zyc.baselibs.dao;

import org.apache.ibatis.annotations.UpdateProvider;

import com.zyc.baselibs.entities.BaseEntity;

public interface MybatisBaseMapper<T extends BaseEntity> {
	
	@UpdateProvider(type = MybatisSqlProvider.class, method = "update")
	int update(T entity) throws Exception;
	
}