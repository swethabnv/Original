package com.dcs.strut.exten.roleXML;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.dcs.strut.exten.IUserRight;
import com.dcs.strut.exten.IUserRole;

@XmlType(name = "userRole")
public class UserRole implements IUserRole{
	private String rolename;
	private Map<String, IUserRight> userRightMap;
	
	public UserRole() {
		userRightMap = new HashMap<String, IUserRight>();
	}
	
	@XmlAttribute(name="name")
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	@XmlElement(name="rightwrapper")
	@XmlJavaTypeAdapter(UserRightMapAdaptor.class)	
	public Map<String, IUserRight> getUserRightMap() {
		return userRightMap;
	}
	public void setUserRightMap(Map<String, IUserRight> userRightMap) {
		this.userRightMap = userRightMap;
	}

}
