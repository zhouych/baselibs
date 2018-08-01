package com.zyc.baselibs.dao;

import com.zyc.baselibs.entities.BaseEntity;

public interface BaseMapper<T extends BaseEntity> {
	
	int update(T entity) throws Exception;
}
