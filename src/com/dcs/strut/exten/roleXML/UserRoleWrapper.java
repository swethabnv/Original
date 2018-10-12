package com.dcs.strut.exten.roleXML;

import javax.xml.bind.annotation.XmlElement;

public class UserRoleWrapper {	
    private UserRole[] userRole;

    @XmlElement(name="userrole")
	public UserRole[] getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole[] userRole) {
		this.userRole = userRole;
	}
}
