package com.dcs.util;

public class RegExp {
	public static final String PositiveIntegers = "^[0-9]+$";
	public static final String NegativeIntegers = "^-[0-9]+$";
	public static final String Integer = "^-{0,1}[0-9]+$";
	public static final String PositiveNumber = "^[0-9]*\\.{0,1}[0-9]+$";
	public static final String NegativeNumber = "^-[0-9]*\\.{0,1}[0-9]+$";
	public static final String Number = "^-{0,1}[0-9]*\\.{0,1}[0-9]+$";
	public static final String Email = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
}
