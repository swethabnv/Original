package com.wsndata.data;

import java.io.Serializable;

import com.dcs.dcswc.common.ICheckOnGrid;

public class FarmerFamily implements Serializable, ICheckOnGrid{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1542281835882671093L;
	private long plantId;
	private String idCard;
	
	private String firstName;
	private String lastName;
	private Plant plant;
	private boolean checkBox;// added for List
	
	
	
	public Plant getPlant() {
		return plant;
	}
	public void setPlant(Plant plant) {
		this.plant = plant;
	}
	public long getPlantId() {
		if (plant != null) {
			return plant.getPlantId();
		} else {
			return plantId;
		}
	}
	public void setPlantId(long plantId) {
		if (plant != null) {
			plant.setPlantId(plantId);
		} else {
			this.plantId = plantId;
		}
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
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
	
	
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public boolean getCheckBox() {
		return checkBox;
	}
	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}
	
	
	
	
	
}
