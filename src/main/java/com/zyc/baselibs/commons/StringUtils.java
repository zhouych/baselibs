package com.zyc.baselibs.commons;

import java.util.Random;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    public static final char[] ALPHABETS = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    
    public static String randomAlphabets(int length) {
    	StringBuilder sb = new StringBuilder();
    	
    	for (int i = 0; i < length; i++) {
    		sb.append(ALPHABETS[new Random().nextInt(ALPHABETS.length)]);
		}
    	
    	return sb.toString();
    }
}
