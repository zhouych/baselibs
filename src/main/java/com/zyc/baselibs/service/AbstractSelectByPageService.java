package com.zyc.baselibs.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.zyc.baselibs.dao.MybatisBaseMapper;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.vo.Pagination;
public abstract class AbstractSelectByPageService extends AbstractBaseService {
	
	private static final Logger logger = Logger.getLogger(AbstractSelectByPageService.class);
	
	private static final String DEBUG_MSG_COMMONSELECTBYPAGE_PREFIX = "[AbstractSelectByPageService.selectByPage(...)] - { mapper: %s, condition: %s%s }";
	
	protected <T extends BaseEntity> List<T> selectByPage(MybatisBaseMapper<T> mapper, final T condition, final String keyword, Pagination pagination) {
		if(logger.isDebugEnabled()) {
			logger.debug(String.format(DEBUG_MSG_COMMONSELECTBYPAGE_PREFIX, mapper, condition, ", keyword: " + keyword));
		}
		return mapper.selectByPageSupportKeyword(condition, keyword, pagination);
	}

	protected <T extends BaseEntity> List<T> selectByPage(MybatisBaseMapper<T> mapper, final T condition, Pagination pagination) {
		if(logger.isDebugEnabled()) {
			logger.debug(String.format(DEBUG_MSG_COMMONSELECTBYPAGE_PREFIX, mapper, condition, ""));
		}
		return mapper.selectByPage(condition, pagination);
	}
	
}
