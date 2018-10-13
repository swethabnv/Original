package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

import com.dcs.dcswc.common.ICheckOnGrid;

public class Farmer implements Serializable, ICheckOnGrid{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1542281835882671093L;
	private String idCard;
	private Date effectiveDate;
	private String abbrPrefix;
	private String fullPrefix;
	private String firstName;
	private String lastName;
	private String addressNo;
	private int moo;
	private int postCode;
	private String tel;
	private String mobile;
	private Date createDate;
	private String createBy;
	private Long regionNo;
	private Long provinceNo;
	private Long districtNo;
	private Long subDistrictNo;
	private String customerNo; // added on 13.08.2014
	
	private Long branchCode;
	private String soi;
	private String street;
	private long farmerGroupId;
	private int plantYear;
	private int plantNo;
	private String email;
	private String village;
	
	private boolean checkBox;// added for List

	private SubDistrict subDistrict;
	private District district;
	private Province province;
	
	public String getSoi() {
		return soi;
	}
	public void setSoi(String soi) {
		this.soi = soi;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public long getFarmerGroupId() {
		return farmerGroupId;
	}
	public void setFarmerGroupId(long farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}
	public int getPlantYear() {
		return plantYear;
	}
	public void setPlantYear(int plantYear) {
		this.plantYear = plantYear;
	}
	public int getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(int plantNo) {
		this.plantNo = plantNo;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getFullPrefix() {
		return fullPrefix;
	}
	public void setFullPrefix(String fullPrefix) {
		this.fullPrefix = fullPrefix;
	}
	public Long getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(Long branchCode) {
		this.branchCode = branchCode;
	}
	public Long getRegionNo() {
		return regionNo;
	}
	public void setRegionNo(Long regionNo) {
		this.regionNo = regionNo;
	}
	public Long getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(Long provinceNo) {
		this.provinceNo = provinceNo;
	}
	public Long getDistrictNo() {
		return districtNo;
	}
	public void setDistrictNo(Long districtNo) {
		this.districtNo = districtNo;
	}
	public Long getSubDistrictNo() {
		return subDistrictNo;
	}
	public void setSubDistrictNo(Long subDistrictNo) {
		this.subDistrictNo = subDistrictNo;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getAbbrPrefix() {
		return abbrPrefix;
	}
	public void setAbbrPrefix(String abbrPrefix) {
		this.abbrPrefix = abbrPrefix;
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
	public String getAddressNo() {
		return addressNo;
	}
	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}
	public int getMoo() {
		return moo;
	}
	public void setMoo(int moo) {
		this.moo = moo;
	}
	public int getPostCode() {
		return postCode;
	}
	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public boolean getCheckBox() {
		return checkBox;
	}
	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}
	public SubDistrict getSubDistrict() {
		return subDistrict;
	}
	public void setSubDistrict(SubDistrict subDistrict) {
		this.subDistrict = subDistrict;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	
}
