package com.zyc.baselibs.web.bootstrap;

import java.util.List;

public class BootstrapTableDataSource<T extends Object> {
	
	private int total;
	
	private List<T> rows;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
