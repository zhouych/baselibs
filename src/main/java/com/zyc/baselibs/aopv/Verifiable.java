package com.zyc.baselibs.aopv;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记Java类在作为方法参数类型时，其对应的实例对象是可被验证的
 * @author zhouyancheng
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Verifiable {
	
	/**
	 * 如果方法参数类型对象实例传入null值，是否忽略该参数，从而不进行校验。
	 * @return  Boolean类型：true - null值不校验; false - 不允许null值。
	 */
	boolean ignoreNull() default false;

	/**
	 * 如果该类型对象实例作为方法参数类型的属性字段时，传入null值，是否忽略该参数，从而不进行校验。
	 * @return  Boolean类型：true - null值不校验; false - 不允许null值。
	 */
	boolean ignoreNullAsProperty() default false;
}
