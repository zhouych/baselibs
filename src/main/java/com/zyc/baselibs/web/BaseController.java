package com.zyc.baselibs.web;

import org.apache.log4j.Logger;

import com.zyc.baselibs.commons.Log4jUtlis;
import com.zyc.baselibs.ex.ExceptionUtils;

public abstract class BaseController {

	protected void handleException(ResponseResult result, Exception e, Logger logger) {
		result.setStatus("1");
		String message = ExceptionUtils.uimessage(e);
		result.setMessage(message);
		Log4jUtlis.error(logger, message, e);
	}
}
