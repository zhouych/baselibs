package com.zyc.baselibs.data;

import java.util.ArrayList;
import java.util.List;

import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.EntryBeanable;

public enum DataStatus implements EntryBeanable {
	ENABLED("enabled", "已启用"), 
	DISABLED("disabled", "已禁用"), 
	LOCKED("locked", "已锁住"),
	DELETED("deleted", "已删除");

	private String value;
	private String text;

	private DataStatus(String value, String text) {
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
		for (DataStatus ds : DataStatus.values()) {
			list.add(new EntryBean(ds.getValue(), ds.getText()));
		}
		return list;
	}
}
