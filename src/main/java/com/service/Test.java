package com.service;

import java.util.Arrays;

public class Test {
	public static void main(String args[]) {
		String fieldJson="tj";
		String sg[] = new String[3];
		for (int j = 0; j < 3; j++) {
		
		
			// fieldarray.put(fieldJson);
			sg[j] = fieldJson;
			System.out.println("fieldJson:: string"+fieldJson);
		}
		System.out.println("Arrays:: string"+Arrays.toString(sg));
		
	}

}
