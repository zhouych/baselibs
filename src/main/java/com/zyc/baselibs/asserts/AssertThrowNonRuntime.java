package com.zyc.baselibs.asserts;

import org.springframework.util.Assert;

import com.zyc.baselibs.ex.IllegalValueException;

public class AssertThrowNonRuntime {
	
	public static void notNull(Object object, String message) throws IllegalValueException {
		try {
			Assert.notNull(object, message);
		} catch (IllegalArgumentException e) {
			throw new IllegalValueException(e.getMessage(), e);
		}
	}
	
	public static void hasText(String text, String message) throws IllegalValueException {
		try {
			Assert.hasText(text, message);
		} catch (IllegalArgumentException e) {
			throw new IllegalValueException(e.getMessage(), e);
		}
	}
}
