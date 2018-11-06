package com.zyc.baselibs.annotation;

import java.lang.reflect.Field;

public class MainfieldUtils {

	public static Mainfield getMainfield(Field field) {
		return field.isAnnotationPresent(Mainfield.class) ? field.getAnnotation(Mainfield.class) : null;
	}

	public static boolean isMainfield(Field field) {
		return getMainfield(field) != null;
	}
}
