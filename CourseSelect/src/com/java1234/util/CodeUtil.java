package com.java1234.util;
public class CodeUtil {
	
	public String GenerateCode() {

		String ret = "";
	    for (int i = 0; i < 4; i++) {
	        int randomInt = (int) ((java.lang.Math.random()) * 10);
	        ret = ret.concat(Integer.toString(randomInt));
	        }
	        return ret;
	}

}
