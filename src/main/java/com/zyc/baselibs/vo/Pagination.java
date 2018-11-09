package com.zyc.baselibs.vo;

import java.util.List;

import com.zyc.baselibs.commons.StringUtils;

/**
 * 分页对象
 * @author zhouyancheng
 *
 */
public class Pagination {
	
	private int pageNumber;
	private int pageRowCount;
	private List<SortField> prevSorts;
	private String order;
	private boolean asc;
	private List<SortField> nextSorts;

	public Pagination() {
		
	}
	
	public Pagination(int pageNumber, int pageRowCount, String order, boolean asc) {
		this.setPageNumber(pageNumber);
		this.setPageRowCount(pageRowCount);
		this.setOrder(order);
		this.setAsc(asc);
	}
	
	/**
	 * 获取页码。
	 * @return
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	
	/**
	 * 设置页码。
	 * @see 缺省值=1，如果传入值小于1，则取缺省值
	 * @param pageNumber
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber > 0 ? pageNumber : 1;
	}
	
	/**
	 * 获取每页显示最大行数。
	 * @return
	 */
	public int getPageRowCount() {
		return pageRowCount;
	}
	
	/**
	 * 设置每页显示最大行数。
	 * @see 缺省值=20，如果传入的值小于1，则取缺省值。
	 * @param pageRowCount
	 */
	public void setPageRowCount(int pageRowCount) {
		this.pageRowCount = pageRowCount > 0 ? pageRowCount : 20;
	}
	
	/**
	 * 优先排序信息：处于当前对象的order字段之前的排序信息列表
	 * @return
	 */
	public List<SortField> getPrevSorts() {
		return prevSorts;
	}

	public void setPrevSorts(List<SortField> prevSorts) {
		this.prevSorts = prevSorts;
	}

	/**
	 * 获取排序字段（查询实体对象的字段名称）。
	 * @return
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序字段（查询实体对象的字段名称）
	 * @see 缺省值=null，如果传入的值为null、空字符串、全空格字符串，则取缺省值。
	 * @return
	 */
	public void setOrder(String order) {
		this.order = StringUtils.isBlank(order) ? null : order;
	}
	
	/**
	 * 获取是否按升序进行排序： true - 升序； false - 降序。
	 * @return
	 */
	public boolean isAsc() {
		return asc;
	}

	/**
	 * 设置是否按升序进行排序： true - 升序； false - 降序。
	 * @return
	 */
	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	/**
	 * 最后排序信息：处于当前对象的order字段之后的排序信息列表
	 * @return
	 */
	public List<SortField> getNextSorts() {
		return nextSorts;
	}

	public void setNextSorts(List<SortField> nextSorts) {
		this.nextSorts = nextSorts;
	}

	/**
	 * 获取分页在数据库的起始行索引
	 * @return
	 */
	public int getStartIndex() {
		return (this.getPageNumber() - 1) * this.getPageRowCount() + 1;
	}

	/**
	 * 获取分页在数据库的结束行索引
	 * @return
	 */
	public int getEndIndex() {
		return this.getStartIndex() + this.getPageRowCount() - 1;
	}
}
