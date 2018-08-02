package com.zyc.baselibs.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;

public class AnnotationUtils {

	@SuppressWarnings("unchecked")
	public static <T extends Object> void scanFieldAnnotation(T target, Class<? extends Annotation> annotationClazz, Visitor<Field, Boolean> visitor, boolean enabledBreak) {
		scan(target, (Class<T>) target.getClass(), annotationClazz, visitor, enabledBreak);
	}
	
	private static <T extends Object> void scan(T target, Class<T> targetClazz, final Class<? extends Annotation> annotationClazz, final Visitor<Field, Boolean> visitor, final boolean enabledBreak) {
		ReflectUtils.scanFields(targetClazz, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				//逻辑：先确保是指定的Annotation，再确保访客的目的达到（visitor.visit函数得到执行)，最后再看是否启用break关键字。
				//目的：根据以上三个因素决定是否要执行break操作。
				return field.isAnnotationPresent(annotationClazz) && visitor.visit(field) && enabledBreak;
			}
		}, enabledBreak);
	}
}
