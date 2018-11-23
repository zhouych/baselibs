package com.zyc.baselibs.service;

import java.util.List;
import java.util.Map;

import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.EntryBeanable;

public interface EnumService {
	
	<T extends Enum<?> & EntryBeanable> List<EntryBean> enumToList(Class<T> clazz);
	
	<T extends Enum<?> & EntryBeanable> Map<String, String> enumToMap(Class<T> clazz);
}
