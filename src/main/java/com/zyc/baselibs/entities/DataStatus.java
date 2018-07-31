package com.zyc.baselibs.entities;

public enum DataStatus {
	ENABLED("已启用"), 
	DISABLED("已禁用"), 
	LOCKED("已锁住"),
	DELETED("已删除");
	
	private String text;
	
	private DataStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
