package com.zyc.baselibs.commons;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class ReflectUtilsTest {
	
	@Test
	public void clazzInstanceTest() {
		
		B b = clazzInstance("com.zyc.baselibs.commons.B", new Object[] { "a", "b", new Integer("1").intValue(), new Date() });
		System.out.println(b.toString());
		
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
				for (Constructor<?> constructor : clazz.getConstructors()) {
					if(matchParameterTypes(args, getPureParameterTypes(constructor, clazz))) {
						parameterMismatch = false;
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
	
	/**
	 * 获取纯属于构造器的参数类型列表。</br>
	 * <p>
	 * 逻辑:</br>
	 * 有可能构造器所属类是一个内部类，内部类的构造器<code>getParameterTypes</code>函数取到的参数类型列表会包含外部类的类型。
	 * 这种情况下，需要将外部类的类型剔除掉，得到纯属于构造器的参数类型列表。
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

class B {
	private String b;
	private int count;
	private Date date;

	public void setDate(Date date) {
		this.date = date;
	}

	public B() {
	}

	public B(String a, String b, int count, Date date) {
		this.b = b;
		this.count = count;
		this.date = date;
	}

	public B(String a, String b, int count) {
		super();
		this.b = b;
		this.count = count;
		this.date = new Date();
	}

	public B(Date date, String a, String b, int count) {
		super();
		this.b = b;
		this.count = count;
		this.date = date;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getDate() {
		return date;
	}
}