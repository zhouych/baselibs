package com.zyc.baselibs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseColumn {
	String name() default "";
	/**
	 * 主键字段标识
	 * @return
	 */
	boolean pk() default false;
	/**
	 * 版本字段标识
	 * @return
	 */
	boolean version() default false;
	/**
	 * 给标记的字段定义一个无效值，只支持String/int/double等等基本类型。</br>
	 * 默认为空字符串，表示无效值为null或者空字符串。</br>
	 * 如果遇到非字符串的其他类型要定义无效值，需要定义成转为String类型之后的值
	 * @see 通常情况下，int/double等数据类型没有null来表示一个无效的值，这时候可以用到当前注解该特性
	 * @return
	 */
	String invalidValue() default "";
	
	String jdbcType() default "";
	
	boolean nullable() default true;
}
