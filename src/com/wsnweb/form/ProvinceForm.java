package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class ProvinceForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3985812604145831358L;
	private String thaiName; // text - provinceName
	private String engName; // text - provinceName
	private String provinceNo; // text - provinceNo
	private String regionNo; // dropdown - regionNo
	private String cmd; 
	private String msg;
	private String prevRegionNo;
	
	
	public String getPrevRegionNo() {
		return prevRegionNo;
	}
	public void setPrevRegionNo(String prevRegionNo) {
		this.prevRegionNo = prevRegionNo;
	}
	
	
	public String getThaiName() {
		return thaiName;
	}
	public void setThaiName(String thaiName) {
		this.thaiName = thaiName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
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
	
	public String getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}
	public String getRegionNo() {
		return regionNo;
	}
	public void setRegionNo(String regionNo) {
		this.regionNo = regionNo;
	}
	
	
	
}
