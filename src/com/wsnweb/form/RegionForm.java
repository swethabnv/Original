package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class RegionForm extends ActionForm {

	private static final long serialVersionUID = 8878251024499978499L;

	private String regionName; // text - regionName
	private String regionNo;
	private String cmd; 
	private String msg;
	
	
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionNo() {
		return regionNo;
	}

	public void setRegionNo(String regionNo) {
		this.regionNo = regionNo;
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
