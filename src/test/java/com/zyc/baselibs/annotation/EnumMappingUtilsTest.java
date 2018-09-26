package com.zyc.baselibs.annotation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zyc.baselibs.entities.DataStatus;
import com.zyc.baselibs.ex.IllegalValueException;

public class EnumMappingUtilsTest {
	
	@Test
	public void verifyEnumFieldTest() {
		boolean flag = false;
		
		E e = new E("1", "abc", "asdf");
		
		try {
			EnumMappingUtils.verifyEnumField(e);
			flag = true;
		} catch (Exception ex) {
			assertEquals(ex instanceof IllegalValueException, true);
		}
		
		assertEquals(flag, false);
		
		e.setStatus(DataStatus.ENABLED.getText());

		try {
			EnumMappingUtils.verifyEnumField(e);
			flag = true;
		} catch (Exception ex) {
			assertEquals(ex instanceof IllegalValueException, true);
		}
		
		assertEquals(flag, false);

		e.setStatus(DataStatus.ENABLED.toString());

		try {
			EnumMappingUtils.verifyEnumField(e);
			flag = true;
		} catch (Exception ex) {
			assertEquals(ex instanceof IllegalValueException, true);
		}
		
		assertEquals(flag, true);
		

		e.setStatus(DataStatus.DELETED.toString());

		try {
			EnumMappingUtils.verifyEnumField(e);
			flag = true;
		} catch (Exception ex) {
			assertEquals(ex instanceof IllegalValueException, true);
		}
		
		assertEquals(flag, true);
	}
	
	class E {
		private String id;
		private String name;
		@EnumMapping(enumClazz = DataStatus.class)
		private String status;
		
		public E() {
			
		}
		
		public E(String id, String name, String status) {
			this.id = id;
			this.name = name;
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
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	}
}
