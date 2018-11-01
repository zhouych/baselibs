package com.zyc.baselibs.service;

import com.zyc.baselibs.asserts.AssertThrowNonRuntime;
import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.vo.DeleteMode;

public abstract class AbstractEntityDeleteService implements EntityDeleteService {

	public boolean delete(String entityId, DeleteMode mode) throws Exception {
		AssertThrowNonRuntime.notNull(mode, "This parameter 'mode' is null or empty, failed to delete. (mode=" + mode.toString() + ")");
		
		if(mode.equals(DeleteMode.LOGIC)) {
			return this.deleteOnLogic(entityId);
		} else if(mode.equals(DeleteMode.PHYSICAL)) {
			return this.deleteOnPhysical(entityId);
		} else {
			throw new BussinessException("This deletion mode is not supported, failed to delete. (mode=" + mode.toString() + ")");
		}
	}
	
}
