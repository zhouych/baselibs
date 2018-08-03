package com.zyc.baselibs.vo;

public enum DeleteMode {
	/**
	 * 逻辑删除模式
	 */
	LOGIC("逻辑删除"),
	/**
	 * 物理删除模式
	 */
	PHYSICAL("物理删除");
	
	private DeleteMode(String text) {
		this.text = text;
	}
	
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
