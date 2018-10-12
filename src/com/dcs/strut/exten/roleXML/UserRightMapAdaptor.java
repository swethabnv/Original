package com.dcs.strut.exten.roleXML;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class UserRightMapAdaptor extends	XmlAdapter<UserRightWrapper, Map<String, UserRight>> {
	public Map<String, UserRight> unmarshal(UserRightWrapper value) {
		Map<String, UserRight> map = new HashMap<String, UserRight>();
		for (UserRight right : value.getUserRight()) {
			map.put(right.getRightName(), right);
		}
		return map;
	}

	public UserRightWrapper marshal(Map<String, UserRight> value) {
		UserRightWrapper userRightWrapper = new UserRightWrapper();
		userRightWrapper.setUserRight(value.values().toArray(new UserRight[value.size()]));
		return userRightWrapper;
	}
}
