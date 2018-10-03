package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7795380519512728709L;
	private String userName;
	private String password;
	private long branchCode;
	private String branchName;
	private String cmd;
	private String msg;
	private String showCapcha; // login ผิด 3 ครั้ง show capcha!
	private String verificationCode;
	
	
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getShowCapcha() {
		return showCapcha;
	}
	public void setShowCapcha(String showCapcha) {
		this.showCapcha = showCapcha;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(long branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
