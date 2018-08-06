package com.zyc.baselibs.mybatis;

public enum SqlType {
	INSERT("Insert"),
	DELETE("Delete"),
	UPDATE("Update"),
	SELECT("Select"),
	SELECTBYPAGE("SelectByPage");
	
	private SqlType(String name) {
		this.name = name;
	}
	
	private String name;

	public String getName() {
		return name;
	}
}
