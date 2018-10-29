package com.zyc.baselibs.vo;

import java.util.List;

public class PaginationResult<T extends Object> implements java.io.Serializable {
	
	private static final long serialVersionUID = -5602517987649930653L;
	
	private List<T> rows;
	private int total;
	private Pagination pagination;
	
	public PaginationResult() {
		this.total = 0;
	}
	
	public PaginationResult(Pagination pagination) {
		this();
		this.pagination = pagination;
	}
	
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
