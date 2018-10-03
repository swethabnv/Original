package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class DistrictForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3674592491751030385L;
	private String regionNo;   // drop
	private String provinceNo; // dropdown - provinceNo
	private String thaiName; // text - districtName
	private String engName;
	private String districtNo;
	private String cmd;
	private String msg;
	private String prevRegionNo;
	private String prevProvinceNo;
	
	

	public String getPrevRegionNo() {
		return prevRegionNo;
	}
	public void setPrevRegionNo(String prevRegionNo) {
		this.prevRegionNo = prevRegionNo;
	}
	public String getPrevProvinceNo() {
		return prevProvinceNo;
	}
	public void setPrevProvinceNo(String prevProvinceNo) {
		this.prevProvinceNo = prevProvinceNo;
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
	public String getDistrictNo() {
		return districtNo;
	}
	public void setDistrictNo(String districtNo) {
		this.districtNo = districtNo;
	}
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
	public String getRegionNo() {
		return regionNo;
	}
	public void setRegionNo(String regionNo) {
		this.regionNo = regionNo;
	}
	public String getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}
	
	/*
	public void loadFromBean(Object o) {
		// TODO Auto-generated method stub
		District d = (District)o;
		this.regionNo = String.valueOf(d.getRegionNo());
		this.provinceNo = String.valueOf(d.getProvinceNo());
		this.districtNo = String.valueOf(d.getDistrictNo());
		this.engName = d.getEngName();
		this.thaiName = d.getThaiName();
	}*/
	
}
