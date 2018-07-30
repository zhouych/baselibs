package com.zyc.baselibs.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.zyc.baselibs.asserts.AssertThrowNonRuntime;
import com.zyc.baselibs.ex.IllegalEditedException;
import com.zyc.baselibs.ex.IllegalValueException;

public class AnnotationUtils {
	
	public static <T extends Object> String[] uneditableFields(T o) throws IllegalEditedException {
		Assert.notNull(o, "The parameter 'o' is null.");

		Field[] fields = o.getClass().getDeclaredFields();
		if(null == fields || fields.length == 0) {
			return null;
		}
		
		List<String> uneditableFields = new ArrayList<String>();
		
		boolean access = false; 
		for (Field field : fields) {
			access = field.isAccessible();
			field.setAccessible(true);
			if(field.isAnnotationPresent(EntityField.class)) {
				EntityField ef = field.getAnnotation(EntityField.class);
				if(ef.uneditable()) {
					uneditableFields.add(field.getName());
				}
			}
			field.setAccessible(access);
		}
		
		return uneditableFields.toArray(new String[uneditableFields.size()]);
	} 
	
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
