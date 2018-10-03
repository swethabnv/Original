package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class RegionListForm extends ListForm {

	private static final long serialVersionUID = -7959244173902436978L;
	private String sortColumn = "regionName";
	private String[] delRegionNo;
	private String regionName;
	
	
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String[] getDelRegionNo() {
		return delRegionNo;
	}

	public void setDelRegionNo(String[] delRegionNo) {
		this.delRegionNo = delRegionNo;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

}
