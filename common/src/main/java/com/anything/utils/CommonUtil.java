package com.anything.utils;

import java.util.Random;

public class CommonUtil {

	/**
	 * default length 30
	 * 
	 * @return
	 */
	public static String randomStr() {

		return randomStr(30);
	}

	public static String randomStr(int length) {

		int leftLimit = 48;
		int rightLimit = 122;

		return new Random().ints(leftLimit, rightLimit + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
					.limit(length)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
					.toString();
	}
}
