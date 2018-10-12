package com.dcs.util;

import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import com.dcs.dcswc.common.ICheckOnGrid;

public class GridUtil {
	public static void applyCheckValue(List<ICheckOnGrid> list, String[] checkValue, String dataField, int start, int row) throws Exception{
		if (list !=null && list.size()>0){
			for (int i=start; i<start+row && i<list.size(); i++){
				ICheckOnGrid obj = list.get(i);
				String value = getPropertyValue(obj, dataField);
				boolean check = false;
				for (int j=0; checkValue!=null && j<checkValue.length; j++) {
					if (value.equals(checkValue[j])) {
						check = true;
						break;
					}
				}
				obj.setCheckBox(check);
			}
		}
	}
	
	public static void applyCheckValue(List<ICheckOnGrid> list, long[] checkValue, String dataField, int start, int row) throws Exception{
		if (list !=null && list.size()>0){
			for (int i=start; i<start+row && i<list.size(); i++){
				ICheckOnGrid obj = list.get(i);
				String value = getPropertyValue(obj, dataField);
				boolean check = false;
				for (int j=0; checkValue!=null && j<checkValue.length; j++) {
					if (value.equals(String.valueOf(checkValue[j]))) {
						check = true;
						break;
					}
				}
				obj.setCheckBox(check);
			}
		}
	}
	
	public static String getPropertyValue(Object obj1, String field) {
		try {
			if (obj1!=null && !"".equals(field)) {
				Object obj = PropertyUtils.getProperty(obj1, field);
				if (obj instanceof String) {
					return (String)obj;
				} if (obj instanceof Integer) {
					return ((Integer)obj).toString();
				} else if (obj instanceof Long) {
					return ((Long)obj).toString();
				} else if (obj instanceof Float) {
					return ((Float)obj).toString();
				} else if (obj instanceof Double) {
					return ((Integer)obj).toString();
				} else {
					return obj.toString();
				}
			}
			return null;
		} catch(Exception ex) {
			System.out.println("Error get property :"+field);
			return null;
		}
	}
}
