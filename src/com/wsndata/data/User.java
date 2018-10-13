package com.wsndata.data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.dcs.dcswc.common.ICheckOnGrid;

public class User implements Serializable, ICheckOnGrid {

	private static final long serialVersionUID = 4798813024876911136L;
	private String userName;
	private String password;       
	private String branchCode;
	private String name; // added for userList
	private String branchName; // added for userList
	private String firstName;
	private String lastName;
	private String email;
	private String groupUser;
	private long level;
	private boolean active;
	private Date createDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private Set<UserAuthorize> userAuthorize;
	private String abbrPrefix;
	private String tel;
	private String mobile;
	
	private boolean checkBox;// added for userList
	private String status; // added for userList
	private long regionNo; // added for userList
	private long provinceNo; // added for userList
	private long districtNo; // added for userList
	private long subDistrictNo; // added for userList
	private String region;// added for userList
	private String province;// added for userList
	private String district;// added for userList
	private String subDistrict;// added for userList
	private String strLevel;
	private long farmerGroupId;
	
	private Set<UserRegion> userRegion;
	private Set<UserProvince> userProvince;
	private Set<UserFarmerGroup> userFarmerGroup;
	
	

	public Set<UserRegion> getUserRegion() {
		return userRegion;
	}
	public void setUserRegion(Set<UserRegion> userRegion) {
		this.userRegion = userRegion;
	}
	public Set<UserProvince> getUserProvince() {
		return userProvince;
	}
	public void setUserProvince(Set<UserProvince> userProvince) {
		this.userProvince = userProvince;
	}
	public Set<UserFarmerGroup> getUserFarmerGroup() {
		return userFarmerGroup;
	}
	public void setUserFarmerGroup(Set<UserFarmerGroup> userFarmerGroup) {
		this.userFarmerGroup = userFarmerGroup;
	}
	public String getAbbrPrefix() {
		return abbrPrefix;
	}
	public void setAbbrPrefix(String abbrPrefix) {
		this.abbrPrefix = abbrPrefix;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Set<UserAuthorize> getUserAuthorize() {
		return userAuthorize;
	}
	public void setUserAuthorize(Set<UserAuthorize> userAuthorize) {
		this.userAuthorize = userAuthorize;
	}
	public boolean getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}
	
	public String getLinkImageAccess() {
		return "images/btn-access.png";
	}
	public String getLinkImageReset() {
		return "images/key.png";
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getLevel() {
		return level;
	}
	public void setLevel(long level) {
		this.level = level;
	}
	public String getStrLevel() {
		return strLevel;
	}
	public void setStrLevel(String strLevel) {
		this.strLevel = strLevel;
	}
	public void setGroupUser(String groupUser) {
		this.groupUser = groupUser;
	}
	public String getGroupUser() {
		return groupUser;
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
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getSubDistrict() {
		return subDistrict;
	}
	public void setSubDistrict(String subDistrict) {
		this.subDistrict = subDistrict;
	}
	public long getFarmerGroupId() {
		return farmerGroupId;
	}
	public void setFarmerGroupId(long farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}
	
}
