package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class BankForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3929413890466377399L;
	private String bankName; // text - bankName
	private long bankId;
	private String branchCode;
	private String cmd; 
	private String msg;
	

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
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
