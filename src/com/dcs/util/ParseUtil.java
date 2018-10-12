package com.dcs.util;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;

public class ParseUtil {
	public static final int defaultValue = 0;
	
	public static short parseShort(String s) {
		if (s!=null)
			try {
				return Short.parseShort(s);
			} catch(NumberFormatException e) {
				return defaultValue;
			}
		else 
			return defaultValue;
	}
	
	public static int parseInt(String s) {
		if (s!=null) 
			try {
				if (s.indexOf(".")>0)
					return Integer.parseInt(s.substring(0, s.indexOf(".")));
				else
					return Integer.parseInt(s);
			} catch(NumberFormatException e) {
				return defaultValue;
			}
		else 
			return defaultValue;
	}
	
	public static long parseLong(String s) {
		if (s!=null) 
			try {
				return Long.parseLong(s);
			} catch(NumberFormatException e) {
				return defaultValue;
			}
		else 
			return defaultValue;
	}
	
	public static double parseDouble(String s) {
		if (s!=null) 
			try {
				return Double.parseDouble(s);
			} catch(NumberFormatException e) {
				return defaultValue;
			}
		else 
			return defaultValue;
	}
	
	public static float parseFloat(String s) {
		if (s!=null) 
			try {
				return Float.parseFloat(s);
			} catch(NumberFormatException e) {
				return defaultValue;
			}			
		else 
			return defaultValue;
	}
	
	public static String parseString(String s) {
		if (s!=null) 
			return s;
		else 
			return "";
	}
	public static boolean parseBoolean(String s) {
		if ("true".equals(s)) 
			return true;
		else 
			return false;
	}
	public static Date parseDate(String s) {
		return parseDate(parseLong(s));
	}
	
	public static Date parseDate(long l) {
		return new Date(l);
	}
	
	public static String toString(int i, int d) {
		String s = String.valueOf(i);
		while(s.length()<d) {
			s = "0"+s;
		}
		return s;
	}
	
	public static String[] parseString(Collection<Object> c, String n) {
		String[] result = new String[c.size()];
		Iterator<Object> i = c.iterator();
		int j=0;
		try {
			while(i.hasNext()) {
				Object obj = i.next();
				result[j] = (String) PropertyUtils.getProperty(obj, n);
				j++;
			}
		} catch (Exception ex) {}
		return result;
	}
	
	public static boolean[] parseBoolean(Collection<Object> c, String n) {
		boolean[] result = new boolean[c.size()];
		Iterator<Object> i = c.iterator();
		int j=0;
		try {
			while(i.hasNext()) {
				Object obj = i.next();
				result[j] = (Boolean) PropertyUtils.getProperty(obj, n);
				j++;
			}
		} catch (Exception ex) {}
		return result;
	}
	
	public static long[] parseLong(Collection<Object> c, String n) {
		long[] result = new long[c.size()];
		Iterator<Object> i = c.iterator();
		int j=0;
		try {
			while(i.hasNext()) {
				Object obj = i.next();
				result[j] = (Long) PropertyUtils.getProperty(obj, n);
				j++;
			}
		} catch (Exception ex) {}
		return result;
	}

}
