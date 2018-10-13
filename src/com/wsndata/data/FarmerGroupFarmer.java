package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

import com.dcs.dcswc.common.ICheckOnGrid;

public class FarmerGroupFarmer implements Serializable ,ICheckOnGrid{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4873864794688803541L;

	private long farmerGroupId;
	private String idCard;
	private Date effectiveDate;
	private FarmerGroup farmerGroup;
	private Farmer farmer;
	private boolean checkBox;// added for farmerGroupList

	private String firstName;
	private String lastName;
	private String provinceName; // added for farmerGroupFarmer
	private String districtName; // added for farmerGroupFarmer
	private String subDistrictName; // added for farmerGroupFarmer
	private String farmerGroupName; // added for farmerGroupFarmer
	private String memberNo; // added for farmerGroupFarmer
	
	public long getFarmerGroupId() {
		if (farmerGroup != null) {
			return farmerGroup.getFarmerGroupId();
		} else {
			return farmerGroupId;
		}
	}
	public void setFarmerGroupId(long farmerGroupId) {
		if (farmerGroup != null) {
			farmerGroup.setFarmerGroupId(farmerGroupId);
		} else {
			this.farmerGroupId = farmerGroupId;
		}
	}
	public String getIdCard() {
	    if (farmer != null) {
			return farmer.getIdCard();
		} else {
			return idCard;
		}
	}
	public void setIdCard(String idCard) {
		if (farmer != null) {
			farmer.setIdCard(idCard);
		} else {
			this.idCard = idCard;
		}
	}
	public Date getEffectiveDate() {
		if (farmer != null) {
			return farmer.getEffectiveDate();
		} else {
			return effectiveDate;
		}
	}
	public void setEffectiveDate(Date effectiveDate) {
		if (farmer != null) {
			farmer.setEffectiveDate(effectiveDate);
		} else {
			this.effectiveDate = effectiveDate;
		}
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public FarmerGroup getFarmerGroup() {
		return farmerGroup;
	}
	public void setFarmerGroup(FarmerGroup farmerGroup) {
		this.farmerGroup = farmerGroup;
	}
	public Farmer getFarmer() {
		return farmer;
	}
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}
	public boolean getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
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
	public String getSubDistrictName() {
		return subDistrictName;
	}
	public void setSubDistrictName(String subDistrictName) {
		this.subDistrictName = subDistrictName;
	}
	public String getFarmerGroupName() {
		return farmerGroupName;
	}
	public void setFarmerGroupName(String farmerGroupName) {
		this.farmerGroupName = farmerGroupName;
	}
}
