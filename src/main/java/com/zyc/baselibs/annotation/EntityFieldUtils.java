package com.zyc.baselibs.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.ex.IllegalEditedException;
import com.zyc.baselibs.ex.IllegalValueException;

public class EntityFieldUtils {

	public static <T extends Object> void eachEntityField(T target, Visitor<Field, Boolean> visitor) {
		EntityFieldUtils.eachEntityField(target, visitor, false);
	}
	
	public static <T extends Object> void eachEntityField(T target, Visitor<Field, Boolean> visitor, boolean enabledBreak) {
		AnnotationUtils.scanFieldAnnotation(target, EntityField.class, visitor, enabledBreak);
	}
	
	public static <T extends Object> String[] uneditableFields(T o) throws IllegalEditedException {
		final List<String> uneditableFields = new ArrayList<String>();
		
		EntityFieldUtils.eachEntityField(o, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				EntityField ef = field.getAnnotation(EntityField.class);
				if(ef.uneditable()) {
					uneditableFields.add(field.getName());
				}
				return true;
			}
		});
		
		return uneditableFields.toArray(new String[uneditableFields.size()]);
	}
	
	public static <T extends Object> void verifyRequired(final T o) throws IllegalValueException {
		final StringBuilder message = new StringBuilder();
		
		EntityFieldUtils.eachEntityField(o, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				EntityField ef = field.getAnnotation(EntityField.class);
				if(ef.required()) {
					Object v = null;
					try {
						v = field.get(o);
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(e.getMessage(), e);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e.getMessage(), e);
					}
					
					if(null == v || v instanceof String && !StringUtils.hasText(v.toString())) {
						message.append(String.format("The parameter '%s' is null or empty. (%s=%s)", field.getName(), field.getName(), String.valueOf(v)));
						return true;
					}
				}
				return false;
			}
		}, true);
		
		if(message.length() > 0) {
			throw new IllegalValueException(message.toString());
		}
	}
}
