package com.zyc.baselibs.vo;


/**
 * 排序字段信息
 * @author zhouyancheng
 *
 */
public class SortField {
	
	/**
	 * 排序字段
	 */
	private String order;
	
	/**
	 * 是否升序：true - 升序; false - 降序 
	 */
	private boolean asc;
	
	public SortField() {
		
	}
	
	public SortField(String order, boolean asc) {
		this.order = order;
		this.asc = asc;
	}
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public boolean isAsc() {
		return asc;
	}
	public void setAsc(boolean asc) {
		this.asc = asc;
	}
}
