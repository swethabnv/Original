package com.wsndata.data;

import java.io.Serializable;

import com.dcs.dcswc.common.ICheckOnGrid;

public class PlantMethod implements Serializable, ICheckOnGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4873864794688803541L;
	
	private long plantMethodId;
	private String plantMethodName;
	private long breedTypeId; 
	private long breedGroupId;
	private String breedTypeName; 
	private String breedGroupName;
	private BreedGroup breedGroup;

	private boolean checkBox;// added for breedTypeList
	
	
	public BreedGroup getBreedGroup() {
		return breedGroup;
	}
	public void setBreedGroup(BreedGroup breedGroup) {
		this.breedGroup = breedGroup;
	}
	public long getPlantMethodId() {
		return plantMethodId;
	}
	public void setPlantMethodId(long plantMethodId) {
		this.plantMethodId = plantMethodId;
	}
	public String getPlantMethodName() {
		return plantMethodName;
	}
	public void setPlantMethodName(String plantMethodName) {
		this.plantMethodName = plantMethodName;
	}
	public long getBreedTypeId() {
		return breedTypeId;
	}
	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}
	public long getBreedGroupId() {
		return breedGroupId;
	}
	public void setBreedGroupId(long breedGroupId) {
		this.breedGroupId = breedGroupId;
	}
	public String getBreedTypeName() {
		return breedTypeName;
	}
	public void setBreedTypeName(String breedTypeName) {
		this.breedTypeName = breedTypeName;
	}
	public String getBreedGroupName() {
		return breedGroupName;
	}
	public void setBreedGroupName(String breedGroupName) {
		this.breedGroupName = breedGroupName;
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
