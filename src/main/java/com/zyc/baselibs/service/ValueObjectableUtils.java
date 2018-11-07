package com.zyc.baselibs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.entities.BaseEntity;

public class ValueObjectableUtils {

	public static <E extends BaseEntity, V extends E> List<V> fromEntities(Collection<E> entities, Class<V> vclazz, Class<E> eclazz, Visitor<Map<String, E>, V> visitor) {
		List<V> result = null;
		if(CollectionUtils.hasElement(entities)) {
			result = new ArrayList<V>();
			V vo;
			Map<String, E> param = new HashMap<String, E>();
			for (E form : entities) {
				try {
					vo = vclazz.getConstructor(eclazz).newInstance(form);
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				} 
				if(visitor != null) {
					param.clear();
					param.put("vo", vo);
					param.put("entity", form);
					visitor.visit(param);
				}
				result.add(vo);
			}
		}
		return result;
	}

}
