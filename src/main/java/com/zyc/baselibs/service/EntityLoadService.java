package com.zyc.baselibs.service;

import com.zyc.baselibs.entities.BaseEntity;

public interface EntityLoadService<T extends BaseEntity> {
	
	 T load(String entityId);
	
}
