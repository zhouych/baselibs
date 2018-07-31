package com.zyc.baselibs.annotation;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.ex.IllegalValueException;

public class EnumMappingUtils {
	
	private static final Logger logger = Logger.getLogger(EnumMappingUtils.class);

	public static <T extends Object> void verifyEnumField(final T target, final Class<?> compareEnumClass) throws IllegalValueException {
		final StringBuilder message = new StringBuilder();
		
		AnnotationUtils.scanFieldAnnotation(target, EnumMapping.class, new Visitor<Field, Boolean>() {
			@SuppressWarnings("unchecked")
			public Boolean visit(Field field) {
				EnumMapping em = field.getAnnotation(EnumMapping.class);
				boolean invalidClass = !em.enumClazz().isEnum() || em.enumClazz() != compareEnumClass;
				boolean invalidValue = false;
				Object v = null;
				if(!invalidClass) {
					try {
						v = field.get(target);
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(e.getMessage(), e);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e.getMessage(), e);
					}
					
					if(v == null) {
						invalidValue = true;
					} else {
						try {
							Enum.valueOf(em.enumClazz(), String.valueOf(v));
						} catch (Exception e) {
							invalidValue = true;
							logger.warn("[EnumMappingUtils.verifyEnumField()] - " +  e.getMessage());
						}
					}
				}
				
				if(invalidClass || invalidValue) {
					message.append(String.format("The value of this parameter is invalid. (%s=%s)", field.getName(), field.getName(), String.valueOf(v)));
				}
				
				return invalidClass;
			};
		}, true);

		if(message.length() > 0) {
			throw new IllegalValueException(message.toString());
		}
	}
}
