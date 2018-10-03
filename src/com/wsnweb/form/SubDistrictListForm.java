package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class SubDistrictListForm extends ListForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2739534389463344662L;
	private String sortColumn = "thaiName";
	private String[] delSubDistrictNo;
	private String subDistrictName;
	private String districtNo;
	private String regionNo;
	private String provinceNo;
	private String postCode;
	
	
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String[] getDelSubDistrictNo() {
		return delSubDistrictNo;
	}

	public void setDelSubDistrictNo(String[] delSubDistrictNo) {
		this.delSubDistrictNo = delSubDistrictNo;
	}

	public String getSubDistrictName() {
		return subDistrictName;
	}

	public void setSubDistrictName(String subDistrictName) {
		this.subDistrictName = subDistrictName;
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

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;

	}

}
