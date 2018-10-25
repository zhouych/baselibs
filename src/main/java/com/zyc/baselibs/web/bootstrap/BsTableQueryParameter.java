package com.zyc.baselibs.web.bootstrap;

import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.vo.Pagination;

public class BsTableQueryParameter<T extends BaseEntity> implements java.io.Serializable {
	
	private static final long serialVersionUID = -129249679725638803L;
	
	private Pagination pagination;
	private T condition;
	private String searchText;
	
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public T getCondition() {
		return condition;
	}
	public void setCondition(T condition) {
		this.condition = condition;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
}
