package com.zyc.baselibs.commons;

import org.apache.log4j.Logger;

import com.zyc.baselibs.ex.BussinessException;

public class Log4jUtlis {
	
	public static void error(Logger logger, Throwable e) {
		if(e instanceof BussinessException) {
			logger.error(e.getMessage());
		} else {
			logger.error(e.getMessage(), e);
		}
	}
	
	public static void error(Logger logger, String message, Throwable e) {
		if(e instanceof BussinessException) {
			logger.error(message);
		} else {
			logger.error(message, e);
		}
	}
}
