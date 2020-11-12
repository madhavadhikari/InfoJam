package com.sevensemesterproject.infoJam.util;

import java.security.SecureRandom;

public class RandomNumber {
	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom randomnumber = new SecureRandom();

	public static String randomString(final int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(AB.charAt(randomnumber.nextInt(AB.length())));
		return sb.toString();
	}
}
