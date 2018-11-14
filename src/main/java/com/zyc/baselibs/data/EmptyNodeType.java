package com.zyc.baselibs.data;

import java.util.ArrayList;
import java.util.List;

import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.EntryBeanable;

/**
 * 空节点类型：用于UI的下拉列表、树形等插件，这些插件的数据源有时候需要一个无业务意义的节点。
 * @author zhouyancheng
 *
 */
public enum EmptyNodeType implements EntryBeanable {
	NONE("none", "------"),
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

	public static List<EntryBean> toList() {
		List<EntryBean> list = new ArrayList<EntryBean>();
		for (EmptyNodeType ds : EmptyNodeType.values()) {
			list.add(new EntryBean(ds.getValue(), ds.getText()));
		}
		return list;
	}
}
