package com.zyc.baselibs.dao;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.mybatis.SqlProviderFactory;
import com.zyc.baselibs.mybatis.SqlProviderSupport;
import com.zyc.baselibs.vo.Pagination;

public interface MybatisBaseMapper<T extends BaseEntity> {
	
	@InsertProvider(type = SqlProviderFactory.class, method = "insert")
	int insert(T entity) throws Exception;
	
	@DeleteProvider(type = SqlProviderFactory.class, method = "delete")
	int delete(T entity) throws Exception;
	
	@UpdateProvider(type = SqlProviderFactory.class, method = "update")
	int update(T entity) throws Exception;
	
	@SelectProvider(type = SqlProviderFactory.class, method = "load")
	T load(@Param(SqlProviderSupport.PARAM_KEY_ID) String id, @Param(SqlProviderSupport.PARAM_KEY_CLASS) Class<T> clazz);
	
	@SelectProvider(type = SqlProviderFactory.class, method = "select")
	List<T> select(T entity);

	@SelectProvider(type = SqlProviderFactory.class, method = "selectByPage")
	List<T> selectByPage(@Param(SqlProviderSupport.PARAM_KEY_ENTITY) T entity, @Param(SqlProviderSupport.PARAM_KEY_PAGINATION) Pagination pagination);
	
}