package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class CloseDueForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105045553168509866L;
	private long closeDueId;
	private long plantYear;
	private long plantNo;
	private long farmerGroupId;
	private String cmd; 
	private String msg;
	

	public long getCloseDueId() {
		return closeDueId;
	}

	public void setCloseDueId(long closeDueId) {
		this.closeDueId = closeDueId;
	}

	public long getPlantYear() {
		return plantYear;
	}

	public void setPlantYear(long plantYear) {
		this.plantYear = plantYear;
	}

	public long getPlantNo() {
		return plantNo;
	}

	public void setPlantNo(long plantNo) {
		this.plantNo = plantNo;
	}

	public long getFarmerGroupId() {
		return farmerGroupId;
	}

	public void setFarmerGroupId(long farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
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
