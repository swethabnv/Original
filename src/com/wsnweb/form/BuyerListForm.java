package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class BuyerListForm extends ListForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7549308669637055618L;
	private String sortColumn = "buyerId";
	private String[] delBuyer;
	private String buyerId;
	private String buyerName;
	private String provinceNo;
	private String districtNo;
	private String subDistrictNo;
	private String districtName;
	private String subDistrictName;
	private String provinceName;
	private String tel;
	
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String[] getDelBuyer() {
		return delBuyer;
	}
	public void setDelBuyer(String[] delBuyer) {
		this.delBuyer = delBuyer;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	
}
