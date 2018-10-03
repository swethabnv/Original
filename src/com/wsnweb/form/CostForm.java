package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class CostForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105045553168509866L;
	private long costId;
	private String costName;
	private String cmd; 
	private String msg;
	
    
	public long getCostId() {
		return costId;
	}

	public void setCostId(long costId) {
		this.costId = costId;
	}

	public String getCostName() {
		return costName;
	}

	public void setCostName(String costName) {
		this.costName = costName;
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
