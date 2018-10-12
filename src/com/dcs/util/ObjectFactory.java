package com.dcs.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class ObjectFactory {
	private static final Logger log = Logger.getLogger(ObjectFactory.class);
	
	static HashMap<String, Object> objMap = new HashMap<String, Object>();
	
	public static Object getObject(Class c) {
		Object o = objMap.get(c.getName());
		if (o==null) {
			try {
				o = c.newInstance();
				objMap.put(c.getName(), o);
			} catch (Exception e) {
				log.error(e, e);
			}
		}
		return o;
	}
}
