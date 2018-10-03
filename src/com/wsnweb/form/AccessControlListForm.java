package com.wsnweb.form;

import org.apache.struts.action.ActionForm;


public class AccessControlListForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1727325453389918456L;

	private String userName;
	private String firstName;
	private String lastName;
	private String menuId;
	private String authArray;
	private String cmd;
	private String msg;
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getAuthArray() {
		return authArray;
	}

	public void setAuthArray(String authArray) {
		this.authArray = authArray;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
