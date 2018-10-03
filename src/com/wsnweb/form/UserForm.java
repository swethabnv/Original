package com.wsnweb.form;

import org.apache.struts.action.ActionForm;

import com.wsndata.data.User;

public class UserForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3969223530849130647L;
	private String userName;
	private String branch;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String groupUser;
	private String abbrPrefix;
	private long level;
	private long regionNo;
	private long provinceNo;
	
	private String userRegionNo;
	private String userProvinceNo;
	
	private long districtNo;
	private long subDistrictNo;
	private String tel;
	private String mobile;
	private String cmd;
	private String msg;
	
	private String currentPassword;
	private String confirmPassword;
	private String newPassword;

	private long farmerGroupId;
	
	private String[] userType; // กลุ่มผู้ใช้งาน
	private String[] userRegion;
	private String[] userProvince;
	private String[] userFarmerGroup;
	
	
	private String[] userRegionName;
	private String[] userProvinceName;
	private String[] userFarmerGroupName;
	
	//Add by Music 20/09/2017
	private String userFarmerGroupId;
	
	
	public String[] getUserRegion() {
		return userRegion;
	}
	public void setUserRegion(String[] userRegion) {
		this.userRegion = userRegion;
	}
	public String[] getUserProvince() {
		return userProvince;
	}
	public void setUserProvince(String[] userProvince) {
		this.userProvince = userProvince;
	}
	public String[] getUserFarmerGroup() {
		return userFarmerGroup;
	}
	public void setUserFarmerGroup(String[] userFarmerGroup) {
		this.userFarmerGroup = userFarmerGroup;
	}
	public String getAbbrPrefix() {
		return abbrPrefix;
	}
	public void setAbbrPrefix(String abbrPrefix) {
		this.abbrPrefix = abbrPrefix;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public void setGroupUser(String groupUser) {
		this.groupUser = groupUser;
	}
	public String getGroupUser() {
		return groupUser;
	}
	public long getLevel() {
		return level;
	}
	public void setLevel(long level) {
		this.level = level;
	}
	
	public long getRegionNo() {
		return regionNo;
	}
	public void setRegionNo(long regionNo) {
		this.regionNo = regionNo;
	}
	public long getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(long provinceNo) {
		this.provinceNo = provinceNo;
	}
	public String getUserRegionNo() {
		return userRegionNo;
	}
	public void setUserRegionNo(String userRegionNo) {
		this.userRegionNo = userRegionNo;
	}
	public String getUserProvinceNo() {
		return userProvinceNo;
	}
	public void setUserProvinceNo(String userProvinceNo) {
		this.userProvinceNo = userProvinceNo;
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
	public long getFarmerGroupId() {
		return farmerGroupId;
	}
	public void setFarmerGroupId(long farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}
	public String[] getUserType() {
		return userType;
	}
	public void setUserType(String[] userType) {
		this.userType = userType;
	}
	
	
	
	public String[] getUserRegionName() {
		return userRegionName;
	}
	public void setUserRegionName(String[] userRegionName) {
		this.userRegionName = userRegionName;
	}
	public String[] getUserProvinceName() {
		return userProvinceName;
	}
	public void setUserProvinceName(String[] userProvinceName) {
		this.userProvinceName = userProvinceName;
	}
	public String[] getUserFarmerGroupName() {
		return userFarmerGroupName;
	}
	public void setUserFarmerGroupName(String[] userFarmerGroupName) {
		this.userFarmerGroupName = userFarmerGroupName;
	}
	
	public String getUserFarmerGroupId() {
		return userFarmerGroupId;
	}
	public void setUserFarmerGroupId(String userFarmerGroupId) {
		this.userFarmerGroupId = userFarmerGroupId;
	}
	public void loadToBean(Object o) {
		User u = (User)o;
		u.setAbbrPrefix(this.abbrPrefix);
		u.setFirstName(this.firstName);
		u.setLastName(this.lastName);
		u.setTel(this.tel);
		u.setMobile(this.mobile);
		u.setEmail(this.email);
		u.setBranchCode(this.branch);
		u.setGroupUser(this.groupUser);
		u.setActive(true);
	}
	
	public void loadFromBean(Object o) {
		User u = (User)o;
		this.password = u.getPassword();
		this.userName = u.getUserName();
		this.abbrPrefix = u.getAbbrPrefix();
		this.firstName = u.getFirstName();
		this.lastName = u.getLastName();
		this.tel = u.getTel();
		this.mobile = u.getMobile();
		this.email = u.getEmail();
		this.branch = u.getBranchCode();
		this.groupUser = u.getGroupUser();
		this.level = u.getLevel();
		this.regionNo = u.getRegionNo(); 
		this.provinceNo =u.getProvinceNo(); 
		this.districtNo = u.getDistrictNo();
		this.subDistrictNo = u.getSubDistrictNo();
		this.farmerGroupId = u.getFarmerGroupId();
	}
	
	
	
}
