package com.zyc.baselibs.vo;

public class ValueCarrier {
	
	private Object value;

	public ValueCarrier() {
		
	}
	
	public ValueCarrier(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
