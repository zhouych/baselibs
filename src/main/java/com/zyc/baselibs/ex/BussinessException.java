package com.zyc.baselibs.ex;

public class BussinessException extends Exception {

	private static final long serialVersionUID = 5190502964197834070L;
	
	public BussinessException() {
		super();
	}
	
	public BussinessException(String message) {
		super(message);
	}
	
	public BussinessException(Throwable e) {
		super(e);
	}
	
	public BussinessException(String message, Throwable e) {
		super(message, e);
	}
}
