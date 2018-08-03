package com.zyc.baselibs.dao;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.vo.Pagination;

public interface MybatisBaseMapper<T extends BaseEntity> {
	
	@InsertProvider(type = MybatisSqlProvider.class, method = "insert")
	int insert(T entity) throws Exception;
	
	@DeleteProvider(type = MybatisSqlProvider.class, method = "delete")
	int delete(T entity) throws Exception;
	
	@UpdateProvider(type = MybatisSqlProvider.class, method = "update")
	int update(T entity) throws Exception;

	@SelectProvider(type = MybatisSqlProvider.class, method = "load")
	T load(@Param("id") String id, @Param("class") Class<T> clazz);
	
	@SelectProvider(type = MybatisSqlProvider.class, method = "select")
	List<T> select(T entity);

	@SelectProvider(type = MybatisSqlProvider.class, method = "selectByPage")
	List<T> selectByPage(@Param(MybatisSqlProvider.PARAM_KEY_ENTITY) T entity, @Param(MybatisSqlProvider.PARAM_KEY_PAGINATION) Pagination pagination);
	
}