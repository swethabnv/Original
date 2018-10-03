package com.wsnweb.form;

import com.dcs.strut.exten.ListForm;

public class UserListForm extends ListForm {


	private static final long serialVersionUID = 2791359044569081012L;
	private String sortColumn = "userName";
	private String[] delUserName;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String branchCode;
	private String regionNo;
	private String provinceNo;
	private String districtNo;
	private String subDistrictNo;
	private long level;
	private long groupUser;
	
	private char active;
	private String cmd;

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String[] getDelUserName() {
		return delUserName;
	}

	public void setDelUserName(String[] delUserName) {
		this.delUserName = delUserName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	public char getActive() {
		return active;
	}

	public void setActive(char active) {
		this.active = active;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public long getGroupUser() {
		return groupUser;
	}

	public void setGroupUser(long groupUser) {
		this.groupUser = groupUser;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	

}
