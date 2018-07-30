package com.zyc.baselibs.asserts;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zyc.baselibs.ex.IllegalValueException;

public class AssertThrowNonRuntimeTest {
	
	@Test
	public void notNullTest() {
		Object obj = null;
		boolean flag = false;
		try {
			AssertThrowNonRuntime.notNull(obj, "The parameter 'obj' is null.");
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException, true);
		}
		
		assertEquals(flag, false);
		
		obj = new Object();
		
		try {
			AssertThrowNonRuntime.notNull(obj, "The parameter 'obj' is null.");
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException, true);
		}
		
		assertEquals(flag, true);
	}
	
	@Test
	public void hasTextTest() {
		String id = null;
		boolean flag = false;
		try {
			AssertThrowNonRuntime.hasText(id, "The parameter 'id' is empty.");
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException, true);
		}
		
		assertEquals(flag, false);
		
		id = "";
		
		try {
			AssertThrowNonRuntime.hasText(id, "The parameter 'id' is empty.");
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException, true);
		}
		
		assertEquals(flag, false);

		id = " ";
		
		try {
			AssertThrowNonRuntime.hasText(id, "The parameter 'id' is empty.");
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException, true);
		}
		
		assertEquals(flag, false);
		
		id = "   ";
		
		try {
			AssertThrowNonRuntime.hasText(id, "The parameter 'id' is empty.");
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException, true);
		}
		
		assertEquals(flag, false);

		id = " id ";
		
		try {
			AssertThrowNonRuntime.hasText(id, "The parameter 'id' is empty.");
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException, true);
		}
		
		assertEquals(flag, true);
	}
}
