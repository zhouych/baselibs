package com.zyc.baselibs.web;

public enum ClientAction {
	ADD("add", "新增"),
	EDIT("edit", "编辑");
	
	private String value;
	private String text;
	
	private ClientAction(String value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public String getValue() {
		return value;
	}
	public String getText() {
		return text;
	}
}
