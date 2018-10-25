package com.zyc.baselibs.web.bootstrap;

import com.zyc.baselibs.web.EmptyNodeType;

public class HierarchySelectNode implements java.io.Serializable {
	
	private static final long serialVersionUID = 1970665601342554943L;
	
	private String value;
	private int level;
	private String text;
	
	public HierarchySelectNode() {}
	
	public HierarchySelectNode(String value, int level, String text) {
		this.value = value;
		this.level = level;
		this.text = text;
	}
	
	public HierarchySelectNode(EmptyNodeType empty) {
		this.value = empty.getValue();
		this.level = 1;
		this.text = empty.getText();
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
