package com.zyc.baselibs.web.bootstrap;

public class TreeSelectNode implements java.io.Serializable {
	
	private static final long serialVersionUID = -3157941346853908031L;
	
	private String value;
	private String text;
	private String parent;
	
	public TreeSelectNode() {}
	
	public TreeSelectNode(String value, String parent, String text) {
		this.value = value;
		this.parent = parent;
		this.text = text;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
}
