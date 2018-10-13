package com.wsndata.data;

import java.io.Serializable;

public class FarmerGroupChild implements Serializable 
{

	private static final long serialVersionUID = 762837946679034991L;
	private long farmerGroupId;
	private long childFarmerGroupId;
	private FarmerGroup farmerGroup;
	
	
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
	
	public long getChildFarmerGroupId() {
		return childFarmerGroupId;
	}
	public void setChildFarmerGroupId(long childFarmerGroupId) {
		this.childFarmerGroupId = childFarmerGroupId;
	}
	public FarmerGroup getFarmerGroup() {
		return farmerGroup;
	}
	public void setFarmerGroup(FarmerGroup farmerGroup) {
		this.farmerGroup = farmerGroup;
	}

}
