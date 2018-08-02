package com.zyc.baselibs.commons;

import java.lang.reflect.Field;

public class ReflectUtils {
	
	public static void scanFields(Class<?> clazz, Visitor<Field, Boolean> visitor, boolean enabledBreak) {
		if(null == clazz) {
			return;
		}
		
		boolean breakFlag = false;
		
		Field[] fields = clazz.getDeclaredFields();
		if(null != fields && fields.length > 0) {
			boolean access = false; 
			for (Field field : fields) {
				access = field.isAccessible();
				field.setAccessible(true);
				if(visitor.visit(field) && enabledBreak) {
					breakFlag = true;
					break;
				}
				field.setAccessible(access);
			}
		}
		
		if(!breakFlag) {
			scanFields(clazz.getSuperclass(), visitor, enabledBreak);	
		}
	}

	public static void scanFields(Class<?> clazz, final Visitor<Field, Boolean> visitor, final boolean enabledBreak, final int[] excludeModifiers) {
		ReflectUtils.scanFields(clazz, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				//逻辑：先确保字段没有指定的修饰符，再确保访客的目的达到（visitor.visit函数得到执行)，最后再看是否启用break关键字。
				//目的：根据以上三个因素决定是否要执行break操作。
				return !ReflectUtils.isModified(field, excludeModifiers) && visitor.visit(field) && enabledBreak;
			}
		}, enabledBreak);
	}
	
	public static boolean isModified(Field field, int[] modifiers) {
		if(null == modifiers || modifiers.length == 0) {
			return false;
		}
		
		for (int modifier : modifiers) {
			if((field.getModifiers() & modifier) != 0) {
				return true;
			}
		}
		
		return false;
	}
}
