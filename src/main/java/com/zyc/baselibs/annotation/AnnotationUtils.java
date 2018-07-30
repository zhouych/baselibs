package com.zyc.baselibs.annotation;

import java.lang.reflect.Field;

import com.zyc.baselibs.asserts.AssertThrowNonRuntime;
import com.zyc.baselibs.ex.IllegalValueException;

@Deprecated
public class AnnotationUtils {

	@Deprecated
	public static <T extends Object> void verify(T o) throws IllegalValueException {
		AssertThrowNonRuntime.notNull(o, "The parameter 'o' is null.");

		Field[] fields = o.getClass().getDeclaredFields();
		if(null == fields || fields.length == 0) {
			return;
		}

		boolean access = false; 
		for (Field field : fields) {
			access = field.isAccessible();
			field.setAccessible(true);
			if(field.isAnnotationPresent(Required.class)) {
				Object v = null;
				try {
					v = field.get(o);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				AssertThrowNonRuntime.notNull(v, String.format("The parameter '%s' is null.", field.getName()));
				if(v instanceof String) {
					AssertThrowNonRuntime.hasText(v.toString(), String.format("The parameter '%s' is empty. (%s=%s)", field.getName(), field.getName(), v.toString()));
				}
			}
			field.setAccessible(access);
		}
	}

}
