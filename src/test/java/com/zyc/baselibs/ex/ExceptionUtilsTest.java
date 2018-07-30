package com.zyc.baselibs.ex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExceptionUtilsTest {

	@Test
	public void uimessageTest() {
		assertEquals(ExceptionUtils.uimessage(new Exception("test")), ExceptionUtils.EX_UNKNOWN_ERROR);
		assertEquals(ExceptionUtils.uimessage(new BussinessException("test")), "test");
		assertEquals(ExceptionUtils.uimessage(new IllegalValueException("test")), "test");
		assertEquals(ExceptionUtils.uimessage(new RuntimeException("test")), ExceptionUtils.EX_UNKNOWN_ERROR);
	}
}
