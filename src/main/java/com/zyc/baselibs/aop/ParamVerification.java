package com.zyc.baselibs.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.zyc.baselibs.ex.BussinessException;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamVerification {
	
	/**
	 * 待验证对象包含的验证规则
	 * <p>
	 * 表达式: </br>
	 * <code>targetPackage.targetClass:[rulePackage1.ruleClass1,rulePackage2.ruleClass2]</code></br>
	 * 以上表达式分以英文冒号分割左右：</br>
	 * 左边，<code>targetPackage.targetClass</code>=包名.类名，表示待验证目标的类型；</br>
	 * 右边，<code>[rulePackage1.ruleClass1,rulePackage2.ruleClass2]</code>=包名.类名，支持多个，表示待验证目标的验证规则的类型列表。
	 * </p>
	 * @return
	 */
	String[] ruleExpression() default {};
	
	/**
	 * 验证不通过时，将抛出的异常
	 * @return
	 */
	Class<? extends Exception> failed() default BussinessException.class;
}
