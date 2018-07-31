package com.zyc.baselibs.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.zyc.baselibs.commons.Visitor;

public class AnnotationUtils {

	@SuppressWarnings("unchecked")
	public static <T extends Object> void scanFieldAnnotation(T target, Class<? extends Annotation> annotationClazz, Visitor<Field, Boolean> visitor, boolean enabledBreak) {
		scan(target, (Class<T>) target.getClass(), annotationClazz, visitor, enabledBreak);
	}
	
	private static <T extends Object> void scan(T target, Class<T> targetClazz, Class<? extends Annotation> annotationClazz, Visitor<Field, Boolean> visitor, boolean enabledBreak) {
		Field[] fields = targetClazz.getDeclaredFields();
		if(null == fields || fields.length == 0) {
			return;
		}
		
		boolean access = false; 
		for (Field field : fields) {
			access = field.isAccessible();
			field.setAccessible(true);
			if(field.isAnnotationPresent(annotationClazz)) {
				if(visitor.visit(field) && enabledBreak) {
					break;
				}
			}
			field.setAccessible(access);
		}
		
		if(null != targetClazz.getSuperclass()) {
			scan(target, targetClazz.getSuperclass(), annotationClazz, visitor, enabledBreak);
		}
	}
}
