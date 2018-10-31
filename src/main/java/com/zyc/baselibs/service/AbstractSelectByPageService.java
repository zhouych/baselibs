package com.zyc.baselibs.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.zyc.baselibs.dao.MybatisBaseMapper;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.vo.Pagination;

public abstract class AbstractSelectByPageService extends AbstractBaseService {
	
	private static final Logger logger = Logger.getLogger(AbstractSelectByPageService.class);
	
	private static final String DEBUG_MSG_SELECTBYPAGE_PREFIX = "[selectByPage(...)] - { mapper: %s; condition: %s%s }";
	
	private static final String DEBUG_MSG_SELECTTOTALCOUNT_PREFIX = "[selectTotalCount(...)] - { mapper: %s; condition: %s%s }";
	
	/**
	 * 得到分页查询的当前页的结果集
	 * @param mapper 执行SQL查询的Dao层对象实例
	 * @param condition 查询条件
	 * @param keyword 用于模糊匹配的关键字
	 * @param pagination 当前查询的分页参数对象实例
	 * @return List 结果集
	 */
	protected <T extends BaseEntity> List<T> selectByPage(MybatisBaseMapper<T> mapper, final T condition, final String keyword, Pagination pagination) {
		logger.debug(String.format(DEBUG_MSG_SELECTBYPAGE_PREFIX, mapper, condition, "; keyword: " + keyword));
		return mapper.selectByPageSupportKeyword(condition, keyword, pagination);
	}

	/**
	 * 得到符合分页查询条件的记录总行数
	 * @param mapper 执行SQL查询的Dao层对象实例
	 * @param condition 查询条件
	 * @param keyword 用于模糊匹配的关键字
	 * @param pagination 当前查询的分页参数对象实例
	 * @return int 记录总行数
	 */
	protected <T extends BaseEntity> int selectTotalCount(MybatisBaseMapper<T> mapper, final T condition, final String keyword, Pagination pagination) {
		logger.debug(String.format(DEBUG_MSG_SELECTTOTALCOUNT_PREFIX, mapper, condition, "; keyword: " + keyword));
		return mapper.selectTotalCountSupportKeyword(condition, keyword, pagination);
	}

	/**
	 * 得到分页查询的当前页的结果集
	 * @param mapper 执行SQL查询的Dao层对象实例
	 * @param condition 查询条件
	 * @param pagination 当前查询的分页参数对象实例
	 * @return List 结果集
	 * @deprecated 无法支持关键字搜索，已过时。
	 */
	@Deprecated
	protected <T extends BaseEntity> List<T> selectByPage(MybatisBaseMapper<T> mapper, final T condition, Pagination pagination) {
		logger.debug(String.format(DEBUG_MSG_SELECTBYPAGE_PREFIX, mapper, condition, ""));
		return mapper.selectByPage(condition, pagination);
	}
	
}
