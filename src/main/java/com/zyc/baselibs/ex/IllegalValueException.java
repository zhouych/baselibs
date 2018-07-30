package com.zyc.baselibs.ex;

public class IllegalValueException extends BussinessException {

	private static final long serialVersionUID = -8124571703978195187L;
	
	public IllegalValueException() {
		super();
	}
	
	public IllegalValueException(String message) {
		super(message);
	}
	
	public IllegalValueException(Throwable e) {
		super(e);
	}
	
	public IllegalValueException(String message, Throwable e) {
		super(message, e);
	}
}
