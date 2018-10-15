package com.zyc.baselibs.dao;

public enum SqlAction {
	INSERT("Insert"),
	DELETE("Delete"),
	UPDATE("Update"),
	SELECT("Select"),
	SELECTBYPAGE("SelectByPage"),
	LOAD("Load");
	
	private SqlAction(String name) {
		this.name = name;
	}
	
	private String name;

	public String getName() {
		return name;
	}
}
