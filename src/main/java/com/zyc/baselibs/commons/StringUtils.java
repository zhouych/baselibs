package com.zyc.baselibs.commons;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.zyc.baselibs.ex.BussinessException;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    public static final char[] ALPHABETS = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * yyyy-MM-dd hh:mm:ss
	 */
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
    public static String randomAlphabets(int length) {
    	StringBuilder sb = new StringBuilder();
    	
    	for (int i = 0; i < length; i++) {
    		sb.append(ALPHABETS[new Random().nextInt(ALPHABETS.length)]);
		}
    	
    	return sb.toString();
    }
    
    public static <T extends Enum<T>> T toEnum(Class<T> enumType, String name) throws BussinessException {
    	T _enum = null;
    	
    	try {
    		_enum = Enum.valueOf(enumType, name);
		} catch (Exception e) {
			throw new BussinessException("This enumeration value is not supported. (value=" + name + ")", e);
		}
    	
    	return _enum;
    }
	
	public static <T extends Enum<T>> T toEnumIgnoreCase(Class<T> enumType, String value) {
		try {
			return toEnum(enumType, value.toUpperCase());
		} catch (BussinessException e) {
			throw new RuntimeException("This enumeration value is not supported. (value=" + value + ")", e);
		}
	}
    
	static final String COMMASPACE = ", ";

	@SuppressWarnings("rawtypes")
	public static String instanceDetail(Object obj) {
		if(obj == null) {
			return "null";
		}
		Class clazz = obj.getClass();
		StringBuilder detail = new StringBuilder(clazz.getName()).append(" => [ ");
		eachAppendPropertyDetail(obj, clazz, detail);
		String ret = detail.toString();
		if(ret.endsWith(COMMASPACE)) {
			ret = ret.substring(0, ret.lastIndexOf(COMMASPACE));
		}
		//Example: "com.it.ocs.*.ClassName => [ property1=value1, property2=value2 ]"
		return ret + " ]";
	}
	
	@SuppressWarnings("rawtypes")
	private static void eachAppendPropertyDetail(Object obj, Class clazz, StringBuilder propertyDetail) {
		if(clazz == null) {
			return;
		}
		
		Field[] fields = clazz.getDeclaredFields();
		int l = fields == null ? 0 : fields.length;
		if(l > 0) {
			Field f = null;
			Object v = null;
			for (int i = 0; i < l; i++) {
				f = fields[i];
				try {
					f.setAccessible(true);
					v = f.get(obj);
				} catch (Exception e) {
					v = null;
				}
				propertyDetail.append(f.getName()).append("=").append(String.valueOf(v)).append(COMMASPACE);
			}
		}
		eachAppendPropertyDetail(obj, clazz.getSuperclass(), propertyDetail);
	}
	
	/**
	 * 将日期转化为<code>yyyy-MM-dd hh:mm:ss</code>格式的字符串。
	 * @param date
	 * @return
	 */
	public static String fromDate(Date date) {
		return date == null ? null : DATE_FORMAT.format(date);
	}
}
