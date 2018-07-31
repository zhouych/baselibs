package com.zyc.baselibs.annotation;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import com.zyc.baselibs.commons.Counter;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.entities.DataStatus;

public class AnnotationUtilsTest {
	
	public void scanFieldAnnotationTest() {
		A a = new A();
		
		final Counter c = new Counter();
		
		AnnotationUtils.scanFieldAnnotation(a, EntityField.class, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				c.afterPlus();
				return true;
			};
		}, true);
		
		assertEquals(c.get(), 1);
		
		AnnotationUtils.scanFieldAnnotation(a, EntityField.class, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				c.afterPlus();
				return true;
			};
		}, false);
		
		assertEquals(c.get(), 4);
		
		AnnotationUtils.scanFieldAnnotation(a, EnumMapping.class, new Visitor<Field, Boolean>() {
			public Boolean visit(Field field) {
				c.afterPlus();
				return true;
			};
		}, false);
		
		assertEquals(c.get(), 5);
	}
	
	class A {
		@EntityField 
		private String id;
		@EntityField
		private String name;
		@EntityField
		@EnumMapping(enumClazz = DataStatus.class)
		private String status;
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
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
