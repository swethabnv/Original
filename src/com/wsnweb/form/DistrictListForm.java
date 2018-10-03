package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class DistrictListForm extends ListForm 
{
	
	private static final long serialVersionUID = 2424807031171302901L;
	private String sortColumn = "thaiName";
	private String[] delDistrictNo;
	private String districtName;
	private String districtNo;
	private String regionNo;
	private String provinceNo;
	
	

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictNo() {
		return districtNo;
	}

	public void setDistrictNo(String districtNo) {
		this.districtNo = districtNo;
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

	public String[] getDelDistrictNo() {
		return delDistrictNo;
	}

	public void setDelDistrictNo(String[] delDistrictNo) {
		this.delDistrictNo = delDistrictNo;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;

	}

}
