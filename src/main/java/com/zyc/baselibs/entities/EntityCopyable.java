package com.zyc.baselibs.entities;

/**
 * 实体可复制的
 * @author zhouych
 *
 */
public interface EntityCopyable<T extends BaseEntity> {
	
	/**
	 * 将实现当前对象转为继承于{@link BaseEntity}类的对象实例。
	 * @return
	 */
	T copyEntity();
}
