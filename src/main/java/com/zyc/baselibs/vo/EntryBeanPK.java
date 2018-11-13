package com.zyc.baselibs.vo;

public class EntryBeanPK extends EntryBean {

	private static final long serialVersionUID = -7123579637663181485L;
	
	private String id;
	
	public EntryBeanPK() {
		super();
	}
	
	public EntryBeanPK(String id, String value, String text) {
		super(value, text);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
