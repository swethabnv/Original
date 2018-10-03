package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class EconomicBreedForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105045553168509866L;
	private long breedTypeId;
	private long regionNo;
	private long provinceNo;
	private String province;
	private String tmpEdit;
	

	private String cmd; 
	private String msg;
	
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public long getBreedTypeId() {
		return breedTypeId;
	}

	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}

	

	public long getRegionNo() {
		return regionNo;
	}

	public void setRegionNo(long regionNo) {
		this.regionNo = regionNo;
	}

	public long getProvinceNo() {
		return provinceNo;
	}

	public void setProvinceNo(long provinceNo) {
		this.provinceNo = provinceNo;
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

	public void setTmpEdit(String tmpEdit) {
		this.tmpEdit = tmpEdit;
	}

	public String getTmpEdit() {
		return tmpEdit;
	}
    
}
