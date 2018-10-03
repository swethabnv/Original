package com.wsnweb.form;

import java.util.Date;

import com.dcs.strut.exten.ListForm;

public class FarmerListForm extends ListForm {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7549308669637055618L;
	private String sortColumn = "firstName";
	private String[] delFarmer;
	private String idCard;
	private Date effectiveDate;
	private String firstName;
	private String lastName;
	private String tel;
	private long branchCode;
	private long provinceNo;
	private long districtNo;
	private long subDistrictNo;


	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	
	public String[] getDelFarmer() {
		return delFarmer;
	}

	public void setDelFarmer(String[] delFarmer) {
		this.delFarmer = delFarmer;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setBranchCode(long branchCode) {
		this.branchCode = branchCode;
	}

	public long getBranchCode() {
		return branchCode;
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

}
