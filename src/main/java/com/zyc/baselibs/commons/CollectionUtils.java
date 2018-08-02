package com.zyc.baselibs.commons;

import java.util.Collection;

public class CollectionUtils {
	
	public static boolean hasElement(Collection<?> collection) {
		return null != collection && collection.size() > 0;
	}
	
	public static boolean hasElement(Object[] array) {
		return null != array && array.length > 0;
	}
}
