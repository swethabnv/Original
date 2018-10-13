package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dcs.dcswc.common.ICheckOnGrid;

public class BreedType implements Serializable, ICheckOnGrid {

	private static final long serialVersionUID = 4473436366397575362L;
	private long breedTypeId;
	private String breedTypeName;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private List<BreedGroup> breedGroup;
	private boolean checkBox;// added for breedTypeList
	private int maxPerYear;
	
	
	
	public int getMaxPerYear() {
		return maxPerYear;
	}
	public void setMaxPerYear(int maxPerYear) {
		this.maxPerYear = maxPerYear;
	}
	public List<BreedGroup> getBreedGroup() {
		return breedGroup;
	}
	public void setBreedGroup(List<BreedGroup> breedGroup) {
		this.breedGroup = breedGroup;
	}
	public long getBreedTypeId() {
		return breedTypeId;
	}
	public void setBreedTypeId(long breedTypeId) {
		this.breedTypeId = breedTypeId;
	}
	public String getBreedTypeName() {
		return breedTypeName;
	}
	public void setBreedTypeName(String breedTypeName) {
		this.breedTypeName = breedTypeName;
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
