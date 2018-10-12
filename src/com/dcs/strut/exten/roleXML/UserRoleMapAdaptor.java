package com.dcs.strut.exten.roleXML;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class UserRoleMapAdaptor extends	XmlAdapter<UserRoleWrapper, Map<String, UserRole>> {
	public Map<String, UserRole> unmarshal(UserRoleWrapper value) {
		Map<String, UserRole> map = new HashMap<String, UserRole>();
		for (UserRole role : value.getUserRole()) {
			map.put(role.getRolename(), role);
		}
		return map;
	}

	public UserRoleWrapper marshal(Map<String, UserRole> value) {
		UserRoleWrapper userRoleWrapper = new UserRoleWrapper();
		userRoleWrapper.setUserRole(value.values().toArray(new UserRole[value.size()]));
		return userRoleWrapper;
	}


}
