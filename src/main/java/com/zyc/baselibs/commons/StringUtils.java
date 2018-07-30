package com.zyc.baselibs.commons;

import java.util.Random;

public class StringUtils {

    public static final String[] ALPHABETS = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    
    public static String randomAlphabets(int length) {
    	StringBuilder sb = new StringBuilder();
    	
    	for (int i = 0; i < length; i++) {
    		sb.append(ALPHABETS[new Random().nextInt(ALPHABETS.length)]);
		}
    	
    	return sb.toString();
    }
}
