package com.zyc.baselibs.mybatis.mysql;

import java.util.List;

import com.zyc.baselibs.annotation.DatabaseUtils;
import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.baselibs.vo.SortField;

public class SqlScriptPaginationUtils {
	
	/**
	 * 追加Mysql下的分页SQL语句
	 * @param targetSql 待追加分页SQL语句的目标SQL
	 * @param pagination 分页对象
	 */
	public static void appendLimitSql(StringBuilder targetSql, Pagination pagination) {
		targetSql.append(" limit ").append(pagination.getStartIndex() - 1).append(",").append(pagination.getPageRowCount());

	}
	
	/**
	 * 生成排序SQL语句。
	 * <p>例如： " order by column1 asc, column2 desc, column3 desc"</p>
	 * @param pagination 分页对象
	 * @param entity ORM中的实体对象实例
	 * @return
	 */
	public static StringBuilder generateOrderbySql(Pagination pagination, Object entity) {
		StringBuilder orderby = new StringBuilder();
		
		scanSorts(orderby, pagination.getPrevSorts(), entity);
		appendOrderby(orderby, entity, pagination.getOrder(), pagination.isAsc());
		scanSorts(orderby, pagination.getNextSorts(), entity);
		
		if(orderby.length() > 0) {
			orderby.deleteCharAt(orderby.length() - 1); //去掉最后多余的逗号
			orderby.insert(0, " order by ");
		}
		
		return orderby;
	}
	
	private static void scanSorts(StringBuilder orderby, List<SortField> sorts, Object entity) {
		if(CollectionUtils.hasElement(sorts)) {
			for (SortField sf : sorts) {
				if(sf != null) {
					appendOrderby(orderby, entity, sf.getOrder(), sf.isAsc());
				}
			}
		}
	}

	private static void appendOrderby(StringBuilder orderby, Object entity, String order, boolean asc) {
		if(StringUtils.isNotBlank(order)) {
			String column = DatabaseUtils.getColumnName(ReflectUtils.getField(order, entity.getClass()), true);
			orderby.append(column).append(" ").append(asc ? "asc" : "desc").append(",");
		}
	}
}
