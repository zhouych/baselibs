package com.zyc.baselibs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.dao.MybatisBaseMapper;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.EntryBeanable;

public abstract class AbstractBaseService extends AbstractEntityDeleteService implements EnumService {
	
	protected static final String ACTION_UPDATE = "update";
	
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
	
	public <T extends Enum<?> & EntryBeanable> List<EntryBean> enumToList(Class<T> clazz) {
		List<EntryBean> beans = new ArrayList<EntryBean>();
		EntryBean bean;
		for (T value : ReflectUtils.invokeValues(clazz)) {
			bean = new EntryBean();
			BeanUtils.copyProperties(value, bean);
			beans.add(bean);
		}
		return beans;
	}
	
	public <T extends Enum<?> & EntryBeanable> Map<String, String> enumToMap(Class<T> clazz) {
		Map<String, String> map = new HashMap<String, String>();
		for (T value : ReflectUtils.invokeValues(clazz)) {
			map.put(value.getValue(), value.getText());
		}
		return map;
	}
}
