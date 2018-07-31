package com.zyc.baselibs.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StringUtilsTest {
	
	private static String ALL_ALPHABET = null;
	
	@Before
	public void init() {
		StringBuilder sb = new StringBuilder();
		for (char alphabet : StringUtils.ALPHABETS) {
			sb.append(alphabet);
		}
		ALL_ALPHABET = sb.toString();
	}
	
	@Test
	public void randomAlphabetsTest() {
		String words = StringUtils.randomAlphabets(10);
		assertEquals(words.length(), 10);
		for (int i = 0; i < words.length(); i++) {
			char c = words.charAt(i);
			assertEquals(ALL_ALPHABET.indexOf(String.valueOf(c)) >= 0, true);
		}
	}
	
}
