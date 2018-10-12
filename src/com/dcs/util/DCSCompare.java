package com.dcs.util;

import java.util.Comparator;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import org.apache.log4j.Logger;

public class DCSCompare implements Comparator {
	private static final Logger log = Logger.getLogger(DCSCompare.class);	
	private boolean sortAscending = true;
	private String sortField = null;
	
	public DCSCompare(String sortField) {
		this.sortField = sortField;
	}
	
	public DCSCompare(String sortField, boolean sortAscending) {
		this.sortField = sortField;
		this.sortAscending = sortAscending;
	}
	
	public int compare(Object o1, Object o2) {
		int i = compareObject(o1, o2);
		if (!sortAscending) {
			i = i*-1;
		}
		return i;
	}	

	private int compareObject(Object o1, Object o2) {
		try {
			Object obj1 = PropertyUtils.getProperty(o1, this.sortField);
			Object obj2 = PropertyUtils.getProperty(o2, this.sortField);
			if (obj1==null) return -1;
			if (obj2==null) return 1;
			if (obj1 instanceof Integer) {
				return ((Integer)obj1).compareTo((Integer)obj2);
			} else if (obj1 instanceof Long) {
				return ((Long)obj1).compareTo((Long)obj2);
			} else if (obj1 instanceof Float) {
				return ((Float)obj1).compareTo((Float)obj2);
			} else if (obj1 instanceof Double) {
				return ((Double)obj1).compareTo((Double)obj2);
			} else if (obj1 instanceof Date) {
				return ((Date)obj1).compareTo((Date)obj2);
			} else if (obj1 instanceof String) {
				return ((String)obj1).compareTo((String)obj2);
			} else if (obj1 instanceof Boolean) {
				return ((Boolean)obj1).compareTo((Boolean)obj2);
			}
			return 0;
		} catch(Exception ex) {
			ex.printStackTrace();
			log.error("Error to compare object "+o1+" "+o2);
			return 0;
		}
	}

}
