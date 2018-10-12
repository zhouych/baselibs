package com.zyc.baselibs.vo;

import com.zyc.baselibs.entities.Labelable;

public class EntryBean implements java.io.Serializable, Labelable {

	private static final long serialVersionUID = -7570988841498043170L;

	private String value;
	private String text;

	public EntryBean() {
	}
	
	public EntryBean(String value, String text) {
		this.value = value;
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

	public String label() {
		return this.getValue() + " - " + this.getText();
	}
}
