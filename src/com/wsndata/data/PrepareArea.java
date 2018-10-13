package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.dcs.dcswc.common.ICheckOnGrid;
public class PrepareArea implements Serializable, ICheckOnGrid {

	
	private static final long serialVersionUID = 6812156586812901134L;
	private long prepareAreaId;
	private String prepareAreaName;
	private long pprepareAreaId;

	private String pprepareAreaName;
	private long breedTypeId;
	private long breedGroupId;
	private String breedTypeName;
	private String breedGroupName;
	private Date lastUpdateDate;
	private String lastUpdateBy;
    private boolean checkBox;
	private List<PrepareArea> childPrepareArea;
	private PrepareArea parentPrepareArea;
	private String linkImageEdit;
	
	public long getPprepareAreaId() {
		return pprepareAreaId;
	}
	public void setPprepareAreaId(long pprepareAreaId) {
		this.pprepareAreaId = pprepareAreaId;
	}

	public long getPrepareAreaId() {
		return prepareAreaId;
	}
	public void setPrepareAreaId(long prepareAreaId) {
		this.prepareAreaId = prepareAreaId;
	}
	public String getPrepareAreaName() {
		return prepareAreaName;
	}
	public void setPrepareAreaName(String prepareAreaName) {
		this.prepareAreaName = prepareAreaName;
	}
	public String getPprepareAreaName() {
		return pprepareAreaName;
	}
	public void setPprepareAreaName(String pprepareAreaName) {
		this.pprepareAreaName = pprepareAreaName;
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
	public void setLinkImageEdit(String linkImageEdit) {
		this.linkImageEdit = linkImageEdit;
	}
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public boolean getCheckBox() {
		return checkBox;
	}
	public List<PrepareArea> getChildPrepareArea() {
		return childPrepareArea;
	}
	public void setChildPrepareArea(List<PrepareArea> childPrepareArea) {
		this.childPrepareArea = childPrepareArea;
	}
	public PrepareArea getParentPrepareArea() {
		return parentPrepareArea;
	}
	public void setParentPrepareArea(PrepareArea parentPrepareArea) {
		this.parentPrepareArea = parentPrepareArea;
	}
	public String getLinkImageEdit() {
		return "images/btn-edit.png";
	}
    
    
}
