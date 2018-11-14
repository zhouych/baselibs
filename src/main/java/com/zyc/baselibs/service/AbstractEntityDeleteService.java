package com.zyc.baselibs.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.baselibs.asserts.AssertThrowNonRuntime;
import com.zyc.baselibs.dao.MybatisBaseMapper;
import com.zyc.baselibs.data.DataStatus;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.vo.DeleteMode;

public abstract class AbstractEntityDeleteService implements EntityDeleteService {
	
	protected static final String ACTION_DELETE = "delete";

	/**
	 * {@inheritDoc}
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean delete(String entityId, DeleteMode mode) throws Exception {
		AssertThrowNonRuntime.notNull(mode, "This parameter 'mode' is null or empty, failed to delete. (entityId=" + entityId + "; mode=" + mode.toString() + ")");
		
		if(mode.equals(DeleteMode.LOGIC)) {
			return this.deleteOnLogic(entityId);
		} else if(mode.equals(DeleteMode.PHYSICAL)) {
			return this.deleteOnPhysical(entityId);
		} else {
			throw new BussinessException("This deletion mode is not supported, failed to delete. (entityId=" + entityId + "; mode=" + mode.toString() + ")");
		}
	}
	
	/**
	 * 加载可被删除的实体
	 * @param entityId
	 * @param clazz
	 * @param mapper
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	protected <T extends BaseEntity> T loadDeletableEntity(String entityId, Class<T> clazz, MybatisBaseMapper<T> mapper, DeleteMode mode) throws Exception {
		AssertThrowNonRuntime.hasText(entityId, "This parameter 'entityId' is null or empty.");
		
		T entity = mapper.load(entityId, clazz);
		
		AssertThrowNonRuntime.notNull(entity, "This form does not exist. (entityId=" + entityId + ")");
		
		//不能逻辑删除的情况：数据处于已逻辑删除状态或者已锁住状态
		//不能物理删除的情况：数据处于已锁住状态
		if((DeleteMode.LOGIC.equals(mode) && entity.getDatastatus().equals(DataStatus.DELETED.getValue())) || entity.getDatastatus().equals(DataStatus.LOCKED.getValue())) {
			throw new BussinessException("The entity was " + entity.getDatastatus().toLowerCase() + ". (entity.id=" + entity.getId() + ")");
		}
		
		return entity;
	}
	
}
