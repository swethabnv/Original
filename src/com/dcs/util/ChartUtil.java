package com.dcs.util;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class ChartUtil {
	public static PieDataset createPieDataset(Collection<Object> cols,String key, String value) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		Object objs[] = cols.toArray();
		Arrays.sort(objs, new DCSCompare(key));
		try {
			for (int i=0; i<objs.length; i++) {
				Object k = PropertyUtils.getProperty(objs[i], key);
				Object v = PropertyUtils.getProperty(objs[i], value);
				dataset.setValue((Comparable)k, (Number)v);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return dataset;
	}
	
	public static CategoryDataset createCategoryDataset(Collection<Object> cols,String cat, String key, String value) {		
		return createCategoryDataset(cols, cat, key, value, null);
	}
	
	public static CategoryDataset createCategoryDataset(Collection<Object> cols,String cat, String key, String value, String sort) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Object objs[] = cols.toArray();
		String sortField = new String();
		if (sort==null) {
			sortField=cat;
		} else {
			sortField=sort;
		}
		Arrays.sort(objs, new DCSCompare(sortField));
		try {
			for (int i=0; i<objs.length; i++) {				
				Object c = PropertyUtils.getProperty(objs[i], cat);
				Object k = PropertyUtils.getProperty(objs[i], key);
				Object v = PropertyUtils.getProperty(objs[i], value);
				dataset.setValue((Number)v, (Comparable)c, (Comparable)k);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return dataset;
	}
	
	public static CategoryDataset createCategoryDataset(Collection<Object> cols, String key, String value) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Object objs[] = cols.toArray();
		Arrays.sort(objs, new DCSCompare(key));
		try {
			for (int i=0; i<objs.length; i++) {
				Object k = PropertyUtils.getProperty(objs[i], key);
				Object v = PropertyUtils.getProperty(objs[i], value);
				dataset.setValue((Number)v, "none", (Comparable)k);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return dataset;
	}
}
