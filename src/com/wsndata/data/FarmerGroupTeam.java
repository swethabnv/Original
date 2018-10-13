package com.wsndata.data;

import java.io.Serializable;

public class FarmerGroupTeam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2134163277161608524L;
	private long farmerGroupTeamId;
	private String firstName;
	private String lastName;
	private String position;
	private long farmerGroupId;
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
	public long getFarmerGroupTeamId() {
		return farmerGroupTeamId;
	}
	public void setFarmerGroupTeamId(long farmerGroupTeamId) {
		this.farmerGroupTeamId = farmerGroupTeamId;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public FarmerGroup getFarmerGroup() {
		return farmerGroup;
	}
	public void setFarmerGroup(FarmerGroup farmerGroup) {
		this.farmerGroup = farmerGroup;
	}
}
