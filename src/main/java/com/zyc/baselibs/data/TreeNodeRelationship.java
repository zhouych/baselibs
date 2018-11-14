package com.zyc.baselibs.data;

import java.util.ArrayList;
import java.util.List;

import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.EntryBeanable;

public enum TreeNodeRelationship implements EntryBeanable {
	//ancestor("ancestor", "祖先"),
	//parents("parents", "父辈"),
	//father("father", "父级"),
	SELF("self", "本身"),
	PEER("peer", "同辈"),
	PEERLEAF("peerleaf", "同辈（叶子）"),
	CHILDREN("children", "成员"),
	CHILDRENLEAF("childrenleaf", "成员（叶子）"),
	DESCENDANT("descendant", "成员及后代"),
	DESCENDANTLEAF("descendantleaf", "成员及后代（叶子）");
	
	private String value;
	private String text;

	private TreeNodeRelationship(String value, String text) {
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
		for (TreeNodeRelationship ds : TreeNodeRelationship.values()) {
			list.add(new EntryBean(ds.getValue(), ds.getText()));
		}
		return list;
	}
}
