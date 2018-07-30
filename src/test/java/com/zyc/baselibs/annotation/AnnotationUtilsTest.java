package com.zyc.baselibs.annotation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zyc.baselibs.ex.IllegalValueException;

public class AnnotationUtilsTest {
	
	@Test
	public void verifyTest() {
		boolean flag = false;
		
		A a = new A();
		
		try {
			AnnotationUtils.verify(a);
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException || e instanceof RuntimeException, true);
		}
		
		assertEquals(flag, false);
		
		a.setId("1");

		try {
			AnnotationUtils.verify(a);
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException || e instanceof RuntimeException, true);
		}
		
		assertEquals(flag, false);
		
		a.setName("admin");

		try {
			AnnotationUtils.verify(a);
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException || e instanceof RuntimeException, true);
		}
		
		assertEquals(flag, true);
	}
	
	class A {
		@Required private String id;
		
		@Required private String name;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
