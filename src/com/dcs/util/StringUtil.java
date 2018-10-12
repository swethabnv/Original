package com.dcs.util;

import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;

public class StringUtil {
	public static String fillCharAtStart(String s, char c, int total) {
		for(int i=s.length(); i<total; i++) {
			s = c+s;
		}
		return s;
	}
	
	public static String fillCharAtEnd(String s, char c, int total) {
		for(int i=s.length(); i<total; i++) {
			s = s+c;
		}
		return s;
	}
	
	public static String replaceAll(String s, Map<String, String> map) {
		String r = new String(s);
		Object keys[] = map.keySet().toArray();
		for(int i=0; i<keys.length; i++) {
			String key = (String)keys[i];
			String value= (String) map.get(key);
			while (r.indexOf(key)>0) {
				int p = r.indexOf(key);
				int q = key.length();
				r = r.substring(0, p)+value+r.substring(p+q, r.length());
			}
		}
		return r;
	}
	public static String beString(Object obj) {
		return (obj != null) ? (obj.toString()).trim() : "";
	}
	public static String resolveFields(String pstr, Object obj) throws ClassCastException
    {
        int    intPos = 0;
        int    intEnd = 0;
        String strCol = null;
        String strRet = null;

        
        if (pstr!=null && !pstr.equals("")) {
	        strRet = pstr;
	        
	        intPos = strRet.indexOf("{");
	        while (intPos >= 0)
	        {
	            intEnd = strRet.indexOf("}", intPos + 1);
	            if (intEnd != -1)
	            {
	                strCol = strRet.substring(intPos + 1, intEnd); 
	                strRet = strRet.substring(0, intPos) +  getField(obj, strCol) +
	                		strRet.substring(intEnd + 1);
	            }
	            intPos = strRet.indexOf("{", intPos +1);
	        }
        }
        return strRet;
    }
	private static Object getField(Object obj, String f) {
		try {
			return PropertyUtils.getProperty(obj, f);
		} catch(Exception ex) {
			return "";
		}
	}
}
