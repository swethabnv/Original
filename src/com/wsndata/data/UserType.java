package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

public class UserType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6067686245083053702L;
	private String userName;
	private String userType;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	
}
