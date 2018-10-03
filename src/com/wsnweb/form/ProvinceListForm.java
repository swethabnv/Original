package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class ProvinceListForm extends ListForm {

	private static final long serialVersionUID = -5264333589802249558L;
	private String sortColumn = "thaiName";
	private String[] delProvinceNo;
	private String provinceName;
	private String regionNo;
		
	public String getRegionNo() {
		return regionNo;
	}
	public void setRegionNo(String regionNo) {
		this.regionNo = regionNo;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String[] getDelProvinceNo() {
		return delProvinceNo;
	}
	public void setDelProvinceNo(String[] delProvinceNo) {
		this.delProvinceNo = delProvinceNo;
	}
	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;

	}

}
