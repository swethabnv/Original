package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class PlantListForm extends ListForm {

	private static final long serialVersionUID = -7787169462559493017L;
	private String[] delPlantId;
	private String sortColumn = "idCard";
	private String cmd;
	private String msg;
	
	private String breedTypeId;
	private String plantYear;
	private String plantNo;
	private String customerNo;
	private String idCard;
	private String branchCode;
 	private String firstName;
	private String lastName;
	
	//Add for search by Thanaput.s
	private long provinceNo;
	private long districtNo;
	private long subDistrictNo;
	private String plantStatus; // Add by Sedtapong.n
	private long farmerGroupId;
	
	private String reason;
	private String remark;
	
	private String userProvinceNo; // Added by Yatphiroon.p on 10/1/2017
	private String userFarmerGroupId; // Added by Yatphiroon.P on 10/1/2017
	
	private String[] userProvince;
	
	
	
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String[] getDelPlantId() {
		return delPlantId;
	}

	public void setDelPlantId(String[] delPlantId) {
		this.delPlantId = delPlantId;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getBreedTypeId() {
		return breedTypeId;
	}

	public void setBreedTypeId(String breedTypeId) {
		this.breedTypeId = breedTypeId;
	}

	public String getPlantYear() {
		return plantYear;
	}

	public void setPlantYear(String plantYear) {
		this.plantYear = plantYear;
	}

	public String getPlantNo() {
		return plantNo;
	}

	public void setPlantNo(String plantNo) {
		this.plantNo = plantNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getPlantStatus() {
		return plantStatus;
	}

	public void setPlantStatus(String plantStatus) {
		this.plantStatus = plantStatus;
	}

	public long getFarmerGroupId() {
		return farmerGroupId;
	}

	public void setFarmerGroupId(long farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserProvinceNo() {
		return userProvinceNo;
	}

	public void setUserProvinceNo(String userProvinceNo) {
		this.userProvinceNo = userProvinceNo;
	}

	public String getUserFarmerGroupId() {
		return userFarmerGroupId;
	}

	public void setUserFarmerGroupId(String userFarmerGroupId) {
		this.userFarmerGroupId = userFarmerGroupId;
	}

	public String[] getUserProvince() {
		return userProvince;
	}

	public void setUserProvince(String[] userProvince) {
		this.userProvince = userProvince;
	}

	

	
}
