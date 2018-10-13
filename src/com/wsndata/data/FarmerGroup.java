package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.List;

import com.dcs.dcswc.common.ICheckOnGrid;

public class FarmerGroup implements Serializable ,ICheckOnGrid{
	

	private static final long serialVersionUID = 6919820277344461475L;
	private long farmerGroupId;
	private String farmerGroupName;
	private long branchCode;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private double target;
	private String strTarget;
	private String farmerGroupType; // set into table only 1 character
	private String objective;
	private String addressNo;
	private int moo;
	private String village;
	private String soi;
	private String road;
	private String mobile;
	private String tel;
	private String fax;
	private Date createDate;
	private String createBy;
	
	private Set<FarmerGroupFarmer> farmerGroupFarmer;
	private Set<FarmerGroupAddress> farmerGroupAddress;
	private Set<FarmerGroupChild> farmerGroupChild;
	private List<FarmerGroupTeam> farmerGroupTeam;
	private boolean checkBox;// added for farmerGroupList
	private String provinceName;// added for farmerGroupfarmer
	private String districtName; // added for farmerGroupfarmer
	private String subDistrictName;// added for farmerGroupfarmer
	private String joinCooperative; // added for farmerGroupfarmer
	private int countFarmer; // added for farmerGroupfarmer
	
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
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public String getFarmerGroupType() {
		return farmerGroupType;
	}
	public void setFarmerGroupType(String farmerGroupType) {
		this.farmerGroupType = farmerGroupType;
	}
	public String getStrTarget() {
		return strTarget;
	}
	public void setStrTarget(String strTarget) {
		this.strTarget = strTarget;
	}
	public Set<FarmerGroupFarmer> getFarmerGroupFarmer() {
		return farmerGroupFarmer;
	}
	public void setFarmerGroupFarmer(Set<FarmerGroupFarmer> farmerGroupFarmer) {
		this.farmerGroupFarmer = farmerGroupFarmer;
	}
	public Set<FarmerGroupAddress> getFarmerGroupAddress() {
		return farmerGroupAddress;
	}
	public void setFarmerGroupAddress(Set<FarmerGroupAddress> farmerGroupAddress) {
		this.farmerGroupAddress = farmerGroupAddress;
	}
	public Set<FarmerGroupChild> getFarmerGroupChild() {
		return farmerGroupChild;
	}
	public void setFarmerGroupChild(Set<FarmerGroupChild> farmerGroupChild) {
		this.farmerGroupChild = farmerGroupChild;
	}
	public List<FarmerGroupTeam> getFarmerGroupTeam() {
		return farmerGroupTeam;
	}
	public void setFarmerGroupTeam(List<FarmerGroupTeam> farmerGroupTeam) {
		this.farmerGroupTeam = farmerGroupTeam;
	}
	public double getTarget() {
		return target;
	}
	public void setTarget(double target) {
		this.target = target;
	}
	
	public long getFarmerGroupId() {
		return farmerGroupId;
	}
	public void setFarmerGroupId(long farmerGroupId) {
		this.farmerGroupId = farmerGroupId;
	}
	public String getFarmerGroupName() {
		return farmerGroupName;
	}
	public void setFarmerGroupName(String farmerGroupName) {
		this.farmerGroupName = farmerGroupName;
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
	public boolean getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}
	public String getLinkImageFarmerGroupEdit() {
		return "images/btn-farmer.png";
	}
	public void setBranchCode(long branchCode) {
		this.branchCode = branchCode;
	}
	public long getBranchCode() {
		return branchCode;
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
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
    public String getJoinCooperative() {
		return joinCooperative;
	}
	public void setJoinCooperative(String joinCooperative) {
		this.joinCooperative = joinCooperative;
	}
	public int getCountFarmer() {
		return countFarmer;
	}
	public void setCountFarmer(int countFarmer) {
		this.countFarmer = countFarmer;
	}
}
