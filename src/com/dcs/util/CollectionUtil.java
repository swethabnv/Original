package com.dcs.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.commons.beanutils.PropertyUtils;

public class CollectionUtil {
	public static HashMap mapFromList(List list, String key){
		HashMap map = new HashMap();
		StringTokenizer st = new StringTokenizer(key,",");
		String[] token = new String[st.countTokens()];
		int c = 0;
		try {
		    while (st.hasMoreTokens()) {
		        token[c++] = st.nextToken();
		    }
			for(int i=0; i<list.size(); i++) {
				String k = "";
				for (int j=0; j<token.length; j++) {
					k += PropertyUtils.getProperty(list.get(i), token[j]);
					if (j<token.length-1) k += "_";
				}
				map.put(k, list.get(i));
			}
		} catch(Exception ex) {}
		return map;
	}
	
	public static List listFromMap(Map map) {
		List list = new ArrayList();
		Collection collection = map.values();
		Iterator iterator = collection.iterator();
		while(iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}
	
	public static List listFromSet(Set set) {
		List list = new ArrayList();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}
	
	public static Set setFromMap(Map map) {
		HashSet set = new HashSet();
		Collection collection = map.values();
		Iterator iterator = collection.iterator();
		while(iterator.hasNext()) {
			set.add(iterator.next());
		}
		return set;
	}
}
