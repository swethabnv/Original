package com.wsnweb.form;

import org.apache.struts.action.ActionForm;
import com.wsndata.data.Farmer;

public class FarmerForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -105045553168509866L;
	private String idCard;
	private String effectiveDate;
	private String abbrPrefix;
	private String firstName;
	private String lastName;
	private String addressNo;
	private int moo;
	private int postCode;
	private String tel;
	private String mobile;
	private String village;
	private String soi;
	private String street;
	private Long regionNo;
	private Long provinceNo;
	private Long districtNo;
	private Long subDistrictNo;
	private Long branchCode;

	private String cmd; 
	private String msg;
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
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
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
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
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setBranchCode(Long branchCode) {
		this.branchCode = branchCode;
	}
	public Long getBranchCode() {
		return branchCode;
	}
	
	public void loadToBean(Object o) {
		Farmer fObj = (Farmer)o;
		fObj.setIdCard(this.idCard);
		fObj.setAbbrPrefix(this.abbrPrefix);
		fObj.setFirstName(this.firstName);
		fObj.setLastName(this.lastName);
		fObj.setAddressNo(this.addressNo);
		fObj.setMoo(this.moo);
		fObj.setMobile(this.mobile);
		fObj.setVillage(this.village);
		fObj.setSoi(this.soi);
		fObj.setStreet(this.street);
		fObj.setPostCode(this.postCode);
		fObj.setTel(this.tel);
		fObj.setBranchCode(this.branchCode);
		fObj.setProvinceNo(this.provinceNo);
		fObj.setDistrictNo(this.districtNo);
		fObj.setSubDistrictNo(this.subDistrictNo);
	}
	
	public void loadFromBean(Object o) {
		if(o !=null){
			Farmer fObj = (Farmer)o;
			this.idCard =fObj.getIdCard();
			this.effectiveDate = fObj.getEffectiveDate().toString();
			this.abbrPrefix = fObj.getAbbrPrefix();
			this.firstName = fObj.getFirstName();
			this.lastName = fObj.getLastName();
			this.addressNo = fObj.getAddressNo();
			this.moo = 	fObj.getMoo();
			this.mobile = fObj.getMobile();
			this.postCode = fObj.getPostCode();
			this.tel = fObj.getTel();
			this.village = fObj.getVillage();
			this.soi = fObj.getSoi();
			this.street = fObj.getStreet();
			this.branchCode = fObj.getBranchCode();
			this.regionNo = fObj.getRegionNo();
			this.provinceNo = fObj.getProvinceNo();
			this.districtNo = fObj.getDistrictNo();
			this.subDistrictNo = fObj.getSubDistrictNo();
		}
	}

}
