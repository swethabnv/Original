package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

public class FarmerGroupAddressForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8475475463911892116L;
	private String farmerGroupName;
	private String regionName;
	private String provinceName;
	private String districtName;
    private String subDistrictName;
    

    private long farmerGroupId;
    private long regionNo;
	private long provinceNo;
	private long districtNo;
    private long subDistrictNo;
    
    private String cmd; 
	private String msg;
	
	public String getFarmerGroupName() {
		return farmerGroupName;
	}
	public void setFarmerGroupName(String farmerGroupId) {
		this.farmerGroupName = farmerGroupId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceNo) {
		this.provinceName = provinceNo;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtNo) {
		this.districtName = districtNo;
	}
	public String getSubDistrictName() {
		return subDistrictName;
	}
	public void setSubDistrictName(String subDistrictNo) {
		this.subDistrictName = subDistrictNo;
	}
	
	public long getFarmerGroupId() {
		return farmerGroupId;
	}
	public void setFarmerGroupId(long farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}
	public long getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(long provinceNo) {
		this.provinceNo = provinceNo;
	}
	public long getDistrictNo() {
		return districtNo;
	}
	public void setDistrictNo(long districtNo) {
		this.districtNo = districtNo;
	}
	public long getSubDistrictNo() {
		return subDistrictNo;
	}
	public void setSubDistrictNo(long subDistrictNo) {
		this.subDistrictNo = subDistrictNo;
	}
	public String getCmd() {
		return cmd;
	}
    
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public long getRegionNo() {
		return regionNo;
	}
	public void setRegionNo(long regionNo) {
		this.regionNo = regionNo;
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
