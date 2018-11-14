package com.zyc.baselibs.data;

import java.util.ArrayList;
import java.util.List;

import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.EntryBeanable;

public enum IncludeOrExclude implements EntryBeanable {
	INCLUDE("include", "包含"),
	EXCLUDE("exclude", "排除");
	
	private String value;
	private String text;

	private IncludeOrExclude(String value, String text) {
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
		for (IncludeOrExclude ds : IncludeOrExclude.values()) {
			list.add(new EntryBean(ds.getValue(), ds.getText()));
		}
		return list;
	}
}
