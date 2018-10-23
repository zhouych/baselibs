package com.zyc.baselibs.entities;

/**
 * 可业务化的
 * @author zhouyancheng
 *
 * @param <T>
 */
public interface Businessable<T extends BaseEntity> {
	/**
	 * 判断指定对象与当前对象在业务上是否相等
	 * @param obj
	 * @return
	 */
	boolean businessEquals(T obj);
}
