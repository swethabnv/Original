package com.dcs.strut.exten.roleXML;

import javax.xml.bind.annotation.XmlElement;

public class UserRightWrapper {	
    private UserRight[] userRight;

    @XmlElement(name="userright")
	public UserRight[] getUserRight() {
		return userRight;
	}

	public void setUserRight(UserRight[] userRight) {
		this.userRight = userRight;
	}
}
