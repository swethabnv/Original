package com.dcs.strut.exten.roleXML;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.dcs.strut.exten.IUserRight;

@XmlType(name = "userRight")
public class UserRight implements IUserRight{
	private String rightName;
	private String authorize;
	
	@XmlAttribute(name="authorize")
	public String getAuthorize() {
		return authorize;
	}
	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}	
	
	@XmlAttribute(name="name")
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	
	public boolean isAuthorizeRead() {
		return AUTHORIZE_READ.equals(getAuthorize());
	}
	public boolean isAuthorizeWrite() {
		return AUTHORIZE_WRITE.equals(getAuthorize());
	}
	public boolean isAuthorizeNone() {
		return AUTHORIZE_NONE.equals(getAuthorize()) || getAuthorize()==null || "".equals(getAuthorize());
	}
}
