package com.zyc.baselibs.annotation;

import java.lang.reflect.Field;

public class SubfieldUtils {
	
	public static Subfield getSubfield(Field field) {
		return field.isAnnotationPresent(Subfield.class) ? field.getAnnotation(Subfield.class) : null;
	}

	public static boolean isSubfield(Field field) {
		return getSubfield(field) != null;
	}
}
