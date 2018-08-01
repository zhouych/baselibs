package com.zyc.baselibs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityField {
	/**
	 * 标记字段属于外部输入必填项，默认<code>false</code>（即非必填）。
	 */
	boolean required() default false;
	
	/**
	 * 标记字段是否禁止外部编辑，默认<code>false</code>（即可编辑）。
	 */
	boolean uneditable() default false;
}
