package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class CooperativeGroupListForm extends ListForm 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4139180851844263655L;
	private String farmerGroupName;
	private String sortColumn = "farmerGroupName";
	private String[] delfarmerGroupName;
	private String provinceName;
	private String districtName;
	private String subDistrictName;
	private long provinceNo;
	private long districtNo;
	private long subDistrictNo;
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

	public String getCooperativeType() {
		return cooperativeType;
	}

	public void setCooperativeType(String cooperativeType) {
		this.cooperativeType = cooperativeType;
	}

}
