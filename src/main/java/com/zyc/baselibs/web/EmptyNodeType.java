package com.zyc.baselibs.web;

/**
 * 空节点类型：用于UI的下拉列表、树形等插件，这些插件的数据源有时候需要一个无业务意义的节点。
 * @author zhouyancheng
 *
 */
public enum EmptyNodeType {
	NONE("none", ""),
	ALL("all", "全部"),
	OPTIONAL("optional", "请选择");

	private String value;
	private String text;

	private EmptyNodeType(String value, String text) {
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
