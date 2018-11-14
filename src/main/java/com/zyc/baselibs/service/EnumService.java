package com.zyc.baselibs.service;

import java.util.List;

import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.EntryBeanable;

public interface EnumService {
	<T extends Enum<?> & EntryBeanable> List<EntryBean> enumToList(Class<T> clazz);
}
