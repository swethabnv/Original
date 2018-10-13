package com.wsndata.data;

import java.io.Serializable;

public class FarmerGroupAddress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8726837083310596691L;
	private long farmerGroupId;
	private long regionNo;
	private long provinceNo;
	private long districtNo;
	private long subDistrictNo;
	
	private FarmerGroup farmerGroup;
	private SubDistrict subDistrict;
	
	public long getFarmerGroupId() {
		if(farmerGroup !=null){
			return farmerGroup.getFarmerGroupId();
		}else{
			return farmerGroupId;
		}
	}
	public void setFarmerGroupId(long farmerGroupId) {
		if(farmerGroup !=null){
			farmerGroup.setFarmerGroupId(farmerGroupId);
		}else{
			this.farmerGroupId = farmerGroupId;
		}
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
	public FarmerGroup getFarmerGroup() {
		return farmerGroup;
	}
	public void setFarmerGroup(FarmerGroup farmerGroup) {
		this.farmerGroup = farmerGroup;
	}
	public SubDistrict getSubDistrict() {
		return subDistrict;
	}
	public void setSubDistrict(SubDistrict subDistrict) {
		this.subDistrict = subDistrict;
	}
}
