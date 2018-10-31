package com.zyc.baselibs.aopv;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.zyc.baselibs.annotation.EnumMapping;
import com.zyc.baselibs.annotation.EnumMappingUtils;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.commons.ReflectUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.ex.IllegalValueException;

/**
 * 校验规则的实现：用于针对实例对象进行全面（例如：<code>FieldRule</code>、<code>EnumMapping</code>等等）的检查。
 * @author zhouyancheng
 *
 */
public class OverallVerificationRuler implements VerificationRuler {
	
	private static final String FORMAT_EX_NULLOREMPTY = "The parameter '%s' is null or empty. (%s.%s=%s)";
	
	private static final String FORMAT_EX_INVALID = "The parameter '%s' is invalid. (%s.%s=%s)";
	
	/**
	 * 在同一个线程内，记录对象实例的地址，判断对象是否同一个引用的凭据
	 */
	private static final ThreadLocal<List<String>> sameReferenceCredential = new ThreadLocal<List<String>>();
	
	public void execute(final Object obj) throws Exception {
		if(sameReferenceCredential.get() == null) {
			sameReferenceCredential.set(new ArrayList<String>());
		}
		
		final OverallVerificationRuler that = this;
		final StringBuilder message = new StringBuilder();
		
		ReflectUtils.scanFields(obj.getClass(), new Visitor<Field, Boolean>() {
			@SuppressWarnings("unchecked")
			public Boolean visit(Field field) {
				Object value = ReflectUtils.getValue(field, obj);
				
				if(field.isAnnotationPresent(FieldRule.class)) {
					FieldRule ef = field.getAnnotation(FieldRule.class);
					if(ef.required()) {
						if(value == null || StringUtils.isBlank(value.toString())) {
							message.append(String.format(FORMAT_EX_NULLOREMPTY, field.getName(), obj.getClass().getName(), field.getName(), String.valueOf(value)));
							return true;
						}
					}
				}
				
				if(field.isAnnotationPresent(EnumMapping.class)) {
					EnumMapping em = field.getAnnotation(EnumMapping.class);
					boolean invalidValue = EnumMappingUtils.invalidEnumValue(value, em.enumClazz());
					if(invalidValue) {
						message.append(String.format(FORMAT_EX_INVALID, field.getName(), obj.getClass().getName(), field.getName(), String.valueOf(value)));
						return true;
					}
				}
				
				if(value != null) {
					Class<?> fieldType = field.getType();
					Collection<Object> collection = null; 
					if(Collection.class.isAssignableFrom(fieldType)) {
						collection = (Collection<Object>) value;
					} else if(fieldType.isArray()) {
						collection = Arrays.asList((Object[]) value);
					} else {
						collection = new ArrayList<Object>(1);
						collection.add(value);
					}
					
					for (Object item : collection) {
						if(item ==null) {
							// ...
						} else {
							if(item.getClass().isAnnotationPresent(Verifiable.class)) {
								try {
									if(sameReferenceCredential.get().contains(item.toString())) {
										throw new ClassCircularityError("对象实例的引用自身作为其属性值时，执行规则校验会导致无限递归。");
									} else {
										sameReferenceCredential.get().add(item.toString());
									}
									that.execute(item);
								} catch (Exception e) {
									message.append(e.getMessage());
									break;
								}
							}
						}
					}
				}
				return message.length() > 0;
			}
		}, true);

		if(message.length() > 0) {
			throw new IllegalValueException(message.toString());
		}
	}
}
