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
	
	@InsertProvider(type = SqlScriptCommander.class, method = "insert")
	int insert(T entity) throws Exception;
	
	@DeleteProvider(type = SqlScriptCommander.class, method = "delete")
	int delete(T entity) throws Exception;

	@UpdateProvider(type = SqlScriptCommander.class, method = "update")
	int update(T entity) throws Exception;
	
	@SelectProvider(type = SqlScriptCommander.class, method = "load")
	T load(@Param(SqlProviderSupport.PARAM_KEY_ID) String id, @Param(SqlProviderSupport.PARAM_KEY_CLASS) Class<T> clazz);
	
	@SelectProvider(type = SqlScriptCommander.class, method = "select")
	List<T> select(T entity);
	
	@SelectProvider(type = SqlScriptCommander.class, method = "selectSupportKeyword")
	List<T> selectSupportKeyword(@Param(SqlProviderSupport.PARAM_KEY_ENTITY) T entity, @Param(SqlProviderSupport.PARAM_KEY_KEYWORD) String keyword);

	@SelectProvider(type = SqlScriptCommander.class, method = "selectByPage")
	List<T> selectByPage(@Param(SqlProviderSupport.PARAM_KEY_ENTITY) T entity, @Param(SqlProviderSupport.PARAM_KEY_PAGINATION) Pagination pagination);
	
	@SelectProvider(type = SqlScriptCommander.class, method = "selectByPageSupportKeyword")
	List<T> selectByPageSupportKeyword(@Param(SqlProviderSupport.PARAM_KEY_ENTITY) T entity, @Param(SqlProviderSupport.PARAM_KEY_KEYWORD) String keyword, @Param(SqlProviderSupport.PARAM_KEY_PAGINATION) Pagination pagination);
	
}