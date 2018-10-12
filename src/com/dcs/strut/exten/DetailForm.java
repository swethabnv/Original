package com.dcs.strut.exten;

import org.apache.struts.action.ActionForm;

public abstract class DetailForm extends ActionForm implements IDetailForm {
	private String errMessage;
	private String cmd;

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
}
