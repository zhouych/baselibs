package com.zyc.baselibs.commons;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
	
	public static Field getField(String name, Class<?> clazz) {
		Field field = null;
		
		try {
			field = clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("[ReflectUtils.getField()] - " + e.getMessage(), e);
		} catch (SecurityException e) {
			throw new RuntimeException("[ReflectUtils.getField()] - " + e.getMessage(), e);
		}
		
		return field;
	}
	
	public static Object getValue(Field field, Object target) {
		Object value = null;
		try {
			value = field.get(target);
		} catch (Exception e) {
			throw new RuntimeException("[ReflectUtils.getValue()] - " + e.getMessage(), e);
		}
		return value;
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

	@SuppressWarnings("unchecked")
	public static <T extends Object> T clazzInstance(String className, Object ... args) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		Object ins = null;
		
		try {
			if(CollectionUtils.hasElement(args)) {
				boolean parameterMismatch = true;
				for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
					if(matchParameterTypes(args, getPureParameterTypes(constructor, clazz))) {
						parameterMismatch = false;
						if(clazz.isMemberClass()) {
							List<Object> temp = new ArrayList<Object>();
							for (Object o : args) {
								temp.add(o);
							}
							temp.add(0, instanceOuterClass(clazz, true));
							args = temp.toArray();
						}
						ins = constructor.newInstance(args);
						break;
					}
				}
				
				if(parameterMismatch) {
					throw new RuntimeException("Parameter mismatch, instantiation failed.");
				}
			} else {
				ins = clazz.newInstance();
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		return (T) ins;
	}
	
	private static Object instanceOuterClass(Class<?> outerClazz, boolean triggerSource) throws Exception {
		if(!outerClazz.isMemberClass()) {
			return triggerSource ? null : outerClazz.newInstance();
		}

		Object ins = null;
		Constructor<?> constructor = null;
		boolean parameterMismatch = true;
		
		for (Constructor<?> outerConstructor : outerClazz.getDeclaredConstructors()) {
			Class<?>[] paramTypeClazzs = outerConstructor.getParameterTypes();
			if(null != paramTypeClazzs && paramTypeClazzs.length == 1) {
				parameterMismatch =false;
				ins = instanceOuterClass(paramTypeClazzs[0], false);
				constructor = outerConstructor;
				break;
			}
		}
		
		if(parameterMismatch) {
			throw new RuntimeException("Internal class parameter mismatch, instantiation failed.");
		}

		return triggerSource ? ins : constructor.newInstance(new Object[] { ins });
	}
	
	/**
	 * 获取纯属于构造器的参数类型列表。</br>
	 * <p>
	 * 逻辑:</br>
	 * 有可能构造器所属类是一个内部类，内部类的构造器<code>getParameterTypes</code>函数取到的参数类型列表会包含外部类的类型。
	 * 这种情况下，将外部类的类型剔除掉，得到纯属于构造器的参数类型列表。
	 * </p>
	 * @param constructor 构造器实例对象
	 * @return 参数类型列表
	 */
	private static Class<?>[] getPureParameterTypes(Constructor<?> constructor, Class<?> clazz) {
		Class<?>[] types = constructor.getParameterTypes();
		if(!CollectionUtils.hasElement(types)) {
			return null;
		}
		
		List<Class<?>> pures = new ArrayList<Class<?>>();
		for (Class<?> type : types) {
			if(!type.isMemberClass() && !clazz.getName().startsWith(type.getName())) {
				pures.add(type);
			}
		}
		
		return pures.toArray(new Class<?>[pures.size()]);
	}
	
	/**
	 * 参数类型匹配。
	 * <p>
	 * 逻辑：</br>
	 * 将作为比较对象的参数类型数组基于数组下标与传入的参数值数组对应的元素的类型进行比较。</br>
	 * 如果参数值数组中的元素存在null现象，基于该下标索引将会被忽略，不进行比较了。</br>
	 * </p>
	 * @param args 参数值数组
	 * @param compareParameterTypes 作为比较对象的参数类型数组
	 * @return 是否匹配：true - 匹配；false - 不匹配
	 */
	private static boolean matchParameterTypes(Object[] args, Class<?>[] compareParameterTypes) {
		if(null == args && null == compareParameterTypes) {
			return true;
		}
		
		if(args.length != compareParameterTypes.length) {
			return false;
		}
		
		Class<?> formal, actual;
		for (int i = 0, l = compareParameterTypes.length; i < l; i++) {
			if(null == args[i]) {
				continue;
			}
			
			formal = compareParameterTypes[i];
			actual = args[i].getClass();
			
			if(!formal.getName().equals(actual.getName()) && !matchPrimitiveType(formal, actual)) {
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean matchPrimitiveType(Class<?> formalParamType, Class<?> actualParamType) {
		return (formalParamType == byte.class && (actualParamType == byte.class || actualParamType == Byte.class))
				|| (formalParamType == short.class && (actualParamType == short.class || actualParamType == Short.class))
				|| (formalParamType == int.class && (actualParamType == int.class || actualParamType == Integer.class))
				|| (formalParamType == long.class && (actualParamType == long.class || actualParamType == Long.class))
				|| (formalParamType == char.class && (actualParamType == char.class || actualParamType == Character.class))
				|| (formalParamType == float.class && (actualParamType == float.class || actualParamType == Float.class))
				|| (formalParamType == double.class && (actualParamType == double.class || actualParamType == Double.class))
				|| (formalParamType == boolean.class && (actualParamType == boolean.class || actualParamType == Boolean.class));
	}
}
