package com.zyc.baselibs.dao;

public enum DatabaseType {
	MYSQL("mysql"),
	ORACLE("oracle");

	private DatabaseType(String name) {
		this.name = name;
	}
	
	private String name;

	public String getName() {
		return name;
	}
}
