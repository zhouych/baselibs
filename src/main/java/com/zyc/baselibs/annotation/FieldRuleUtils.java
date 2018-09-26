package com.zyc.baselibs.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.ex.IllegalEditedException;
import com.zyc.baselibs.ex.IllegalValueException;

public class FieldRuleUtils {

	public static <T extends Object> void eachEntityField(T target, Visitor<Field, Boolean> visitor) {
		FieldRuleUtils.eachEntityField(target, visitor, false);
	}
	
	public static <T extends Object> void eachEntityField(T target, Visitor<Field, Boolean> visitor, boolean enabledBreak) {
		AnnotationUtils.scanFieldAnnotation(target, FieldRule.class, visitor, enabledBreak);
	}
	
	public static <T extends Object> String[] uneditableFields(T o) throws IllegalEditedException {
		final List<String> uneditableFields = new ArrayList<String>();
		
		FieldRuleUtils.eachEntityField(o, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				FieldRule ef = field.getAnnotation(FieldRule.class);
				if(ef.externalUneditable()) {
					uneditableFields.add(field.getName());
				}
				return true;
			}
		});
		
		return uneditableFields.toArray(new String[uneditableFields.size()]);
	}
	
	public static <T extends Object> void verifyRequired(final T o) throws IllegalValueException {
		final StringBuilder message = new StringBuilder();
		
		FieldRuleUtils.eachEntityField(o, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				FieldRule ef = field.getAnnotation(FieldRule.class);
				if(ef.required()) {
					if(ReflectUtils.isNullOrEmpty(field, o)) {
						message.append(String.format("The parameter '%s' is null or empty.", field.getName()));
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
