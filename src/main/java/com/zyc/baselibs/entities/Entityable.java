package com.zyc.baselibs.entities;

/**
 * 可实体化的
 * @author zhouych
 *
 */
public interface Entityable<T extends BaseEntity> {
	
	/**
	 * 将实现当前对象转为继承于{@link BaseEntity}类的对象实例。
	 * @return
	 */
	T toEntity();
}
