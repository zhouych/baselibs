package com.zyc.baselibs.service;

import com.zyc.baselibs.vo.DeleteMode;

public interface EntityDeleteService {

	/**
	 * 删除数据
	 * @param entityId 待删除的实体数据主键ID
	 * @param mode 删除模式：逻辑删除 | 物理删除
	 * @return 删除结果：true - 成功；false - 失败。
	 * @throws Exception
	 */
	boolean delete(String entityId, DeleteMode mode) throws Exception;
	
	/**
	 * 逻辑删除（执行update操作，将实体数据更新为已删除状态）
	 * @param entityId 待删除的实体数据主键ID
	 * @return 删除结果：true - 成功；false - 失败。
	 * @throws Exception
	 */
	boolean deleteOnLogic(String entityId) throws Exception;

	/**
	 * 物理删除（执行delete操作，将实体数据从数据库删除）
	 * @param entityId 待删除的实体数据主键ID
	 * @return 删除结果：true - 成功；false - 失败。
	 * @throws Exception
	 */
	boolean deleteOnPhysical(String entityId) throws Exception;
}
