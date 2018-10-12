package com.dcs.util;

public class HexUtil {
	public static byte[] hexToByte(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
	
	public static String byteToHex(byte[] b) {
		String result = "";
		for (int i=0; i < b.length; i++) {
			result +=
			Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}
	
	public static String byteToHex(byte[] b, int x) {
		String result = "";
		for (int i=0; i < x; i++) {
			result +=
			Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}
}
