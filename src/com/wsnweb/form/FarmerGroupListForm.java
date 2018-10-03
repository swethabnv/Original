package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class FarmerGroupListForm extends ListForm 
{
	private static final long serialVersionUID = 694370643247916746L;
	private String farmerGroupName;
	private String farmerGroupId;
	private String sortColumn = "farmerGroupName";
	private String[] delfarmerGroupName;
	private String provinceName;
	private String districtName;
	private String subDistrictName;
	private String provinceNo;
	private String districtNo;
	private String subDistrictNo;
	private String cooperativeType;
	
	public String[] getDelfarmerGroupName() {
		return delfarmerGroupName;
	}

	public void setDelfarmerGroupName(String[] delfarmerGroupName) {
		this.delfarmerGroupName = delfarmerGroupName;
	}

	public String getFarmerGroupName() {
		return farmerGroupName;
	}

	public void setFarmerGroupName(String farmerGroupName) {
		this.farmerGroupName = farmerGroupName;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getSubDistrictName() {
		return subDistrictName;
	}

	public void setSubDistrictName(String subDistrictName) {
		this.subDistrictName = subDistrictName;
	}

	public String getProvinceNo() {
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	public String getDistrictNo() {
		return districtNo;
	}

	public void setDistrictNo(String districtNo) {
		this.districtNo = districtNo;
	}

	public String getSubDistrictNo() {
		return subDistrictNo;
	}

	public void setSubDistrictNo(String subDistrictNo) {
		this.subDistrictNo = subDistrictNo;
	}

	public String getCooperativeType() {
		return cooperativeType;
	}

	public void setCooperativeType(String cooperativeType) {
		this.cooperativeType = cooperativeType;
	}

	public String getFarmerGroupId() {
		return farmerGroupId;
	}

	public void setFarmerGroupId(String farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}

	

}
