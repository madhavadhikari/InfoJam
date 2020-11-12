package com.sevensemesterproject.infoJam.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	private static Pattern pattern;
	private static Matcher matcher;
	
	public static boolean getEmailValidator(String email) {
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.find();
	}
	
	public static boolean getPhoneValidator(String phone) {
		final String MOBILE_PATTERN = "^\\+?\\d{0,3}[-.\\s]?[9][87]\\d{8}?";
		pattern = Pattern.compile(MOBILE_PATTERN);
		matcher = pattern.matcher(phone);
		return matcher.matches();
	}
}
