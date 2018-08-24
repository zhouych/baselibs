package com.zyc.baselibs.dao;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.mybatis.SqlActionCommander;
import com.zyc.baselibs.mybatis.SqlProviderSupport;
import com.zyc.baselibs.vo.Pagination;

public interface MybatisBaseMapper<T extends BaseEntity> {
	
	@InsertProvider(type = SqlActionCommander.class, method = "insert")
	int insert(T entity) throws Exception;
	
	@DeleteProvider(type = SqlActionCommander.class, method = "delete")
	int delete(T entity) throws Exception;

	@UpdateProvider(type = SqlActionCommander.class, method = "update")
	int update(T entity) throws Exception;
	
	@SelectProvider(type = SqlActionCommander.class, method = "load")
	T load(@Param(SqlProviderSupport.PARAM_KEY_ID) String id, @Param(SqlProviderSupport.PARAM_KEY_CLASS) Class<T> clazz);
	
	@SelectProvider(type = SqlActionCommander.class, method = "select")
	List<T> select(T entity);

	@SelectProvider(type = SqlActionCommander.class, method = "selectByPage")
	List<T> selectByPage(@Param(SqlProviderSupport.PARAM_KEY_ENTITY) T entity, @Param(SqlProviderSupport.PARAM_KEY_PAGINATION) Pagination pagination);
	
}