package com.dcs.strut.exten.roleXML;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="rolemap")
public class RoleMap {
	private Map<String, UserRole> userRole;
	
	public RoleMap() {
		userRole = new HashMap<String, UserRole>();
	}	
	
	@XmlElement(name="rolewrapper")
	@XmlJavaTypeAdapter(UserRoleMapAdaptor.class)
	public Map<String, UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Map<String, UserRole> userRole) {
		this.userRole = userRole;
	}
}
