package com.sevensemesterproject.infoJam.util;

import java.util.Base64;

public class PasswordEncodeDecode {
	
	public  String doEncode(String encodeString) {
		byte[] x = Base64.getEncoder().encode(encodeString.getBytes());
		return  new String(x); 
	}
	
	public String doDecode(String decodeString) {
		byte[] y= Base64.getDecoder().decode(decodeString.getBytes());
		return new String(y);
	}
}
