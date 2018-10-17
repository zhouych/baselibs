package com.zyc.baselibs.entities;

import java.util.ArrayList;
import java.util.List;

import com.zyc.baselibs.vo.EntryBean;

public enum MemberRelation {
	//ancestor("ancestor", "祖先"),
	//parents("parents", "父辈（父亲及其兄弟姐妹）"),
	//father("father", "父亲"),
	SELF("self", "本身（自己）"),
	PEER("peer", "同辈（自己以及兄弟姐妹）"),
	CHILDREN("children", "成员（子女）"),
	DESCENDANT("descendant", "成员及后代（子孙后代）");
	
	private String value;
	private String text;

	private MemberRelation(String value, String text) {
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
		for (MemberRelation ds : MemberRelation.values()) {
			list.add(new EntryBean(ds.getValue(), ds.getText()));
		}
		return list;
	}
}
