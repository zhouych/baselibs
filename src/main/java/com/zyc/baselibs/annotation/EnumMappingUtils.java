package com.zyc.baselibs.annotation;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.ex.IllegalValueException;

public class EnumMappingUtils {
	
	private static final Logger logger = Logger.getLogger(EnumMappingUtils.class);

	public static <T extends Object> void verifyEnumField(final T target) throws IllegalValueException {
		final StringBuilder message = new StringBuilder();
		
		AnnotationUtils.scanFieldAnnotation(target, EnumMapping.class, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				EnumMapping em = field.getAnnotation(EnumMapping.class);
				boolean invalidClass = !em.enumClazz().isEnum();
				boolean invalidValue = false;
				Object v = null;
				if(!invalidClass) {
					v = ReflectUtils.getValue(field, target);
					invalidValue = invalidEnumValue(v, em.enumClazz());
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
	
	/**
	 * 判断指定的值是否属于无效的枚举值
	 * @param value 待判断的值
	 * @param clazz 有效枚举的类型
	 * @return Boolean 值是否有效: true - 无效值; false - 有效值.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> boolean invalidEnumValue(Object value, Class<? extends Enum> clazz) {
		if(value == null) {
			return true;
		} else {
			try {
				Enum.valueOf(clazz, String.valueOf(value));
				return false;
			} catch (Exception e) {
				logger.warn("[EnumMappingUtils.verifyEnumField()] - " +  e.getMessage());
				return true;
			}
		}
	}
}
