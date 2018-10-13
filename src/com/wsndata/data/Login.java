package com.wsndata.data;
import java.io.Serializable;
import java.util.Date;
public class Login implements Serializable {

	private static final long serialVersionUID = 6274004892017679235L;

	private String userName;
	private int countLogin;
	private Date lastLoginDate; 

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getCountLogin() {
		return countLogin;
	}

	public void setCountLogin(int countLogin) {
		this.countLogin = countLogin;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
}
