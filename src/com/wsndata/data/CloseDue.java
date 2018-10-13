package com.wsndata.data;

import java.io.Serializable;
import java.util.Date;

import com.dcs.dcswc.common.ICheckOnGrid;


public class CloseDue implements Serializable, ICheckOnGrid {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6067686245083053702L;
	private long closeDueId;
	private long plantYear;
	private long plantNo;
	private Date createDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private long farmerGroupId;

	private String farmerGroupName;
	
	private boolean checkBox;// added for costList
	
	public long getCloseDueId() {
		return closeDueId;
	}
	public void setCloseDueId(long closeDueId) {
		this.closeDueId = closeDueId;
	}
	public long getPlantYear() {
		return plantYear;
	}
	public void setPlantYear(long plantYear) {
		this.plantYear = plantYear;
	}
	public long getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(long plantNo) {
		this.plantNo = plantNo;
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
