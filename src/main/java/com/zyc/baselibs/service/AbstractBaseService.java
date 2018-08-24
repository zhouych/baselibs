package com.zyc.baselibs.service;

import com.zyc.baselibs.dao.MybatisBaseMapper;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.ex.BussinessException;

public abstract class AbstractBaseService {
	
	protected static final String ACTION_UPDATE = "update";
	
	protected static final String ACTION_DELETE = "delete";
	
	/**
	 * Update entity object
	 * @param mapper 
	 * @param entity
	 * @param action Options：update | delete
	 * @return Affected row count.
	 * @throws BussinessException
	 */
	protected <T extends BaseEntity> int update(MybatisBaseMapper<T> mapper, T entity, String action) throws Exception {
		entity.update();
		int result = mapper.update(entity);
		if(result < 1) {
			throw new BussinessException("The data does not exist or the data conflicts, you can try to " + action + " again.");
		}
		return result;
	} 
}
