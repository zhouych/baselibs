package com.zyc.baselibs.ex;

public class IllegalEditedException extends BussinessException {

	private static final long serialVersionUID = 751645757309054458L;

	public IllegalEditedException() {
		super();
	}
	
	public IllegalEditedException(String message) {
		super(message);
	}
	
	public IllegalEditedException(Throwable e) {
		super(e);
	}
	
	public IllegalEditedException(String message, Throwable e) {
		super(message, e);
	}
}
