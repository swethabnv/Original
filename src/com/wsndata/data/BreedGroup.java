package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

import com.dcs.dcswc.common.ICheckOnGrid;

public class BreedGroup implements Serializable, ICheckOnGrid {

	private static final long serialVersionUID = -1403568806284025604L;
	private long breedTypeId;
	private long breedGroupId;
	private String breedGroupName;
	private Integer period;
	private String plantPeriodFrom;
	private String plantPeriodTo;
	private String forcastPeriodFrom;
	private String forcastPeriodTo;
	private String breedCategory;
	
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private BreedType breedType;
	
	private boolean checkBox;// added for breedGroupList
	private String breedTypeName;// added for breedGroupList
	
	public BreedType getBreedType() {
		return breedType;
	}
	public void setBreedType(BreedType breedType) {
		this.breedType = breedType;
	}
	public long getBreedTypeId() {
		if(breedType !=null){
			return breedType.getBreedTypeId();
		}else{
			return breedTypeId;
		}
	}
	public void setBreedTypeId(long breedTypeId) {
		if(breedType !=null){
			breedType.setBreedTypeId(breedTypeId);
		}else{
			this.breedTypeId = breedTypeId;
		}
	}
	public long getBreedGroupId() {
		return breedGroupId;
	}
	public void setBreedGroupId(long breedGroupId) {
		this.breedGroupId = breedGroupId;
	}
	public String getBreedGroupName() {
		return breedGroupName;
	}
	public void setBreedGroupName(String breedGroupName) {
		this.breedGroupName = breedGroupName;
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
	public void setBreedTypeName(String breedTypeName) {
		this.breedTypeName = breedTypeName;
	}
	public String getBreedTypeName() {
		return breedTypeName;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getPlantPeriodFrom() {
		return plantPeriodFrom;
	}
	public void setPlantPeriodFrom(String plantPeriodFrom) {
		this.plantPeriodFrom = plantPeriodFrom;
	}
	public String getPlantPeriodTo() {
		return plantPeriodTo;
	}
	public void setPlantPeriodTo(String plantPeriodTo) {
		this.plantPeriodTo = plantPeriodTo;
	}
	public String getForcastPeriodFrom() {
		return forcastPeriodFrom;
	}
	public void setForcastPeriodFrom(String forcastPeriodFrom) {
		this.forcastPeriodFrom = forcastPeriodFrom;
	}
	public String getForcastPeriodTo() {
		return forcastPeriodTo;
	}
	public void setForcastPeriodTo(String forcastPeriodTo) {
		this.forcastPeriodTo = forcastPeriodTo;
	}
	public String getBreedCategory() {
		return breedCategory;
	}
	public void setBreedCategory(String breedCategory) {
		this.breedCategory = breedCategory;
	}
	
	

}
