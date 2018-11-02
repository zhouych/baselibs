package com.zyc.baselibs.dao;

public enum SqlAction {
	INSERT("Insert"),
	DELETE("Delete"),
	DELETEBYID("DeleteById"),
	UPDATE("Update"),
	SELECT("Select"),
	WHEREIN("WhereIn"),
	KEYWORDSELECT("KeywordSelect"),
	SELECTBYPAGE("SelectByPage"),
	KEYWORDSELECTBYPAGE("KeywordSelectByPage"),
	KEYWORDSELECTTOTALCOUNT("KeywordSelectTotalCount"),
	LOAD("Load");
	
	private SqlAction(String name) {
		this.name = name;
	}
	
	private String name;

	public String getName() {
		return name;
	}
}
