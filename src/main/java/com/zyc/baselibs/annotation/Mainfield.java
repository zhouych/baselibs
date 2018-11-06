package com.zyc.baselibs.annotation;

/**
 * 主字段
 * @author zhouyancheng
 *
 */
public @interface Mainfield {

	/**
	 * 依赖的副字段列表
	 * @return
	 */
	String[] subfields() default "";
}
