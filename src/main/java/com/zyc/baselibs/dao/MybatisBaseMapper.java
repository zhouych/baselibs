package com.zyc.baselibs.dao;

import java.util.List;
import java.util.Map;

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
	
	int deleteById(@Param(SqlScriptProviderSupport.PKEY_ID) String id, @Param(SqlScriptProviderSupport.PKEY_VERSION) int version, @Param(SqlScriptProviderSupport.PKEY_CLASS) Class<T> clazz);

	@UpdateProvider(type = SqlScriptCommander.class, method = "update")
	int update(T entity) throws Exception;
	
	@SelectProvider(type = SqlScriptCommander.class, method = "load")
	T load(@Param(SqlScriptProviderSupport.PKEY_ID) String id, @Param(SqlScriptProviderSupport.PKEY_CLASS) Class<T> clazz);
	
	@SelectProvider(type = SqlScriptCommander.class, method = "select")
	List<T> select(T entity);

	@SelectProvider(type = SqlScriptCommander.class, method = "whereIn")
	List<T> whereIn(@Param(SqlScriptProviderSupport.PKEY_FIELD2VALUES) Map<String, Object> field2values, @Param(SqlScriptProviderSupport.PKEY_CLASS) Class<T> clazz);
	
	@SelectProvider(type = SqlScriptCommander.class, method = "selectSupportKeyword")
	List<T> selectSupportKeyword(@Param(SqlScriptProviderSupport.PKEY_ENTITY) T entity, @Param(SqlScriptProviderSupport.PKEY_KEYWORD) String keyword);

	@SelectProvider(type = SqlScriptCommander.class, method = "selectByPage")
	List<T> selectByPage(@Param(SqlScriptProviderSupport.PKEY_ENTITY) T entity, @Param(SqlScriptProviderSupport.PKEY_PAGINATION) Pagination pagination);
	
	@SelectProvider(type = SqlScriptCommander.class, method = "selectByPageSupportKeyword")
	List<T> selectByPageSupportKeyword(@Param(SqlScriptProviderSupport.PKEY_ENTITY) T entity, @Param(SqlScriptProviderSupport.PKEY_KEYWORD) String keyword, @Param(SqlScriptProviderSupport.PKEY_PAGINATION) Pagination pagination);
	
	@SelectProvider(type = SqlScriptCommander.class, method = "selectTotalCountSupportKeyword")
	int selectTotalCountSupportKeyword(@Param(SqlScriptProviderSupport.PKEY_ENTITY) T entity, @Param(SqlScriptProviderSupport.PKEY_KEYWORD) String keyword, @Param(SqlScriptProviderSupport.PKEY_PAGINATION) Pagination pagination);
	
}