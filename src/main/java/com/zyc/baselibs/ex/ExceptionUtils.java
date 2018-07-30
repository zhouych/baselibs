package com.zyc.baselibs.ex;

public class ExceptionUtils {
	
	public static final String EX_UNKNOWN_ERROR  = "Unknown error, please contact the administrator.";
	
	public static String uimessage(Exception e) {
		return null != e && e instanceof BussinessException ? e.getMessage() : EX_UNKNOWN_ERROR;
	}
}
