package com.zyc.baselibs.web;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;

import com.zyc.baselibs.commons.Log4jUtlis;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.entities.DataStatus;
import com.zyc.baselibs.ex.ExceptionUtils;
public abstract class BaseController {
	
    protected static final String restPath = "/api";
    
    protected static final String STRING_EMPTY = "";
    
    protected String getCommonPath() {
    	return STRING_EMPTY;
    }

	protected void handleException(ResponseResult result, Exception e, Logger logger) {
		result.setStatus("1");
		String message = ExceptionUtils.uimessage(e);
		result.setMessage(message);
		Log4jUtlis.error(logger, message, e);
	}

    protected <T extends BaseEntity> String requestDetail(Model model, ClientAction action, String entityId, boolean readonly, T entity) {
    	model.addAttribute("action", action.getValue());
    	model.addAttribute("actionText", action.getText());
    	model.addAttribute("allDataStatus", DataStatus.toList());
    	model.addAttribute("readonly", readonly || !DataStatus.ENABLED.getValue().equals(entity.getDatastatus()));
    	return this.getCommonPath() + "/detail";
    }
}
